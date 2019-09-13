package fabric.swarm.example.util;

import fabric.swarm.example.model.FabricUserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author eduardo.thums
 */
@Component
public class FabricUserDeserializer {

	private String fabricUserDirectory;

	public FabricUserDeserializer(@Value("${fabric.userDirectoryPath}") final String fabricUserDirectory) {
		this.fabricUserDirectory = fabricUserDirectory;
	}

	public FabricUserModel readFabricUser(final String affiliation, final String username) throws Exception {
		final String filePath = fabricUserDirectory + affiliation + "/" + username + ".ser";
		final File file = new File(filePath);

		if (!file.exists()) {
			return null;
		}

		final FileInputStream fileStream = new FileInputStream(filePath);
		final ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		final FabricUserModel fabricUserModel = (FabricUserModel) objectInputStream.readObject();

		objectInputStream.close();
		fileStream.close();

		return fabricUserModel;
	}
}
