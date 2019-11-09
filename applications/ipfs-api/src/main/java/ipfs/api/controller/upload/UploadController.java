package ipfs.api.controller.upload;

import ipfs.api.service.kafka.ProduceRecordModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

	private ProduceRecordModelService produceRecordModelService;

	public UploadController(ProduceRecordModelService produceRecordModelService) {
		this.produceRecordModelService = produceRecordModelService;
	}

	@PostMapping
	public void uploadFile(@RequestParam("cameraId") Long cameraId,
	                       @RequestParam("startDate") Long startDate,
	                       @RequestParam("endDate") Long endDate,
	                       @RequestParam("logStartDate") Long logStartDate,
	                       @RequestParam("file") MultipartFile file) throws Exception {
		produceRecordModelService.produceRecord(cameraId, startDate, endDate, logStartDate, file.getBytes());
	}
}
