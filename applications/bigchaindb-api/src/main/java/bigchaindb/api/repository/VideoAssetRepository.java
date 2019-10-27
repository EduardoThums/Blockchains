package bigchaindb.api.repository;

import bigchaindb.api.model.AssetModel;
import bigchaindb.api.projection.AssetProjection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author eduardo.thums
 */
public interface VideoAssetRepository extends MongoRepository<AssetModel, ObjectId> {

	@Query(value = "{'data.cameraId': ?0 , 'data.startDate': {'$gte': ?1 }, 'data.endDate': {'$lte': ?2 }}",
			fields = "{'data': 1}",
			sort = "{'data.startDate': 1}")
	List<AssetProjection> findByCameraIdAndStartDateAndEndDateRange(long cameraId, long startDate, long endDate);
}
