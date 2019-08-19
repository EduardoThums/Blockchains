package bigchaindb.driver.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
public class VideoAssetModel implements Serializable {

	private String hash;

	private Instant timestamp;

	private byte[] content;
}
