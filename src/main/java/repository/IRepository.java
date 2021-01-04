package repository;

import data.InputCollection;
import data.PlaylistData;
import data.SongData;
import data.UserData;

public interface IRepository {
    public SongData getSong(Long songId);
    public UserData getUser(Long userId);
    public PlaylistData getPlaylist(Long playlistId);
    public void addPlaylist(PlaylistData addPlaylist);
    public void removePlaylist(Long removePlaylistId);
    public InputCollection getDataForOutput();
}
