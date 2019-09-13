package bigchaindb.ipfs.example.service.ipfs;

import java.io.IOException;

public interface IPFSService {

    String insert(byte[] record) throws IOException;

    String findFileByHash(String hash) throws IOException;
}
