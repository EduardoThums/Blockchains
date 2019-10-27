package fabric.api.util;

import fabric.api.model.FabricUserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * @author eduardo.thums
 */
@Component
public class FabricUserSerializer {

	private String fabricUserDirectory;

	public FabricUserSerializer(@Value("${fabric.userDirectoryPath}") final String fabricUserDirectory) {
		this.fabricUserDirectory = fabricUserDirectory;
	}

	public FabricUserModel writeFabricUser(final FabricUserModel fabricUserModel) throws Exception {
		final String directoryPath = fabricUserDirectory + "/" + fabricUserModel.getAffiliation();
		final File directory = new File(directoryPath);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		final String filePath = directoryPath + "/" + fabricUserModel.getName() + ".ser";

		final FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));

		objectOutputStream.writeObject(fabricUserModel);
		objectOutputStream.close();
		fileOutputStream.close();

		return fabricUserModel;
	}
}
