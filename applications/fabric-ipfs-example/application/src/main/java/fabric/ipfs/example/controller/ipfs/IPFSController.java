package fabric.ipfs.example.controller.ipfs;


import fabric.ipfs.example.service.ipfs.IPFSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ipfs")
public class IPFSController {

	private IPFSService ipfsService;

	public IPFSController(IPFSService ipfsService) {
		this.ipfsService = ipfsService;
	}

	@GetMapping("/find-file-by-hash/{hash}")
	public ResponseEntity<String> findFileByHash(@PathVariable("hash") String hash) throws IOException {
		final String filePath = ipfsService.findFileByHash(hash);

		return ResponseEntity.ok(filePath);
	}
}


