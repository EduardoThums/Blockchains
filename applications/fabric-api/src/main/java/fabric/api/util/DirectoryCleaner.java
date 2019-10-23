package fabric.api.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.logging.Logger;

/**
 * @author eduardo.thums
 */
@Component
public class DirectoryCleaner {

	private final static Logger LOGGER = Logger.getLogger(DirectoryCleaner.class.getName());


	private String fabricUserDirectory;

	public DirectoryCleaner(@Value("${fabric.userDirectoryPath}") String fabricUserDirectory) {
		this.fabricUserDirectory = fabricUserDirectory;
	}

	@PostConstruct
	public void cleanUp() {
		final File directory = new File(fabricUserDirectory);
		deleteDirectory(directory);
	}

	private boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}

		LOGGER.info("Deleting - " + dir.getName());
		return dir.delete();
	}
}
