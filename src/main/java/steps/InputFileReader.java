package steps;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

import data.InputCollection;

public class InputFileReader implements IInputReader {
    
    public static final String INPUT_FILE_OPEN_ERROR_MESSAGE = "Problem opening input file";
    public static final String INPUT_FILE_PARSE_ERROR_MESSAGE = "Problem parsing JSON in input file";
    
    @Override
    public InputCollection parseInput(String inputAbsoluteFilePath) throws Exception {

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(inputAbsoluteFilePath));
        } catch (Exception e) {
            throw new Exception(INPUT_FILE_OPEN_ERROR_MESSAGE, e);
        }
        Gson gson = new Gson();
        InputCollection inputCollection = null;
        try {
            inputCollection = gson.fromJson(reader, InputCollection.class);
        } catch (Exception e) {
            throw new Exception(INPUT_FILE_PARSE_ERROR_MESSAGE, e);
        }
        
        return inputCollection;
    }

}
