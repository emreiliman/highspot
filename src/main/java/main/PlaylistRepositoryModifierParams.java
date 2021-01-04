package main;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PlaylistRepositoryModifierParams {
    @Getter
    private String inputAbsoluteFilePath;
    @Getter
    private String changesAbsoluteFilePath;
    @Getter
    private String outputAbsoluteFilePath;
}
