package com.ramij.hashing.nodes;

import java.util.Objects;

public class SampleNode implements Node{
    String key;

    public SampleNode(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleNode that)) return false;
        return Objects.equals(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }

    @Override
    public String getKey() {
        return key;
    }
    public String getData(){
        return getKey();
    }

}
