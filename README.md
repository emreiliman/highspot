
# Highspot take home exercise - PlaylistRepositoryModifier

PlaylistRepositoryModifier is a command line tool that ingests an input JSON file (of a certain format) and a changes file (of a certain format), applies the changes to the input and produces an output file (that has the same format as input file's).
The format of the input file and changes file can be found on documentation section.


### Prerequisites

* [Maven](https://maven.apache.org/install.html)


### Building

```
git clone https://github.com/emreiliman/highspot.git
mvn package shade:shade
```

### Usage

```
java -jar highspot-0.0.1.jar - inputFilePath [inputFilePath] -changeFilePath [changeFilePath] -outputFilePath [outputFilePath]
```

### Authors

* Emre iliman


## Documentation

**Program's workflow is as follows:**

* Parse command line options
* Read the input
* Read the changes file
* Apply changes
* Write to output

**Command line options:**

`-c,--changeFilePath <arg>`   change file path
 
`-i,--inputFilePath <arg>`    input file path
 
`-o,--outputFilePath <arg>`   output file path

**Input and change file format:**

Sample input file can be found on [exercise description](https://engage.highspot.com/viewer/5fce67dca2e3a91edd602c02?iid=5e3ddf836a3b111a75317962&source=email.untracked).

Sample change file is follows:

```
   [
    {
        "type":"AddPlaylist",
        "playlist_id" : "100",
        "user_id" : "2",
        "song_ids" : [
            "8",
            "32"
        ] 
    },
    {
        "type":"RemovePlaylist",
        "playlist_id" : "3"
    },
    {
        "type":"AddSongToPlaylist",
        "song_id" : "4",
        "playlist_id" : "1"
    }
  ]
```

## Folders

**main** contains the main method (on App.java) and the PlaylistRepositoryModifier.java which is the class that's responsible for the program's workflow.

**steps** contains classes that are responsible executing the the stages of the app, parsing the command line options, reading the input file, reading the changes file, applying the changes, and writing to the output file.

**data** contains classes that represent the data: The entities in the input the file, collection of the entities in the input the file and different types of changes.

**actions** contains 'action' classes that executes the logic that corresponds to the changes in the change file, i.e. actual code that adds a playlist, adds a song or removes a playlist.

**repository** contains Repository class which represents the &quot;data store&quot; where the data lives.

**util** contains custom deserializer class.


## Scaling

To scale this, we can scale up or use parallelism. In terms of scaling up, there is not much to discuss in terms of software effort. In order to scale out, we need to be able to use parallelism without any single point of bottleneck.
There are 3 parts that can be the bottleneck:
1.  Reading the input and the changes file and writing the output file.
2.  Processing the input file and the changes file.
    1.  Running operations themselves that achieve the required state changes that stem from the change file (input file can also be considered as a list of add operations.)
    2.  Storing the result of the operations.

### Considerations:

**Ordering guarantee:**

Order of processing of operations matters if the operations are done on the same entity, e.g. if there is an addition of an entity and then a deletion of the entity, different ordering of the operations result in different states.
Requirement 1: Operations that entail the same entity must be processed in same order that they are in the input.
To distribute load in general, we can say that input is grouped in to n groups and distributed to m workers. In order to guarantee Requirement 1, we need to make sure the operations that entail the same entity ends up in the same group in the same order.
This will have implications on both reading the input and processing the changes.

**Reading the input and change files:**
*   When the file is too large to fit the memory, an obvious must is to use a streaming reader.
*   We can consider introducing parallelism while reading the file 
    *   On a single machine with a single disk, this won’t be of benefit since the disk will be the bottleneck
    *   We can’t really divide the file into multiple pieces and distribute to multiple machines without preprocessing either, because then we won’t be able to guarantee ordering/grouping e.g. we can’t make sure the first record of the last file is not related to the first record of the first file.
    *   We could copy the whole the file to multiple machines and make each machine be responsible for a non-intersecting set of groups and skip over records that are in other groups while reading.
        *This may not be much of a performance gain either, since the only processing that’s done while reading is figuring out what kind of change is corresponds to each record. This is not an expensive operation at all. And since each machine will need to read the whole file, this doesn’t really justify copying cost.
*   [Suggested1] Since we’ll need to read the whole file to guarantee ordering, introducing parallelism for this stage doesn’t have much of a benefit. Our suggested scaled system reads the file using a streaming reader sequentially.
*   [Suggested2] If the producer of the input file can create multiple files which are already grouped, then we can use the distributing to multiple machines approach.

**Processing the records on input and change files:**

Given we have read the input/change file and have representation of the records, we’d like to take advantage of parallelism at this stage.
*   [Suggested] The speed at which we read from the input or change file may be faster than the speed at which we process the records, requiring us to save the not yet processed records to be processed later. Thus, in order to both guarantee ordering and to be able account for the speed difference we should use queues with ordering.
*   We could use multithreading and in memory queues, and concurrent hashmaps on a single machine but since applying changes is write heavy and all writes go to the same data structure in the end, there'll be a lot of locking and we won't benefit from threading that much.
*   We could shard the hashmap on the single machine to ease the locking but still that’s not nowhere near the performance of a distributed setup.
*   [Suggested] Since neither in memory queues on a single machine nor the sharded hashmaps on a single machine can come close to the reliability and performance characteristics of a distributed solution, we should use distributed queues such as SQS or Kafka and a distributed key-value store with durability option such as DynamoDB or Redis.

**Data integrity and performance:**

Both while processing the input and change files, we may need to check for data integrity.
*   Checking not to add playlists for non-existent users or with non-existing songs might be a trade-off between processing speed and data store size and also the time lost because of extra processing that will be necessary by the consumer of the playlists. The decision will depend on the number of such records and business requirements. If we decide to forgo such a check:
    *  We could delete such playlists periodically as a background operation.
    *  The consumer of the playlists will have to do the checks.
*   While processing the input file, if we can assume that the input file doesn’t violate integrity, we can process songs, playlists and users in parallel. Otherwise, we’ll need to delay processing of playlists until users and songs are processed. (How to process the change file is mentioned in the previous section).

**Why key-value store:**

We suggest a key-value store over SQL is because of the barely relational nature of the specific data, extent of possible scale and ease of management.

### Proposal

![alt text](https://github.com/emreiliman/highspot/blob/master/diag1.jpg?raw=true)


**Workflow:**

1.  Read the input file. As explained before the suggestion is to use read the input file sequentially. We can use the import functionality of DynamoDB or Redis. We might need to preprocess to change the format.
    1.  The preprocessing to just changing format can be done by map-reduce as the ordering since not change during format change.
2.  Read the change file sequentially unless it’s preprocessed and divided into multiple files in a grouped fashion such that the operations on the same entity end up in the same group. The suggested grouping is by a set of playlists. We can divide the set into n groups, where n will also control the level of parallelism.
    1.  Create n FIFO queues to keep the operations in order.
    2.  Create a ‘task’ per record in the change file. And add it to the corresponding queue depending on which group the owner of the playlist is.
3.  Create n workers to consume the tasks from the n queues. Each worker will consume from its queue sequentially but n workers will be running in parallel.
    1.  Each worker will process the addPlaylist, removePlaylist, addSongToPlaylist operations, interacting with the distributed key-value store.
4.  We can use the export option of key-value store to create the output file.
5.  If needed, do postprocessing fix formatting of the output file.
    a.  As in preprocessing, postprocessing can be done via map-reduce.



