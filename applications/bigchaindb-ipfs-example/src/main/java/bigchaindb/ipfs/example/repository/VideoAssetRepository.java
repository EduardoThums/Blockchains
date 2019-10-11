package bigchaindb.ipfs.example.repository;

import bigchaindb.ipfs.example.model.AssetModel;
import bigchaindb.ipfs.example.projection.AssetProjection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author eduardo.thums
 */
public interface VideoAssetRepository extends MongoRepository<AssetModel, ObjectId> {

	@Query(value = "{'id': :#{id} }", fields = "{'data': 1}")
	AssetModel findById(@Param("id") String id);

	@Query(value = "{'data.startDate': {'$gt': ?0 } }", fields = "{'data': 1}")
	List<AssetModel> findByStartDateGreaterThan(Long startDate);

	@Query(value = "{'data.startDate': {'$gte': ?0 },  'data.endDate': {'$lte': ?1 }}", fields = "{'data': 1}")
	List<AssetProjection> findByStartDateAndEndDateRange(Long startDate, Long endDate);
}
