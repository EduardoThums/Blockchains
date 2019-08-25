package org.hyperledger.fabric.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VideoAssetModel {

	private String ipfsHash;

	private String contentHash;

}
