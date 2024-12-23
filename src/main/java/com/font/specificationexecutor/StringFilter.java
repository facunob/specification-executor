package com.font.specificationexecutor;

public class StringFilter extends Filter<String> {
    private String contains;
    private String doesNotContain;

    public StringFilter(StringFilter filter) {
        super(filter);
        this.contains = filter.contains;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getDoesNotContain() {
        return this.doesNotContain;
    }

    public StringFilter setDoesNotContain(String doesNotContain) {
        this.doesNotContain = doesNotContain;
        return this;
    }
}
