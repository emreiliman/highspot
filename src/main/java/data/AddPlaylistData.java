package data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AddPlaylistData extends ChangeData {
    public static final String TYPE = "AddPlaylist";
    @Getter
    private Long playlist_id;
    @Getter
    private Long user_id;
    @Getter
    private List<Long> song_ids;
}
