package steps;

import data.InputCollection;

public interface IInputReader {
    public InputCollection parseInput(String inputAbsoluteFilePath) throws Exception;
}
