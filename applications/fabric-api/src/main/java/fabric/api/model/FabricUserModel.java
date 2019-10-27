package fabric.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.util.Set;

/**
 * @author eduardo.thums
 */
@Getter
@Setter
@NoArgsConstructor
public class FabricUserModel implements User, Serializable {

	private String name;

	private String password;

	private Set<String> roles;

	private String account;

	private String affiliation;

	private Enrollment enrollment;

	private String mspId;
}
