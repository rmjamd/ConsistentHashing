package com.ramij.hashing;

import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;

import java.util.Collection;

public abstract class ConsistentHashing<T extends Node,H extends HashFunction> {
    protected Collection<T> nodes;
    protected H h;

    public ConsistentHashing(Collection<T> nodes, H h) {
        this.nodes = nodes;
        this.h = h;
    }

    public abstract void addNode(T t);
    public abstract void addAll(Collection<T> addNodes);
    public abstract void removeNode(T t);
    public abstract void removeAll(Collection<T> removeNodes);
    public abstract String getNode(String key);

}
