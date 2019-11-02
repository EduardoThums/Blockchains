package fabric.api.service.file;

import java.io.IOException;
import java.util.List;

/**
 * @author eduardo.thums
 */
public interface GetFilesByHashListService {

	List<byte[]> getFilesByHashList(List<String> hashList) throws IOException;
}
