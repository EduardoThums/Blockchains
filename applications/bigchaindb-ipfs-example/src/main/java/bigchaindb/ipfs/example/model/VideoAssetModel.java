package bigchaindb.ipfs.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
public class VideoAssetModel implements Serializable {

	private static final long serialVersionUID = -9061180728548775137L;

	private Long startDate;

	private Long endDate;

	private String storageHash;

	private String contentHash;

	private Long cameraId;
}
