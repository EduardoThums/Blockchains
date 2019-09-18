package bigchaindb.ipfs.example.controller.ipfs;

import bigchaindb.ipfs.example.service.ipfs.GetFileByHashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ipfs")
public class IpfsController {

	private GetFileByHashService getFileByHashService;

	public IpfsController(GetFileByHashService getFileByHashService) {
		this.getFileByHashService = getFileByHashService;
	}

	@GetMapping
	public ResponseEntity<String> getFileByHashService(@RequestParam("hash") String hash) throws IOException {
		return ResponseEntity.ok(getFileByHashService.getFileByHash(hash));
	}
}
