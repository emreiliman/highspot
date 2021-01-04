package data;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class UserData {
    @NonNull @Getter @Expose
    private Long id;
    @NonNull @Getter @Expose
    private String name;
}
