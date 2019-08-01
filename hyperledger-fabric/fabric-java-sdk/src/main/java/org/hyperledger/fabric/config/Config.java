package org.hyperledger.fabric.config;

public class Config {

	public static final String ORG1_MSP = "Org1MSP";

	public static final String ORG1 = "org1";

	public static final String ADMIN = "admin";

	public static final String ADMIN_PASSWORD = "adminpw";

	public static final String CA_ORG1_URL = "http://localhost:7054";

	public static final String ORDERER_URL = "grpc://localhost:7050";

	public static final String ORDERER_NAME = "orderer.example.com";

	public static final String CHANNEL_NAME = "mychannel";

	public static final String ORG1_PEER_0 = "peer0.org1.example.com";

	public static final String ORG1_PEER_0_URL = "grpc://localhost:7051";

	public static final String CHAINCODE_1_NAME = "mycc";

	public static final String CREATE_CAR_FUNCTION_NAME = "createCar";

	public static final String[] CREATE_CAR_FUNCTION_ARGUMENTS = {"CAR1", "Chevy", "Volt", "Red", "Nick"};
}
