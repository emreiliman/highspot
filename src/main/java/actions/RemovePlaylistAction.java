package actions;

import data.RemovePlaylistData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import repository.IRepository;

@AllArgsConstructor
@Log4j2
public class RemovePlaylistAction implements IAction {

    @Getter @NonNull
    private RemovePlaylistData removePlaylistData;
    
    @Override
    public void apply(IRepository r) {
        if(removePlaylistData.getPlaylist_id() == null) {
            log.info("Missing data for RemovePlaylist change, skipping.");
            return;
        }
        String msg = String.format("Removing playlist with id [%s]", removePlaylistData.getPlaylist_id()); 
        log.info(msg);
        r.removePlaylist(removePlaylistData.getPlaylist_id());
    }
}
