package com.ramij.hashing;

import com.ramij.hashing.hasher.DefaultHashFunction;
import com.ramij.hashing.hasher.HashFunction;
import com.ramij.hashing.nodes.Node;
import com.ramij.hashing.nodes.VirtualNode;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public final class ConsistentHashBuilder <T extends Node> {
	Collection <T> nodes        = Collections.emptyList();
	int            noOfReplicas = 3;
	HashFunction   hashFunction;


	private ConsistentHashBuilder () {
	}


	public static <T extends Node> ConsistentHashBuilder <T> create () {
		return new ConsistentHashBuilder <>();
	}


	public ConsistentHashBuilder <T> addReplicas (int replicas) {
		noOfReplicas = replicas;
		return this;
	}


	public ConsistentHashBuilder <T> addNodes (Collection <T> nodes) {
		this.nodes.addAll(nodes);
		return this;
	}



	public ConsistentHashBuilder <T> addHashFunction (HashFunction h) {
		this.hashFunction = h;
		return this;
	}


	public ConsistentHashing <T> build () {
		if (hashFunction == null) {
			hashFunction = new DefaultHashFunction();
		}
		return new ConsistentHashingImpl <>(nodes, hashFunction, noOfReplicas);
	}


	public static class ConsistentHashingImpl <T extends Node> implements ConsistentHashing <T> {
		HashFunction                    hash;
		int                             noOfReplicas;
		TreeMap <Long, VirtualNode <T>> ring = new TreeMap <>();


		private ConsistentHashingImpl (Collection <T> nodes, HashFunction hashFunction, int noOfReplicas) {
			this.noOfReplicas = noOfReplicas;
			this.hash         = hashFunction;
			if (nodes != null && !nodes.isEmpty()) {
				for (T node : nodes) {
					for (int pos = 1; pos <= noOfReplicas; pos++) {
						VirtualNode <T> vNode = getVirtualNode(node, pos);
						ring.put(hashFunction.getHash(vNode.getKey()), vNode);
					}
				}
			}
		}


		@Override
		public void addNode (T node) {
			for (int pos = 1; pos <= noOfReplicas; pos++) {
				VirtualNode <T> vNode = getVirtualNode(node, pos);
				ring.put(hash.getHash(vNode.getKey()), vNode);
			}
		}


		@Override
		public void addAllNodes (Collection <T> nodes) {
			for (T node : nodes) {
				for (int pos = 1; pos <= noOfReplicas; pos++) {
					VirtualNode <T> vNode = getVirtualNode(node, pos);
					ring.put(hash.getHash(vNode.getKey()), vNode);
				}
			}
		}


		@Override
		public void removeNode (T node) {
			for (int pos = 1; pos <= noOfReplicas; pos++) {
				VirtualNode <T> vNode = getVirtualNode(node, pos);
				ring.remove(hash.getHash(vNode.getKey()));
			}
		}


		@Override
		public void removeAll (Collection <T> removeNodes) {
			for (T node : removeNodes) {
				for (int pos = 1; pos <= noOfReplicas; pos++) {
					VirtualNode <T> vNode = getVirtualNode(node, pos);
					ring.remove(hash.getHash(vNode.getKey()));
				}
			}
		}


		@Override
		public T getNode (String key) {
			long                              lastValue = hash.getHash(key);
			Map.Entry <Long, VirtualNode <T>> entry     = ring.higherEntry(lastValue);
			entry = entry == null ? ring.firstEntry() : entry;
			return entry == null ? null : entry.getValue().getOriginalNode();
		}


		private VirtualNode <T> getVirtualNode (T node, int position) {
			return new VirtualNode <T>(node, position);
		}

	}


}
