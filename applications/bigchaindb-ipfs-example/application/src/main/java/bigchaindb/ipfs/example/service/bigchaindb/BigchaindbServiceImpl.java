package bigchaindb.ipfs.example.service.bigchaindb;

import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.util.KeyPairUtils;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BigchaindbServiceImpl implements BigchaindbService {

    @Override
    public List<String> createTransaction(final String ipfsHash, final String contentHash) throws Exception {
        final KeyPair keyPair = KeyPairUtils.generateNewKeyPair();
        final Map<String, String> assetData = mapAssetData(ipfsHash, contentHash);

        return Collections.singletonList(BigchainDbTransactionBuilder
                .init()
                .addAssets(assetData, TreeMap.class)
                .operation(Operations.CREATE)
                .buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
                .sendTransaction()
                .getId());
    }

    @Override
    public Transaction findTransactionById(String transactionId) throws IOException {
        return TransactionsApi.getTransactionById(transactionId);
    }

    private Map<String, String> mapAssetData(final String ipfsHash, final String contentHash) {
        return new TreeMap<String, String>() {
            {
                put("ipfsHash", ipfsHash);
                put("contentHash", contentHash);
            }
        };
    }
}
