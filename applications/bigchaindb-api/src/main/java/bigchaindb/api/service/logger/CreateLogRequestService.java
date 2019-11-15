package bigchaindb.api.service.logger;

import bigchaindb.api.client.LoggerClient;
import bigchaindb.api.model.LogRequestModel;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class CreateLogRequestService {

	private LoggerClient loggerClient;

	public CreateLogRequestService(LoggerClient loggerClient) {
		this.loggerClient = loggerClient;
	}

	public void createLog(Long startDate, Long endDate) {
		loggerClient.createLog(new LogRequestModel(startDate, endDate));
	}
}
