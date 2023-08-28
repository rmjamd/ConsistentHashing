package com.ramij.hashing.nodes;

import java.util.Objects;

public class ServerNode implements Node {
    String ip;
    int port;

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ServerNode(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerNode{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerNode that)) return false;
        return port == that.port && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public String getKey() {
        return ip + ":" + port;
    }
}
