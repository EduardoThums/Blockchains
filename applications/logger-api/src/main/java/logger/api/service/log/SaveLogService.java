package logger.api.service.log;

import logger.api.controller.log.request.CreateLogRequest;
import logger.api.domain.entity.LogEntity;
import logger.api.repository.LogRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class SaveLogService {

	private LogRepository logRepository;

	public SaveLogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public void saveLogEntity(CreateLogRequest request) {
		final LogEntity logEntity = new LogEntity(request.getStartDate(), request.getEndDate());

		logRepository.saveAndFlush(logEntity);
	}
}
