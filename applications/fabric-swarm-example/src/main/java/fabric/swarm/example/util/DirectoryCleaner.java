package fabric.swarm.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author eduardo.thums
 */
@Component
public class DirectoryCleaner {

	private String fabricUserDirectory;

	public DirectoryCleaner(@Value("${fabric.userDirectoryPath}") String fabricUserDirectory) {
		this.fabricUserDirectory = fabricUserDirectory;
	}

	public void cleanUp() {
		final File directory = new File(fabricUserDirectory);
		deleteDirectory(directory);
	}

	private boolean deleteDirectory(File dir) {
		if (!dir.isDirectory()) {
			return false;
		}

		final File[] children = dir.listFiles();
		for (File child : children) {
			boolean success = deleteDirectory(child);
			if (!success) {
				return false;
			}
		}

		return dir.delete();
	}
}
