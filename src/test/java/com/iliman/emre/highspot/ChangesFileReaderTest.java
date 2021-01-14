package com.iliman.emre.highspot;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import data.AddPlaylistData;
import data.AddSongToPlaylistData;
import data.ChangeData;
import data.RemovePlaylistData;
import steps.ChangesFileReader;

public class ChangesFileReaderTest {

    @Test
    public void Given_ValidChangesFile_When_ReadChangesFile_Then_CreateCorrectChangesData() throws Exception
    {
        ChangesFileReader reader = new ChangesFileReader();
        File changeFile = new File("src/test/testData/changes1.json");
        List<ChangeData> changes = reader.parseChangeFile(changeFile.getAbsolutePath());
        assertTrue(changes.size() == 3);
        AddPlaylistData addPlaylist = (AddPlaylistData)(changes.get(0));
        assertTrue(addPlaylist.getSong_ids().containsAll(Arrays.asList(new Long[] {8L,32L})));
        assertTrue(addPlaylist.getUser_id() == 2);
        RemovePlaylistData removePlaylist = (RemovePlaylistData)(changes.get(1));
        assertTrue(removePlaylist.getPlaylist_id() == 3);
        AddSongToPlaylistData addSongToPlaylist = (AddSongToPlaylistData)(changes.get(2));
        assertTrue(addSongToPlaylist.getSong_id() == 4);
        assertTrue(addSongToPlaylist.getPlaylist_id() == 1);
    }
    
    @Test
    public void Given_BrokenChangesFile_When_ReadChangesFile_Then_CreateCorrectChangesData() throws Exception
    {
        ChangesFileReader reader = new ChangesFileReader();
        File changeFile = new File("src/test/testData/brokenChanges1.json");
        List<ChangeData> changes = reader.parseChangeFile(changeFile.getAbsolutePath());
        assertTrue(changes.size() == 3);
        AddPlaylistData addPlaylist = (AddPlaylistData)(changes.get(0));
        assertTrue(addPlaylist.getSong_ids().containsAll(Arrays.asList(new Long[] {8L,32L})));
        assertTrue(addPlaylist.getUser_id() == 2);
        RemovePlaylistData removePlaylist = (RemovePlaylistData)(changes.get(1));
        assertTrue(removePlaylist.getPlaylist_id() == 3);
        AddSongToPlaylistData addSongToPlaylist = (AddSongToPlaylistData)(changes.get(2));
        assertTrue(addSongToPlaylist.getSong_id() == 4);
        assertTrue(addSongToPlaylist.getPlaylist_id() == 1);
    }
}
