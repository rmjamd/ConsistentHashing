package com.ramij.hashing;

import com.ramij.hashing.exceptions.AttributeMissingException;
import com.ramij.hashing.hasher.DefaultHashFunction;
import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;

import java.util.Collection;

public final class ConsistentHashBuilder<T extends Node> {
    Collection<T> nodes;
    int noOfReplicas;
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
        if(noOfReplicas==0 )
            throw new AttributeMissingException("No of replicas is Zero");
        if(hashFunction==null){
            hashFunction=new DefaultHashFunction();
        }
        return new ConsistentHashingImpl<>(nodes, hashFunction,noOfReplicas);
    }


}
