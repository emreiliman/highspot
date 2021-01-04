package steps;

import data.InputCollection;

public interface IOutputWriter {
    public void writeOuput(InputCollection inputCollection, String outputAbsoluteFilePath) throws Exception;
}
