package basic.network.application.util;

import basic.network.application.config.FabricConfig;
import basic.network.application.fabric.user.FabricUser;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

	public FabricUser writeFabricUser(final FabricUser fabricUser) throws Exception {
		final String directoryPath = FabricConfig.USERS_DIRECTORY_PATH.getValue() + fabricUser.getAffiliation();
		final File directory = new File(directoryPath);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		final String filePath = directoryPath + "/" + fabricUser.getName() + ".ser";

		final FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));

		objectOutputStream.writeObject(fabricUser);

		objectOutputStream.close();
		fileOutputStream.close();

		return fabricUser;
	}

	public FabricUser readFabricUser(final String affiliation, final String username) throws Exception {
		final String filePath = FabricConfig.USERS_DIRECTORY_PATH.getValue() + affiliation + "/" + username + ".ser";
		final File file = new File(filePath);

		if (!file.exists()) {
			return null;
		}

		final FileInputStream fileStream = new FileInputStream(filePath);
		final ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

		final FabricUser fabricUser = (FabricUser) objectInputStream.readObject();

		objectInputStream.close();
		fileStream.close();

		return fabricUser;
	}

	public void cleanUp() {
		final File directory = new File(FabricConfig.USERS_DIRECTORY_PATH.getValue());
		deleteDirectory(directory);
	}

	private static boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}

		// either file or an empty directory
		Logger.getLogger(Util.class.getName()).log(Level.INFO, "Deleting - " + dir.getName());
		return dir.delete();
	}
}
