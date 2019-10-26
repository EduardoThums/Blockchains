package fabric.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fabric.api.model.VideoAssetModel;
import fabric.api.service.fabric.QueryByCameraIdAndTimestampRangeService;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/fabric")
public class FabricController {

	private QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService;

	public FabricController(QueryByCameraIdAndTimestampRangeService queryByCameraIdAndTimestampRangeService) {
		this.queryByCameraIdAndTimestampRangeService = queryByCameraIdAndTimestampRangeService;
	}

	@GetMapping("/cameraId/{cameraId}")
	public ResponseEntity<List<VideoAssetModel>> queryByCameraIdAndTimestampRange(@PathVariable("cameraId") long cameraid,
	                                                                              @RequestParam("startDate") long startDate,
	                                                                              @RequestParam("endDate") long endDate) throws JsonProcessingException, ProposalException, InvalidArgumentException {

		return ResponseEntity.ok(queryByCameraIdAndTimestampRangeService.queryByCameraIdAndTimestampRange(cameraid, startDate, endDate));
	}
}
