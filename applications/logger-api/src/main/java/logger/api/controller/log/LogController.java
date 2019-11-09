package logger.api.controller.log;

import logger.api.controller.log.request.CreateLogRequest;
import logger.api.controller.log.response.LogResponse;
import logger.api.service.log.AuditLogService;
import logger.api.service.log.SaveLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/log")
public class LogController {

	private SaveLogService saveLogService;

	private AuditLogService auditLogService;

	public LogController(SaveLogService saveLogService, AuditLogService auditLogService) {
		this.saveLogService = saveLogService;
		this.auditLogService = auditLogService;
	}

	@PostMapping
	public void createLog(@RequestBody @Valid final CreateLogRequest request) {
		saveLogService.saveLogEntity(request);
	}

	@GetMapping
	public ResponseEntity<List<LogResponse>> auditLogs() {
		return ResponseEntity.ok(auditLogService.auditLogs());
	}
}
