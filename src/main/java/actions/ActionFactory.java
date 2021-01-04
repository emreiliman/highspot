package actions;

import data.AddPlaylistData;
import data.AddSongToPlaylistData;
import data.ChangeData;
import data.RemovePlaylistData;

public class ActionFactory {
    public static IAction create(ChangeData change) {
        switch(change.getType()) {
            case AddPlaylistData.TYPE: 
                return new AddPlaylistAction((AddPlaylistData)change);
            case AddSongToPlaylistData.TYPE:
                return new AddSongToPlaylistAction((AddSongToPlaylistData)change);
            case RemovePlaylistData.TYPE:
                return new RemovePlaylistAction((RemovePlaylistData)change);
            default:
                return null;
        }
    }
}
