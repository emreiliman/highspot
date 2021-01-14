package com.iliman.emre.highspot;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import data.InputCollection;
import data.PlaylistData;
import steps.InputFileReader;

public class InputFileReaderTest {
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void Given_ValidInputFile_When_ReadInputFile_Then_CreateCorrectInputCollection() throws Exception
    {
        InputFileReader reader = new InputFileReader();
        File testFile = new File("src/test/testData/input1.json");
        InputCollection inputCollection = reader.parseInput(testFile.getAbsolutePath());
        assertTrue(inputCollection.getPlaylists().size() == 3);
        assertTrue(inputCollection.getSongs().size() == 40);
        assertTrue(inputCollection.getUsers().size() == 7);
        assertTrue(inputCollection.getUsers().get(2).getName().equals("Ankit Sacnite"));
        PlaylistData playlist = inputCollection.getPlaylists().stream().collect(Collectors.toList()).get(1);
        List<Long> songIds = playlist.getSong_ids();
        assertTrue(songIds.containsAll(Arrays.asList(new Long[] {6L,8L,11L})));
        assertTrue(inputCollection.getSongs().get(5).getTitle().equals("Whatever It Takes"));
        assertTrue(inputCollection.getSongs().get(5).getArtist().equals("Imagine Dragons"));
    }
    
    @Test
    public void Given_InvalidInputFilePath_When_ReadInputFile_Then_Throw() throws Exception
    {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Problem opening input file");
        
        InputFileReader reader = new InputFileReader();
        File testFile = new File("src/test/testData/x.json");
        reader.parseInput(testFile.getAbsolutePath());
    }
    
    @Test
    public void Given_InvalidInputFile_When_ReadInputFile_Then_Throw() throws Exception
    {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Problem parsing JSON in input file");
        
        InputFileReader reader = new InputFileReader();
        File testFile = new File("src/test/testData/brokenInput1.json");
        reader.parseInput(testFile.getAbsolutePath());
    }
    
}
