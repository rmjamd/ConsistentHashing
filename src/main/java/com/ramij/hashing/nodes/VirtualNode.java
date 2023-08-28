package com.ramij.hashing.nodes;

import lombok.Getter;

import java.util.Objects;

public class VirtualNode<T extends Node> implements Node {
    T node;
    int position;

    public int getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return "VirtualNode{" +
                "node=" + node +
                ", position=" + position +
                '}';
    }

    public VirtualNode(T node, int position) {
        this.node = node;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualNode<?> that)) return false;
        return position == that.position && node.equals(that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, position);
    }

    @Override
    public String getKey() {
        return node.getKey() + "-" + position;
    }
}
