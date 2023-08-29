package com.ramij.hashing;

import com.ramij.hashing.hasher.DefaultHashFunction;
import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;
import com.ramij.hashing.nodes.SampleNode;
import com.ramij.hashing.nodes.VirtualNode;

public class HashFunctionTest {
    public static void main(String[] args) {
        HashFunction hash = new DefaultHashFunction();

        Node node1 = new SampleNode("sample node 1");
        Node node2 = new SampleNode("sample node 2");
        Node node3 = new SampleNode("sample node 3");
        VirtualNode<Node> v1 = new VirtualNode<>(node1, 1);
        VirtualNode<Node> v2 = new VirtualNode<>(node1, 2);
        VirtualNode<Node> v3 = new VirtualNode<>(node1, 3);
        System.out.println(node1 + " -> " + hash.getHash(v1.getKey()));
        System.out.println(node2 + " -> " + hash.getHash(v2.getKey()));
        System.out.println(node3 + " -> " + hash.getHash(v3.getKey()));
    }

}
