package com.ramij.hashing;

import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;
import com.ramij.hashing.nodes.VirtualNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public final class ConsistentHashBuilder<T extends Node> {
    Collection<T> nodes;
    int noOfReplicas;
    HashFunction hashFunction;

    private ConsistentHashBuilder() {
    }

    public static <T extends Node> ConsistentHashBuilder<T> create() {
        return new ConsistentHashBuilder<>();
    }

    public void addReplicas(int replicas) {
        noOfReplicas = replicas;
    }
    public void addNodes(Collection<T > nodes){
        this.nodes=nodes;
    }

    public void addHashFunction(HashFunction h){
        this.hashFunction=h;
    }

    public ConsistentHashing<T> build() {
        Collection<VirtualNode> virtualNodes = nodes.stream().map(t -> new VirtualNode<T>(t, noOfReplicas)).collect(Collectors.toList());
        return new ConsistentHashingImpl<>(nodes, hashFunction,noOfReplicas);
    }


}
