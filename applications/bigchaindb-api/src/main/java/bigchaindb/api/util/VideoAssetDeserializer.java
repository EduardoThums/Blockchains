package bigchaindb.api.util;

import bigchaindb.api.model.VideoAssetModel;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author eduardo.thums
 */
@Component
public class VideoAssetDeserializer implements JsonDeserializer {

	@Override
	public VideoAssetModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		return context.deserialize(jsonObject, VideoAssetModel.class);
	}
}
