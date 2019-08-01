package org.hyperledger.fabric.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hyperledger.fabric.sdk.Enrollment;

import java.io.Serializable;
import java.security.PrivateKey;

@Getter
@AllArgsConstructor
public class CAEnrollment implements Enrollment, Serializable {

	private PrivateKey key;

	private String cert;
}
