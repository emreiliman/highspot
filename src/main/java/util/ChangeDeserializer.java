package util;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import data.AddPlaylistData;
import data.AddSongToPlaylistData;
import data.ChangeData;
import data.RemovePlaylistData;

public class ChangeDeserializer implements JsonDeserializer<ChangeData> {
    @Override
    public ChangeData deserialize(JsonElement json, Type member, JsonDeserializationContext context) {
        String type = json.getAsJsonObject().get("type").getAsString();
        switch (type) {
            case AddPlaylistData.TYPE: return context.deserialize(json, AddPlaylistData.class);
            case AddSongToPlaylistData.TYPE: return context.deserialize(json, AddSongToPlaylistData.class);
            case RemovePlaylistData.TYPE: return context.deserialize(json, RemovePlaylistData.class);
            default: return null;
        }
    }
}