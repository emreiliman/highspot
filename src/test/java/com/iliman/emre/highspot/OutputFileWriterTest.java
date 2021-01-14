package com.iliman.emre.highspot;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import data.InputCollection;
import data.PlaylistData;
import repository.Repository;
import steps.InputFileReader;
import steps.OutputFileWriter;

public class OutputFileWriterTest {
    @Test
    public void Given_ValidOutputPath_When_WriteOutput_Then_WriteToOutputFile() throws Exception
    {
        InputFileReader reader = new InputFileReader();
        File testFile = new File("src/test/testData/input1.json");
        InputCollection inputCollection = reader.parseInput(testFile.getAbsolutePath());
        Repository repository = new Repository(inputCollection);
        
        repository.removePlaylist(1L);
        
        OutputFileWriter writer = new OutputFileWriter();
        
        File file = File.createTempFile("temp", null);
        file.deleteOnExit();
        String outputPath = file.getAbsolutePath();
        writer.writeOuput(repository.getDataForOutput(), outputPath);
        inputCollection = reader.parseInput(outputPath);
        
        assertTrue(inputCollection.getPlaylists().size() == 2);
        assertTrue(inputCollection.getSongs().size() == 40);
        assertTrue(inputCollection.getUsers().size() == 7);
        assertTrue(inputCollection.getUsers().get(2).getName().equals("Ankit Sacnite"));
        PlaylistData playlist = inputCollection.getPlaylists().stream().collect(Collectors.toList()).get(0);
        List<Long> songIds = playlist.getSong_ids();
        assertTrue(songIds.containsAll(Arrays.asList(new Long[] {6L,8L,11L})));
        assertTrue(inputCollection.getSongs().get(5).getTitle().equals("Whatever It Takes"));
        assertTrue(inputCollection.getSongs().get(5).getArtist().equals("Imagine Dragons"));
    }
}
