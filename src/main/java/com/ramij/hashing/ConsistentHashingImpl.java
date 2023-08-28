package com.ramij.hashing;

import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;

import java.util.Collection;

public class ConsistentHashingImpl<T extends Node,H extends HashFunction> extends ConsistentHashing{


    public ConsistentHashingImpl(Collection nodes, HashFunction hashFunction) {
        super(nodes, hashFunction);
    }

    @Override
    public void addNode(Node node) {

    }

    @Override
    public void addAll(Collection addNodes) {

    }

    @Override
    public void removeNode(Node node) {

    }

    @Override
    public void removeAll(Collection removeNodes) {

    }

    @Override
    public String getNode(String key) {
        return null;
    }
}
