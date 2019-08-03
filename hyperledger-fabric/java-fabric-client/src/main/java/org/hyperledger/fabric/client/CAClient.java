package org.hyperledger.fabric.client;

import lombok.Getter;
import org.hyperledger.fabric.config.Config;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.user.FabricUser;
import org.hyperledger.fabric.util.Util;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

@Getter
public class CAClient {

	private HFCAClient instance;

	public CAClient() throws MalformedURLException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, CryptoException {
		init();
	}

	private void init() throws MalformedURLException, IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException {
		this.instance = HFCAClient.createNewInstance(Config.CA_ORG1_URL.getValue(), null);
		this.instance.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
	}

	public FabricUser registerAdminUser() throws Exception {
		final Util util = new Util();
		final FabricUser fabricUser = util.readFabricUser(Config.ORG1.getValue(), Config.ADMIN_USERNAME.getValue());

		if (fabricUser != null) {
			return fabricUser;
		}

		final FabricUser adminFabricUser = new FabricUser();
		adminFabricUser.setName(Config.ADMIN_USERNAME.getValue());
		adminFabricUser.setPassword(Config.ADMIN_PASSWORD.getValue());
		adminFabricUser.setAffiliation(Config.ORG1.getValue());
		adminFabricUser.setMspId(Config.ORG1_MSP.getValue());

		final Enrollment adminEnrollment = instance.enroll(adminFabricUser.getName(), adminFabricUser.getPassword());
		adminFabricUser.setEnrollment(adminEnrollment);

		return util.writeFabricUser(adminFabricUser);
	}
}
