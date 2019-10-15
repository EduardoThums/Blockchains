package bigchaindb.ipfs.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author eduardo.thums
 */
@Getter
@Document(collection = "assets")
@AllArgsConstructor
public class AssetModel {

	@Id
	@Field("_id")
	private ObjectId objectId;

	@Field("data")
	private VideoAssetModel data;
}
