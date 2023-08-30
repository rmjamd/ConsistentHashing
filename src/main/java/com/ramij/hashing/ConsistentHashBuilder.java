package com.ramij.hashing;

import com.ramij.hashing.hasher.DefaultHashFunction;
import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;

import java.util.Collection;
import java.util.Collections;

public final class ConsistentHashBuilder<T extends Node> {
    Collection<T> nodes= Collections.emptyList();
    int noOfReplicas=3;
    HashFunction hashFunction;

    private ConsistentHashBuilder() {
    }

    public static <T extends Node> ConsistentHashBuilder<T> create() {
        return new ConsistentHashBuilder<>();
    }

    public ConsistentHashBuilder<T> addReplicas(int replicas) {
        noOfReplicas = replicas;
        return this;
    }
    public ConsistentHashBuilder<T> addNodes(Collection<T > nodes){
        this.nodes=nodes;
        return this;
    }

    public ConsistentHashBuilder<T> addHashFunction(HashFunction h){
        this.hashFunction=h;
        return this;
    }

    public ConsistentHashing<T> build() {
        if(hashFunction==null){
            hashFunction=new DefaultHashFunction();
        }
        return new ConsistentHashingImpl<>(nodes, hashFunction,noOfReplicas);
    }


}
