package com.iliman.emre.highspot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import data.InputCollection;
import data.PlaylistData;
import main.App;
import steps.InputFileReader;

public class AppTest 
{
    @Test
    public void Given_ValidInputFile_ValidChangeFile_ValidOutputFile_When_Run_Then_ProduceCorrectOutputFile() throws Exception
    {
        File file = File.createTempFile("temp", null);
        file.deleteOnExit();
        String outputPath = file.getAbsolutePath();
        
        String[] args = new String[] {"app","-input","src/test/testData/input1.json",
                "-change","src/test/testData/changes1.json",
                "-output",outputPath};
         
        
         App app = new App();
         app.run(args);
        
         InputFileReader reader = new InputFileReader();
         InputCollection inputCollection = reader.parseInput(outputPath);
         
         assertTrue(inputCollection.getPlaylists().size() == 3);
         assertTrue(inputCollection.getSongs().size() == 40);
         assertTrue(inputCollection.getUsers().size() == 7);
         assertTrue(inputCollection.getUsers().get(2).getName().equals("Ankit Sacnite"));
         List<PlaylistData> playlists = inputCollection.getPlaylists().stream().filter(p -> p.getUser_id() == 2L).collect(Collectors.toList());
         assertTrue(playlists.size() == 2);
         assertEquals(3, playlists.get(0).getSong_ids().size());
         assertTrue(playlists.get(1).getSong_ids().size() == 2);
         assertTrue(inputCollection.getSongs().get(5).getTitle().equals("Whatever It Takes"));
         assertTrue(inputCollection.getSongs().get(5).getArtist().equals("Imagine Dragons"));
         assertTrue(playlists.get(0).getSong_ids().containsAll(Arrays.asList(new Long[] {8L,32L,4L})));
         
    }
}
