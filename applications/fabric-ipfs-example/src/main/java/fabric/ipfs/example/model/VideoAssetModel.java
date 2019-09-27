package fabric.ipfs.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

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
}
