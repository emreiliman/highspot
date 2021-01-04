package steps;

import java.util.List;

import data.ChangeData;

public interface IChangesReader {
    public List<ChangeData> parseChangeFile(String changesAbsoluteFilePath) throws Exception;
}
