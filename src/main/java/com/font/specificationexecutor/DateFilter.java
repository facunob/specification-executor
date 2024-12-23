package com.font.specificationexecutor;

import java.util.Date;

public class DateFilter extends Filter<Date> {

    private Date lessThan;
    private Date lessThanOrEqual;
    private Date greaterThan;
    private Date greaterThanOrEqual;

    public DateFilter() {
    }

    public DateFilter(DateFilter filter) {
        super(filter);
        this.lessThan = filter.lessThan;
        this.lessThanOrEqual = filter.lessThanOrEqual;
        this.greaterThan = filter.greaterThan;
        this.greaterThanOrEqual = filter.greaterThanOrEqual;
    }

    public DateFilter copy() {
        return new DateFilter(this);
    }

    public Date getLessThan() {
        return lessThan;
    }

    public void setLessThan(Date lessThan) {
        this.lessThan = lessThan;
    }

    public Date getLessThanOrEqual() {
        return lessThanOrEqual;
    }

    public void setLessThanOrEqual(Date lessThanOrEqual) {
        this.lessThanOrEqual = lessThanOrEqual;
    }

    public Date getGreaterThan() {
        return greaterThan;
    }

    public void setGreaterThan(Date greaterThan) {
        this.greaterThan = greaterThan;
    }

    public Date getGreaterThanOrEqual() {
        return greaterThanOrEqual;
    }

    public void setGreaterThanOrEqual(Date greaterThanOrEqual) {
        this.greaterThanOrEqual = greaterThanOrEqual;
    }
}
