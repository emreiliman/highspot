package steps;

import main.PlaylistRepositoryModifierParams;

public interface ICmdLineParser {
    public PlaylistRepositoryModifierParams parseCommandLine(String args[]) throws Exception;
}
