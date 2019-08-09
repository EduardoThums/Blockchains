package basic.network.application.fabric.client;

import basic.network.application.config.FabricConfig;
import basic.network.application.fabric.user.FabricUser;
import basic.network.application.util.Util;
import lombok.Getter;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
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
		this.instance = HFCAClient.createNewInstance(FabricConfig.CA_ORG1_URL.getValue(), null);
		this.instance.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
	}

	public FabricUser registerAdminUser() throws Exception {
		final Util util = new Util();
		final FabricUser fabricUser = util.readFabricUser(FabricConfig.ORG1.getValue(), FabricConfig.ADMIN_USERNAME.getValue());

		if (fabricUser != null) {
			return fabricUser;
		}

		final FabricUser adminFabricUser = new FabricUser();
		adminFabricUser.setName(FabricConfig.ADMIN_USERNAME.getValue());
		adminFabricUser.setPassword(FabricConfig.ADMIN_PASSWORD.getValue());
		adminFabricUser.setAffiliation(FabricConfig.ORG1.getValue());
		adminFabricUser.setMspId(FabricConfig.ORG1_MSP.getValue());

		final Enrollment adminEnrollment = instance.enroll(adminFabricUser.getName(), adminFabricUser.getPassword());
		adminFabricUser.setEnrollment(adminEnrollment);

		return util.writeFabricUser(adminFabricUser);
	}
}
