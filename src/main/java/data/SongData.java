package data;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor 
public class SongData {
    @NonNull @Getter @Expose
    private Long id;
    @NonNull @Getter @Expose
    private String artist;
    @NonNull @Getter @Expose
    private String title;
}
