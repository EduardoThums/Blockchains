package fabric.swarm.example.component.fabric;

import fabric.swarm.example.model.FabricUserModel;
import fabric.swarm.example.util.FabricUserDeserializer;
import fabric.swarm.example.util.FabricUserSerializer;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author eduardo.thums
 */
@Component
public class CaClient {

	private String org1;

	private String org1Msp;

	private String adminUsername;

	private String adminPassword;

	private HFCAClient hfcaClient;

	private FabricUserDeserializer fabricUserDeserializer;

	private FabricUserSerializer fabricUserSerializer;

	private CaClient(@Value("${fabric.org1}") String org1,
					 @Value("${fabric.org1.msp}") String org1Msp,
					 @Value("${fabric.admin.username}") String adminUsername,
					 @Value("${fabric.admin.username}") String adminPassword,
					 HFCAClient hfcaClient,
					 FabricUserDeserializer fabricUserDeserializer,
					 FabricUserSerializer fabricUserSerializer) {
		this.org1 = org1;
		this.org1Msp = org1Msp;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.hfcaClient = hfcaClient;
		this.fabricUserDeserializer = fabricUserDeserializer;
		this.fabricUserSerializer = fabricUserSerializer;
	}

	public FabricUserModel registerAdminUser() throws Exception {
		final FabricUserModel fabricUserModel = fabricUserDeserializer.readFabricUser(org1, adminUsername);

		if (fabricUserModel != null) {
			return fabricUserModel;
		}

		final FabricUserModel adminFabricUser = new FabricUserModel();
		adminFabricUser.setName(adminUsername);
		adminFabricUser.setPassword(adminPassword);
		adminFabricUser.setAffiliation(org1);
		adminFabricUser.setMspId(org1Msp);

		final Enrollment adminEnrollment = hfcaClient.enroll(adminFabricUser.getName(), adminFabricUser.getPassword());
		adminFabricUser.setEnrollment(adminEnrollment);

		return fabricUserSerializer.writeFabricUser(adminFabricUser);
	}
}
