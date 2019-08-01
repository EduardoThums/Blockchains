package org.hyperledger.fabric.client;

import lombok.Getter;
import lombok.Setter;
import org.hyperledger.fabric.config.Config;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.user.UserContext;
import org.hyperledger.fabric.util.Util;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@Getter
@Setter
public class CAClient {

	String caUrl;

	Properties caProperties;

	HFCAClient instance;

	UserContext adminContext;

	public CAClient(String caUrl, Properties caProperties) throws MalformedURLException {
		this.caUrl = caUrl;
		this.caProperties = caProperties;
		init();
	}

	private void init() throws MalformedURLException {
		instance = HFCAClient.createNewInstance(caUrl, caProperties);
		instance.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
	}

	public UserContext enrollAdminUser(String username, String password) throws Exception {
		UserContext userContext = Util.readUserContext(adminContext.getAffiliation(), username);
		if (userContext != null) {
			Logger.getLogger(CAClient.class.getName()).log(Level.WARNING, "CA -" + caUrl + " admin is already enrolled.");
			return userContext;
		}
		Enrollment adminEnrollment = instance.enroll(username, password);
		adminContext.setEnrollment(adminEnrollment);
		Logger.getLogger(CAClient.class.getName()).log(Level.INFO, "CA -" + caUrl + " Enrolled Admin.");
		Util.writeUserContext(adminContext);
		return adminContext;
	}

	public UserContext registerAdminUser() throws Exception {
		final UserContext adminUserContext = new UserContext();
		adminUserContext.setName(Config.ADMIN);
		adminUserContext.setAffiliation(Config.ORG1);
		adminUserContext.setMspId(Config.ORG1_MSP);

		final UserContext userContext = Util.readUserContext(adminUserContext.getAffiliation(), Config.ADMIN);

		if (userContext != null) {
			System.out.printf("CA - %s admin is already enrolled.", this.caUrl);
			return userContext;
		}

		final Enrollment adminEnrollment = instance.enroll(Config.ADMIN, Config.ADMIN_PASSWORD);
		adminUserContext.setEnrollment(adminEnrollment);

		System.out.printf("CA - %s Enrolled admin.", this.caUrl);

		Util.writeUserContext(adminUserContext);

		return adminUserContext;
	}

	public String registerUser(String username, String organization) throws Exception {
		UserContext userContext = Util.readUserContext(adminContext.getAffiliation(), username);
		if (userContext != null) {
			Logger.getLogger(CAClient.class.getName()).log(Level.WARNING, "CA -" + caUrl + " User " + username + " is already registered.");
			return null;
		}
		RegistrationRequest rr = new RegistrationRequest(username, organization);
		String enrollmentSecret = instance.register(rr, adminContext);
		Logger.getLogger(CAClient.class.getName()).log(Level.INFO, "CA -" + caUrl + " Registered User - " + username);
		return enrollmentSecret;
	}

	public UserContext enrollUser(UserContext user, String secret) throws Exception {
		UserContext userContext = Util.readUserContext(adminContext.getAffiliation(), user.getName());
		if (userContext != null) {
			Logger.getLogger(CAClient.class.getName()).log(Level.WARNING, "CA -" + caUrl + " User " + user.getName() + " is already enrolled");
			return userContext;
		}
		Enrollment enrollment = instance.enroll(user.getName(), secret);
		user.setEnrollment(enrollment);
		Util.writeUserContext(user);
		Logger.getLogger(CAClient.class.getName()).log(Level.INFO, "CA -" + caUrl + " Enrolled User - " + user.getName());
		return user;
	}

}
