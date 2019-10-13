package bigchaindb.ipfs.example.controller.bigchaindb;

import bigchaindb.ipfs.example.model.VideoAssetModel;
import bigchaindb.ipfs.example.service.bigchaindb.*;
import com.bigchaindb.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/bigchaindb")
public class BigchaindbController {

	private FindTransactionByIdService findTransactionByIdService;

	private QueryByAssetIdService queryByAssetIdService;

	private QueryByStartDateGreaterThanService queryByStartDateGreaterThanService;

	private QueryByStartAndEndDateRangeService queryByStartAndEndDateRangeService;

	private QueryAssetByCameraIdAndStartAndEndDateRangeService queryAssetByCameraIdAndStartAndEndDateRangeService;

	public BigchaindbController(FindTransactionByIdService findTransactionByIdService, QueryByAssetIdService queryByAssetIdService, QueryByStartDateGreaterThanService queryByStartDateGreaterThanService, QueryByStartAndEndDateRangeService queryByStartAndEndDateRangeService, QueryAssetByCameraIdAndStartAndEndDateRangeService queryAssetByCameraIdAndStartAndEndDateRangeService) {
		this.findTransactionByIdService = findTransactionByIdService;
		this.queryByAssetIdService = queryByAssetIdService;
		this.queryByStartDateGreaterThanService = queryByStartDateGreaterThanService;
		this.queryByStartAndEndDateRangeService = queryByStartAndEndDateRangeService;
		this.queryAssetByCameraIdAndStartAndEndDateRangeService = queryAssetByCameraIdAndStartAndEndDateRangeService;
	}

	@GetMapping
	public ResponseEntity<Transaction> findTransactionById(@RequestParam("transactionId") String transactionId) throws IOException {
		return ResponseEntity.ok(findTransactionByIdService.findTransactionById(transactionId));
	}

	@GetMapping("/find-by-id/{id}")
	public ResponseEntity<VideoAssetModel> queryAssetById(@PathVariable("id") String id) {
		return ResponseEntity.ok(queryByAssetIdService.queryByAssetById(id));
	}

	@GetMapping("/find-by-start-date/gt/{startDate}")
	public ResponseEntity<List<VideoAssetModel>> queryByStartDateRange(@PathVariable("startDate") Long startDate) {
		return ResponseEntity.ok(queryByStartDateGreaterThanService.queryByStartDateGreaterThanService(startDate));
	}

	@GetMapping("/find-by-date-range")
	public ResponseEntity<List<VideoAssetModel>> queryByStartAndEndDateRange(@RequestParam("startDate") Long startDate, @RequestParam("endDate") Long endDate) {
		return ResponseEntity.ok(queryByStartAndEndDateRangeService.queryByStartAndEndDateRange(startDate, endDate));
	}

	@GetMapping("/find-by-camera-id-and-date-range/{cameraId}")
	public ResponseEntity<List<VideoAssetModel>> queryAssetByCameraIdAndStartAndEndDateRange(@PathVariable("cameraId") Long cameraId,
	                                                                                         @RequestParam("startDate") Long startDate,
	                                                                                         @RequestParam("endDate") Long endDate) {
		return ResponseEntity.ok(queryAssetByCameraIdAndStartAndEndDateRangeService.queryAssetByCameraIdAndStartAndEndDateRange(cameraId, startDate, endDate));
	}
}
