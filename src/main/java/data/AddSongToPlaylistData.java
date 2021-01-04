package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AddSongToPlaylistData extends ChangeData {
    public static final String TYPE = "AddSongToPlaylist";
    @Getter
    private Long song_id;
    @Getter
    private Long playlist_id;
}
