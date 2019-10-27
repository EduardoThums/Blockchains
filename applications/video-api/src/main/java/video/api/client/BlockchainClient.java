package video.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import video.api.model.VideoAssetModel;

import java.util.List;

/**
 * @author eduardo.thums
 */
@FeignClient(value = "${feign.blockchain.value}", url = "${feign.blockchain.url}")
public interface BlockchainClient {

	@RequestMapping(method = RequestMethod.GET, value = "/blockchain/cameraId/{cameraId}")
	List<VideoAssetModel> queryByCameraIdAndTimestampRange(@PathVariable("cameraId") long cameraId,
	                                                       @RequestParam("startDate") long startDate,
	                                                       @RequestParam("endDate") long endDate,
	                                                       @RequestParam("logStartDate") long logStartDate);
}
