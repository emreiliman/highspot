package com.iliman.emre.highspot;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import data.ChangeData;
import data.InputCollection;
import data.PlaylistData;
import repository.Repository;
import steps.ChangesApplier;
import steps.ChangesFileReader;
import steps.InputFileReader;

public class ChangesApplierTest {
    @Test
    public void Given_ValidChangeFile_When_ApplyChanges_Then_MakeCorrectChanges() throws Exception
    {
        InputFileReader inputReader = new InputFileReader();
        File testFile = new File("src/test/testData/input1.json");
        InputCollection inputCollection = inputReader.parseInput(testFile.getAbsolutePath());
        Repository repository = new Repository(inputCollection);
        
        ChangesFileReader changesReader = new ChangesFileReader();
        File changeFile = new File("src/test/testData/changes1.json");
        List<ChangeData> changes = changesReader.parseChangeFile(changeFile.getAbsolutePath());
        
        ChangesApplier applier = new ChangesApplier();
        applier.applyChanges(repository, changes);
        
        inputCollection = repository.getDataForOutput();
        
        assertTrue(inputCollection.getPlaylists().size() == 3);
        assertTrue(inputCollection.getSongs().size() == 40);
        assertTrue(inputCollection.getUsers().size() == 7);
        assertTrue(inputCollection.getUsers().get(2).getName().equals("Ankit Sacnite"));
        List<PlaylistData> playlists = inputCollection.getPlaylists().stream().filter(p -> p.getUser_id() == 2L).collect(Collectors.toList());
        assertTrue(playlists.size() == 2);
        assertTrue(playlists.get(0).getSong_ids().size() == 3);
        assertTrue(playlists.get(1).getSong_ids().size() == 2);
        assertTrue(inputCollection.getSongs().get(5).getTitle().equals("Whatever It Takes"));
        assertTrue(inputCollection.getSongs().get(5).getArtist().equals("Imagine Dragons"));
        assertTrue(playlists.get(0).getSong_ids().containsAll(Arrays.asList(new Long[] {8L,32L,4L})));
        
    }
    
    @Test
    public void Given_BrokenChangeFile_When_ApplyChanges_Then_SkipChangesWithMissingField() throws Exception
    {
        InputFileReader inputReader = new InputFileReader();
        File testFile = new File("src/test/testData/input1.json");
        InputCollection inputCollection = inputReader.parseInput(testFile.getAbsolutePath());
        Repository repository = new Repository(inputCollection);
        
        ChangesFileReader changesReader = new ChangesFileReader();
        File changeFile = new File("src/test/testData/brokenChanges1.json");
        List<ChangeData> changes = changesReader.parseChangeFile(changeFile.getAbsolutePath());
        
        ChangesApplier applier = new ChangesApplier();
        applier.applyChanges(repository, changes);
        
        inputCollection = repository.getDataForOutput();
        
        assertTrue(inputCollection.getPlaylists().size() == 2);
        assertTrue(inputCollection.getSongs().size() == 40);
        assertTrue(inputCollection.getUsers().size() == 7);
        assertTrue(inputCollection.getUsers().get(2).getName().equals("Ankit Sacnite"));
        List<PlaylistData> playlists = inputCollection.getPlaylists().stream().filter(p -> p.getUser_id() == 2L).collect(Collectors.toList());
        assertTrue(playlists.size() == 1);
        assertTrue(playlists.get(0).getSong_ids().size() == 3);
        
        assertTrue(inputCollection.getSongs().get(5).getTitle().equals("Whatever It Takes"));
        assertTrue(inputCollection.getSongs().get(5).getArtist().equals("Imagine Dragons"));
        assertTrue(playlists.get(0).getSong_ids().containsAll(Arrays.asList(new Long[] {8L,32L,4L})));
        
    }
}
