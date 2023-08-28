package com.ramij.hashing;

import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;
import com.ramij.hashing.nodes.VirtualNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class ConsistentHashBuilder<T extends Node, H extends HashFunction> {
    Collection<T> nodes;
    H hashFunction;
    int noOfReplicas;

    private ConsistentHashBuilder() {
    }

    public static ConsistentHashBuilder<Node, HashFunction> create() {
        return new ConsistentHashBuilder<>();
    }

    public void addReplicas(int replicas) {
        noOfReplicas = replicas;
    }
    public void addNodes(Collection<T> nodes){
        this.nodes=nodes;
    }

    public void addHashFunction(H h){
        this.hashFunction=h;
    }

    public ConsistentHashing<T, H> build() {
        Collection<VirtualNode> virtualNodes = nodes.stream().map(t -> new VirtualNode<T>(t, noOfReplicas)).collect(Collectors.toList());
        return new ConsistentHashingImpl<T, H>(virtualNodes, hashFunction);
    }



}
