package com.font.specificationexecutor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Filter<FIELD_TYPE> implements Serializable {

    private FIELD_TYPE equals;
    private FIELD_TYPE notEquals;
    private Boolean isNotNull;
    private List<FIELD_TYPE> in;
    private List<FIELD_TYPE> notIn;

    public Filter() {
    }

    public Filter(Filter<FIELD_TYPE> filter) {
        this.equals = filter.equals;
        this.notEquals = filter.notEquals;
        this.isNotNull = filter.isNotNull;
        this.in = filter.in == null ? null : new ArrayList<>(filter.in);
        this.notIn = filter.notIn == null ? null : new ArrayList<>(filter.notIn);
    }

    public Filter<FIELD_TYPE> copy() {
        return new Filter<>(this);
    }

    public FIELD_TYPE getEquals() {
        return equals;
    }

    public void setEquals(FIELD_TYPE equals) {
        this.equals = equals;
    }

    public FIELD_TYPE getNotEquals() {
        return notEquals;
    }

    public void setNotEquals(FIELD_TYPE notEquals) {
        this.notEquals = notEquals;
    }

    public Boolean getIsNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(Boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    public List<FIELD_TYPE> getIn() {
        return in;
    }

    public void setIn(List<FIELD_TYPE> in) {
        this.in = in;
    }

    public List<FIELD_TYPE> getNotIn() {
        return notIn;
    }

    public void setNotIn(List<FIELD_TYPE> notIn) {
        this.notIn = notIn;
    }
}
