package video.api.controller.upload;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import video.api.service.kafka.ProduceFileRecordService;

import java.io.IOException;
import java.time.Instant;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

	private ProduceFileRecordService produceFileRecordService;

	public UploadController(ProduceFileRecordService produceFileRecordService) {
		this.produceFileRecordService = produceFileRecordService;
	}

	@PostMapping
	public void uploadFile(@RequestParam("cameraId") Long cameraId,
	                       @RequestParam("startDate") Long startDate,
	                       @RequestParam("endDate") Long endDate,
	                       @RequestParam("logStartDate") Long logStartDate,
	                       @RequestParam("file") MultipartFile file) throws IOException {
		final Long milliLogStartDate = Instant.now().toEpochMilli();
		produceFileRecordService.produceFileRecord(cameraId, startDate, endDate, milliLogStartDate, file.getBytes());
	}
}
