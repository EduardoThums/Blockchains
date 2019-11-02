package fabric.api.controller;

import fabric.api.model.VideoAssetModel;
import fabric.api.service.fabric.QueryByCameraIdAndTimestampRangeService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/api/blockchain")
public class FabricController {

	private QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService;

	public FabricController(QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService) {
		this.queryByCameraIdAndTimestampRangeService = queryByCameraIdAndTimestampRangeService;
	}

	@GetMapping("/cameraId/{cameraId}")
	public ResponseEntity<List<VideoAssetModel>> queryByCameraIdAndTimestampRange(@PathVariable("cameraId") long cameraId,
	                                                                              @RequestParam("startDate") long startDate,
	                                                                              @RequestParam("endDate") long endDate,
	                                                                              @RequestParam("logStartDate") long logStartDate) throws IOException, ProposalException, InvalidArgumentException {

		return ResponseEntity.ok(queryByCameraIdAndTimestampRangeService.queryByCameraIdAndTimestampRange(cameraId, startDate, endDate, logStartDate));
	}
}
