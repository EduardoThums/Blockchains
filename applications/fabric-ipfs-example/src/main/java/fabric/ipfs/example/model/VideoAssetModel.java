package fabric.ipfs.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.stream.Stream;

/**
 * @author eduardo.thums
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoAssetModel {

	private Instant starDate;

	private Instant endDate;

	private String storageHash;

	private String contentHash;

	private long cameraId;

	public String[] toArguments() {
		return Stream.of(
				String.valueOf(this.starDate.getEpochSecond()),
				String.valueOf(this.endDate.getEpochSecond()),
				this.storageHash,
				this.contentHash,
				String.valueOf(this.cameraId))
				.toArray(String[]::new);
	}
}
