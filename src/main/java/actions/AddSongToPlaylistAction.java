package actions;

import data.AddSongToPlaylistData;
import data.PlaylistData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import repository.IRepository;

@AllArgsConstructor
@Log4j2
public class AddSongToPlaylistAction implements IAction {

    @Getter @NonNull
    private AddSongToPlaylistData addSongToPlaylist;
    
    @Override
    public void apply(IRepository r) throws Exception {
        
        if(addSongToPlaylist.getPlaylist_id() == null || addSongToPlaylist.getSong_id() == null) {
            log.info("Missing data for AddSongToPlaylist change, skipping.");
            return;
        }
        
        String msg = String.format("Adding song with id [%s] to playlist with id [%s]", addSongToPlaylist.getSong_id(), addSongToPlaylist.getPlaylist_id()); 
        log.info(msg);
        
        PlaylistData playlist = r.getPlaylist(addSongToPlaylist.getPlaylist_id());
        if(playlist == null) {
            msg = String.format("Playlist with id [%s] does not exits, skipping.", addSongToPlaylist.getPlaylist_id());
            log.warn(msg);
            return;
        }
        if(r.getSong(addSongToPlaylist.getSong_id()) == null) {
            msg = String.format("Song with id [%s] does not exist.", addSongToPlaylist.getSong_id());
            log.info(msg);
        }
        
        playlist.getSong_ids().add(addSongToPlaylist.getSong_id());
        r.addPlaylist(playlist);
    }
}
