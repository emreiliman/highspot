package steps;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import data.ChangeData;
import util.ChangeDeserializer;

public class ChangesFileReader implements IChangesReader {

    @Override
    public List<ChangeData> parseChangeFile(String changesAbsoluteFilePath) throws Exception {

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(changesAbsoluteFilePath));
        } catch (IOException e) {
            throw new Exception("Problem opening the the changes file.", e);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ChangeData.class, new ChangeDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<ArrayList<ChangeData>>(){}.getType();
        List<ChangeData> changes = null;
        try {
            changes = gson.fromJson(reader, listType);
        } catch (Exception e) {
            throw new Exception("Problem parsing the JSON in the the changes file.", e);
        }
        
        return changes;
    }

}
