package com.ramij.hashing;

import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;
import com.ramij.hashing.nodes.VirtualNode;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class ConsistentHashingImpl<T extends Node> implements ConsistentHashing<T> {
    HashFunction hash;
    int noOfReplicas;
    TreeMap<Long, VirtualNode<T>> ring = new TreeMap<>();

    public ConsistentHashingImpl(Collection<T> nodes, HashFunction hashFunction, int noOfReplicas) {
        this.noOfReplicas = noOfReplicas;
        this.hash = hashFunction;
        if (nodes != null && !nodes.isEmpty()) {
            for (T node : nodes) {
                for (int pos = 1; pos <= noOfReplicas; pos++) {
                    VirtualNode<T> vNode = getVirtualNode(node, pos);
                    ring.put(hashFunction.getHash(vNode.getKey()), vNode);
                }
            }
        }
    }

    @Override
    public void addNode(T node) {
        for (int pos = 1; pos <= noOfReplicas; pos++) {
            VirtualNode<T> vNode = getVirtualNode(node, pos);
            ring.put(hash.getHash(vNode.getKey()), vNode);
        }
    }

    @Override
    public void addAllNodes(Collection<T> nodes) {
        for (T node : nodes) {
            for (int pos = 1; pos <= noOfReplicas; pos++) {
                VirtualNode<T> vNode = getVirtualNode(node, pos);
                ring.put(hash.getHash(vNode.getKey()), vNode);
            }
        }
    }

    @Override
    public void removeNode(T node) {
        for (int pos = 1; pos <= noOfReplicas; pos++) {
            VirtualNode<T> vNode = getVirtualNode(node, pos);
            ring.remove(hash.getHash(vNode.getKey()));
        }
    }

    @Override
    public void removeAll(Collection<T> removeNodes) {
        for (T node : removeNodes) {
            for (int pos = 1; pos <= noOfReplicas; pos++) {
                VirtualNode<T> vNode = getVirtualNode(node, pos);
                ring.remove(hash.getHash(vNode.getKey()));
            }
        }
    }

    @Override
    public T getNode(String key) {
        long lastValue = hash.getHash(key);
        Map.Entry<Long, VirtualNode<T>> longVirtualNodeEntry = ring.higherEntry(lastValue);
        longVirtualNodeEntry = longVirtualNodeEntry == null ? ring.firstEntry() : longVirtualNodeEntry;
        return longVirtualNodeEntry.getValue().getOriginalNode();
    }

    private VirtualNode<T> getVirtualNode(T node, int position) {
        return new VirtualNode<T>(node, position);
    }

}
