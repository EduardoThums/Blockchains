package fabric.ipfs.example.controller.fabric;

import com.fasterxml.jackson.core.JsonProcessingException;
import fabric.ipfs.example.controller.fabric.request.QueryByTimestampRequest;
import fabric.ipfs.example.model.VideoAssetModel;
import fabric.ipfs.example.service.fabric.FindTransactionByHash;
import fabric.ipfs.example.service.fabric.QueryByCameraIdAndTimestampRangeService;
import fabric.ipfs.example.service.fabric.QueryByCameraIdService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fabric")
public class FabricController {

	private FindTransactionByHash findTransactionByHash;

	private QueryByCameraIdService queryByCameraIdService;

	private QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService;

	public FabricController(FindTransactionByHash findTransactionByHash, QueryByCameraIdService queryByCameraIdService, QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService) {
		this.findTransactionByHash = findTransactionByHash;
		this.queryByCameraIdService = queryByCameraIdService;
		this.queryByCameraIdAndTimestampRangeService = queryByCameraIdAndTimestampRangeService;
	}

	@GetMapping
	public ResponseEntity<String> findByHash(@RequestParam("hash") String hash) throws InvalidArgumentException, ProposalException {
		return ResponseEntity.ok(findTransactionByHash.findByHash(hash));
	}

	@GetMapping("/find-by-camera-id/{cameraId}")
	public ResponseEntity<List<VideoAssetModel>> findByCameraId(@PathVariable("cameraId") Long cameraId) throws InvalidArgumentException, ProposalException, IOException, ClassNotFoundException {
		return ResponseEntity.ok(queryByCameraIdService.queryByCameraId(cameraId));
	}

	@GetMapping("/find-by-camera-id-and-timestamp-range/{cameraId}")
	public ResponseEntity<List<VideoAssetModel>> findByCameraIdAndTimestampRange(@PathVariable("cameraId") final long cameraId,
																				 @Valid @RequestBody final QueryByTimestampRequest request) throws InvalidArgumentException, ProposalException, JsonProcessingException {
		return ResponseEntity.ok(queryByCameraIdAndTimestampRangeService.queryByCameraIdAndTimestampRange(cameraId, request));
	}
}
