package bigchaindb.ipfs.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

/**
 * @author eduardo.thums
 */
@Component
public class DirectoryCleaner {

	private String keyPairDirectoryPath;

	public DirectoryCleaner(@Value("${bigchaindb.keyPair.directoryPath}") String keyPairDirectoryPath) {
		this.keyPairDirectoryPath = keyPairDirectoryPath;
	}

	public void cleanUp() {
		final File directory = new File(keyPairDirectoryPath);
		deleteDirectory(directory);
	}

	private boolean deleteDirectory(File dir) {
		if (!dir.isDirectory()) {
			return false;
		}

		final File[] children = dir.listFiles();
		for (File child : Objects.requireNonNull(children)) {
			final boolean success = deleteDirectory(child);
			if (!success) {
				return false;
			}
		}

		return dir.delete();
	}
}
