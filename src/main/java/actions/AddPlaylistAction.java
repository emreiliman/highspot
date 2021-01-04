package actions;

import java.util.ArrayList;

import data.AddPlaylistData;
import data.PlaylistData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import repository.IRepository;

@AllArgsConstructor
@Log4j2
public class AddPlaylistAction implements IAction {

    @Getter @NonNull
    private AddPlaylistData addPlaylistData;
    
    @Override
    public void apply(IRepository r) throws Exception {
        
        if(addPlaylistData == null || addPlaylistData.getPlaylist_id() == null || addPlaylistData.getSong_ids() == null || addPlaylistData.getSong_ids().size() == 0 ||
                addPlaylistData.getUser_id() == null) {
            log.info("Missing data for AddPlaylist change, skipping.");
            return;
        }
        
        String msg = String.format("Adding playlist with id [%s]", addPlaylistData.getPlaylist_id()); 
        log.info(msg);
        
        
        if(r.getUser(addPlaylistData.getUser_id()) == null) {
            msg = String.format("Adding playlist with id [%s], user with id [%s] does not exist.", addPlaylistData.getPlaylist_id(), addPlaylistData.getUser_id());
            log.info(msg);
        }
        
        for(Long id : addPlaylistData.getSong_ids()) {
            if(r.getSong(id) == null) {
                msg = String.format("Adding playlist with id [%s], song with id [%s] does not exist.", addPlaylistData.getPlaylist_id(), id);
                log.info(msg);
            }
        }
        
        PlaylistData playlistData = r.getPlaylist(addPlaylistData.getPlaylist_id());
        playlistData = playlistData != null ? playlistData : new PlaylistData(addPlaylistData.getPlaylist_id(), addPlaylistData.getUser_id(), new ArrayList<Long>());
        playlistData.getSong_ids().addAll(addPlaylistData.getSong_ids());
        r.addPlaylist(playlistData);
    }
}
