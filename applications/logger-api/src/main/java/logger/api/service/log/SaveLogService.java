package logger.api.service.log;

import logger.api.domain.entity.LogEntity;
import logger.api.model.LogRequestModel;
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

	public void saveLogEntity(LogRequestModel logRequestModel) {
		final LogEntity logEntity = new LogEntity(logRequestModel.getStartDate(), logRequestModel.getEndDate());

		logRepository.saveAndFlush(logEntity);
	}
}
