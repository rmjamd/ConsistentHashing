package com.ramij.hashing;

import com.ramij.hashing.hasher.DefaultHashFunction;
import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;
import com.ramij.hashing.nodes.ServerNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
 
class HashFunctionTest {

    private ConsistentHashBuilder<Node> builder;

    @BeforeEach
     void setUp() {
        builder = ConsistentHashBuilder.create().addReplicas(3);
    }

    @Test
     void testAddingAndRetrievingNodes() {
        ServerNode server1 = new ServerNode("192.168.1.1", 8080);
        ServerNode server2 = new ServerNode("192.168.1.2", 8080);

        ConsistentHashing<Node> consistentHashing = builder.build();
        consistentHashing.addAllNodes(List.of(server1, server2));

        assertEquals(server1, consistentHashing.getNode("user123"));
    }

    @Test
     void testRemovingNodes() {
        ServerNode server1 = new ServerNode("192.168.1.1", 8080);
        ServerNode server2 = new ServerNode("192.168.1.2", 8080);

        ConsistentHashing<Node> consistentHashing = builder.build();
        consistentHashing.addAllNodes(List.of(server1, server2));

        assertEquals(server1, consistentHashing.getNode("user123"));

        consistentHashing.removeNode(server1);

        assertEquals(server2, consistentHashing.getNode("user123"));
    }

    @Test
     void testAddingReplicas() {
        HashFunction customHash = new DefaultHashFunction();

        ConsistentHashing<Node> customHashing = builder.addHashFunction(customHash).build();
        ServerNode server1 = new ServerNode("192.168.1.1", 8080);

        customHashing.addAllNodes(List.of(server1));

        assertNotNull(customHashing.getNode("data123"));
    }

    @Test
     void testAddingAndRemovingMultipleNodes() {
        List<Node> servers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            servers.add(new ServerNode("192.168.1." + i, 8080));
        }

        ConsistentHashing<Node> consistentHashing = builder.build();
        consistentHashing.addAllNodes(servers);

        assertNotNull(consistentHashing.getNode("user50"));

        consistentHashing.removeAll(servers);

        assertNull(consistentHashing.getNode("user50"));
    }

    @Test
     void testChangingHashFunction() {
        HashFunction customHash2 = new DefaultHashFunction();

        ConsistentHashing<Node> complexHashing = builder.addHashFunction(customHash2).build();
        ServerNode server1 = new ServerNode("172.16.0.1", 8080);

        complexHashing.addNode(server1);

        assertNotNull(complexHashing.getNode("data300"));

        complexHashing.removeNode(server1);

        assertNull(complexHashing.getNode("data300"));
    }
    @Test
     void testLoadBalancingWithReplicas() {
        List<Node> servers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            servers.add(new ServerNode("192.168.1." + i, 8080));
        }

        ConsistentHashing<Node> consistentHashing = builder.build();
        consistentHashing.addAllNodes(servers);

        // Add more data points and check if they are balanced across nodes
        int dataPoints = 1000;
        Map<Node, Integer> nodeDataCounts = new HashMap<>();
        for (int i = 0; i < dataPoints; i++) {
            Node node = consistentHashing.getNode("data" + i);
            nodeDataCounts.put(node, nodeDataCounts.getOrDefault(node, 0) + 1);
        }

        // Calculate the standard deviation of data distribution
        double mean = (double) dataPoints / servers.size();
        double variance = nodeDataCounts.values().stream()
                .mapToDouble(count -> Math.pow(count - mean, 2))
                .average()
                .orElse(0);
        double standardDeviation = Math.sqrt(variance);

        // Allow a 2x standard deviation difference in data distribution
        assertTrue(standardDeviation < mean * 2);
    }


}