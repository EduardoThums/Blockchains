package bigchaindb.ipfs.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IPFSConfig {

	IPFS_DAEMON_ADDRESS("/ip4/127.0.0.1/tcp/5001");

	private String value;
}