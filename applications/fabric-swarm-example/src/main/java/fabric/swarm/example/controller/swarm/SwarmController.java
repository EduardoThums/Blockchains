package fabric.swarm.example.controller.swarm;

import fabric.swarm.example.service.swarm.GetFileByHashService;
import fabric.swarm.example.service.swarm.UploadFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/swarm")
public class SwarmController {

	private UploadFileService uploadFileService;

	private GetFileByHashService getFileByHashService;

	public SwarmController(UploadFileService uploadFileService, GetFileByHashService getFileByHashService) {
		this.uploadFileService = uploadFileService;
		this.getFileByHashService = getFileByHashService;
	}

	@GetMapping
	public ResponseEntity<String> getFileByHash(@RequestParam("hash") String hash) throws IOException {
		return ResponseEntity.ok(getFileByHashService.getFileByHash(hash));
	}

	@PostMapping
	public ResponseEntity<String> uploadFile(@RequestParam("filePath") String filePath) throws IOException {
		return ResponseEntity.ok(uploadFileService.uploadFile(filePath));
	}
}
