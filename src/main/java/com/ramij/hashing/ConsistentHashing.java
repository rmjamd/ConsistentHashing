package com.ramij.hashing;

import com.ramij.hashing.nodes.Node;

import java.util.Collection;

public interface ConsistentHashing<T extends Node> {

      void addNode(T t);
      void addAllNodes(Collection<T> addNodes);
      void removeNode(T t);
      void removeAll(Collection<T> removeNodes);
      Node getNode(String key);

}
