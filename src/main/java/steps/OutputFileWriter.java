package steps;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.InputCollection;

public class OutputFileWriter implements IOutputWriter {

    @Override
    public void writeOuput(InputCollection inputCollection, String outputAbsoluteFilePath) throws Exception {
        try(Writer writer = Files.newBufferedWriter(Paths.get(outputAbsoluteFilePath))) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(inputCollection, writer);
        } catch (Exception e) {
            throw new Exception("Problem writing to output file", e);
        }
    }

}
