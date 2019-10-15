package bigchaindb.ipfs.example.repository;

import bigchaindb.ipfs.example.model.AssetModel;
import bigchaindb.ipfs.example.projection.AssetProjection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author eduardo.thums
 */
public interface VideoAssetRepository extends MongoRepository<AssetModel, ObjectId> {

	@Query(value = "{'id': ?0 }", fields = "{'data': 1}")
	Optional<AssetProjection> findById(@Param("id") String id);

	@Query(value = "{'data.startDate': {'$gt': ?0 } }", fields = "{'data': 1}")
	List<AssetProjection> findByStartDateGreaterThan(Long startDate);

	@Query(value = "{'data.startDate': {'$gte': ?0 }, 'data.endDate': {'$lte': ?1 }}",
			fields = "{'data': 1}",
			sort = "{'data.start': 1}")
	List<AssetProjection> findByStartDateAndEndDateRange(Long startDate, Long endDate);

	@Query(value = "{'data.cameraId': ?0 }, 'data.startDate': {'$gte': ?1 }, 'data.endDate': {'$lte': ?2 }}",
			fields = "{'data': 1}",
			sort = "{'data.start': 1}")
	List<AssetProjection> findByCameraIdAndStartDateAndEndDateRange(Long cameraId, Long startDate, Long endDate);
}
