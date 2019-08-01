package org.hyperledger.fabric.user;

import lombok.Getter;
import lombok.Setter;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class UserContext implements User, Serializable {

	private String name;

	private Set<String> roles;

	private String account;

	private String affiliation;

	private Enrollment enrollment;

	private String mspId;
}
