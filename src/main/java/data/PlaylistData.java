package data;

import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class PlaylistData {
    @NonNull @Getter @Expose
    private Long id;
    @NonNull @Getter @Expose
    private Long user_id;
    @NonNull @Getter @Expose
    private List<Long> song_ids;
}
