package logger.api.service.log;

import logger.api.controller.log.response.LogResponse;
import logger.api.domain.entity.LogEntity;
import logger.api.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eduardo.thums
 */
@Service
public class AuditLogService {

	private LogRepository logRepository;

	public AuditLogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public List<LogResponse> auditLogs() {
		final List<LogEntity> logEntities = logRepository.findAll();

		logRepository.deleteAll();

		return logEntities
				.stream()
				.map(logEntity -> LogResponse
						.builder()
						.startDate(logEntity.getStartDate())
						.endDate(logEntity.getEndDate())
						.build())
				.collect(Collectors.toList());
	}
}
