package repository;

import java.util.HashMap;
import java.util.Map;

import data.InputCollection;
import data.PlaylistData;
import data.SongData;
import data.UserData;

public class Repository implements IRepository {
    
    public Repository(InputCollection input) {
        this.input = input;
        
        playlistMap = new HashMap<Long, PlaylistData>();
        songMap = new HashMap<Long, SongData>();
        userMap = new HashMap<Long, UserData>();
        for(PlaylistData p : input.getPlaylists()) {
            playlistMap.put(p.getId(), p);
        }
        for(UserData u : input.getUsers()) {
            userMap.put(u.getId(), u);
        }
        for(SongData s : input.getSongs()) {
            songMap.put(s.getId(), s);
        }
    }
    
    private InputCollection input;
     
    private Map<Long, PlaylistData> playlistMap;
     
    private Map<Long, SongData> songMap;
     
    private Map<Long, UserData> userMap;
    
    @Override
    public InputCollection getDataForOutput() {
        return new InputCollection(input.getUsers(), input.getSongs(), playlistMap.values());
    }
    
    @Override
    public UserData getUser(Long userId) {
        return userMap.get(userId);
    }

    @Override
    public SongData getSong(Long songId) {
        return songMap.get(songId);
    }

    @Override
    public PlaylistData getPlaylist(Long playlistId) {
        return playlistMap.get(playlistId);
    }

    @Override
    public void addPlaylist(PlaylistData playlist) {
        playlistMap.put(playlist.getId(), playlist);
    }

    @Override
    public void removePlaylist(Long playlistId) {
        playlistMap.remove(playlistId);
    }
}
