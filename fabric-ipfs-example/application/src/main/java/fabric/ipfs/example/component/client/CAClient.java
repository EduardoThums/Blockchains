package fabric.ipfs.example.component.client;

import fabric.ipfs.example.config.FabricConfig;
import fabric.ipfs.example.model.FabricUserModel;
import fabric.ipfs.example.util.Util;
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

	public FabricUserModel registerAdminUser() throws Exception {
		final Util util = new Util();
		final FabricUserModel fabricUserModel = util.readFabricUser(FabricConfig.ORG1.getValue(), FabricConfig.ADMIN_USERNAME.getValue());

		if (fabricUserModel != null) {
			return fabricUserModel;
		}

		final FabricUserModel adminFabricUser = new FabricUserModel();
		adminFabricUser.setName(FabricConfig.ADMIN_USERNAME.getValue());
		adminFabricUser.setPassword(FabricConfig.ADMIN_PASSWORD.getValue());
		adminFabricUser.setAffiliation(FabricConfig.ORG1.getValue());
		adminFabricUser.setMspId(FabricConfig.ORG1_MSP.getValue());

		final Enrollment adminEnrollment = instance.enroll(adminFabricUser.getName(), adminFabricUser.getPassword());
		adminFabricUser.setEnrollment(adminEnrollment);

		return util.writeFabricUser(adminFabricUser);
	}
}
