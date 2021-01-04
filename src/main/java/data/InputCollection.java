package data;

import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class InputCollection {
    @NonNull @Getter @Expose 
    private List<UserData> users;
    
    @NonNull @Getter @Expose
    private List<SongData> songs;
    
    @NonNull @Getter @Expose
    private Collection<PlaylistData> playlists;
}
