package fabric.ipfs.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FabricConfig {
	ORG1_MSP("Org1MSP"),

	ORG1("org1"),

	ADMIN_USERNAME("admin"),

	ADMIN_PASSWORD("adminpw"),

	CA_ORG1_URL("http://localhost:7054"),

	ORDERER_URL("grpc://localhost:7050"),

	ORDERER_NAME("orderer.example.com"),

	CHANNEL_NAME("mychannel"),

	ORG1_PEER_0("peer0.org1.example.com"),
	ORG1_PEER_0_URL("grpc://localhost:7051"),

	ORG1_PEER_1("peer1.org1.example.com"),
	ORG1_PEER_1_URL("grpc://localhost:8051"),

	ORG1_PEER_2("peer2.org1.example.com"),
	ORG1_PEER_2_URL("grpc://localhost:9051"),

	ORG1_PEER_3("peer3.org1.example.com"),
	ORG1_PEER_3_URL("grpc://localhost:10051"),

	CHAINCODE_NAME("video"),

	EVENT_HUB_NAME("eventhub01"),

	EVENT_HUB_URL("grpc://localhost:7053"),

	USERS_DIRECTORY_PATH("user/");

	final String value;
}
