package fabric.api.component.logger;

import fabric.api.model.LogRequestModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author eduardo.thums
 */
@FeignClient(value = "${logger.value}", url = "${logger.url}")
public interface LoggerClient {

	@RequestMapping(method = RequestMethod.POST, value = "/log")
	void createLog(@RequestBody LogRequestModel request);
}
