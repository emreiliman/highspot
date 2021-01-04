package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RemovePlaylistData extends ChangeData{
    public static final String TYPE = "RemovePlaylist";
    @Getter
    private Long playlist_id;
}
