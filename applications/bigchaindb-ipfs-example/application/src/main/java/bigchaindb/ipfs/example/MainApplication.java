package bigchaindb.ipfs.example;

import bigchaindb.ipfs.example.config.BigchaindbConfig;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.model.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @PostConstruct
    public void initBigchainDbConfig() {
        final Map<String, Object> peer01ConnectionConfig = new HashMap<>();
        peer01ConnectionConfig.put("baseUrl", BigchaindbConfig.PEER0_URL.getValue());

        final Map<String, Object> peer02ConnectionConfig = new HashMap<>();
        peer02ConnectionConfig.put("baseUrl", BigchaindbConfig.PEER1_URL.getValue());

        final Map<String, Object> peer03ConnectionConfig = new HashMap<>();
        peer03ConnectionConfig.put("baseUrl", BigchaindbConfig.PEER2_URL.getValue());

        final Map<String, Object> peer04ConnectionConfig = new HashMap<>();
        peer04ConnectionConfig.put("baseUrl", BigchaindbConfig.PEER3_URL.getValue());

        final Connection peer0Connection = new Connection(peer01ConnectionConfig);
        final Connection peer1Connection = new Connection(peer02ConnectionConfig);
        final Connection peer2Connection = new Connection(peer03ConnectionConfig);
        final Connection peer3Connection = new Connection(peer04ConnectionConfig);

        final List<Connection> connections = Arrays.asList(
                peer0Connection,
                peer1Connection,
                peer2Connection,
                peer3Connection);

        BigchainDbConfigBuilder
                .addConnections(connections)
                .setTimeout(60000)
                .setup();
    }
}
