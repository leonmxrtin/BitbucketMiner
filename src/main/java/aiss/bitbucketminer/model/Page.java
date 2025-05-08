package aiss.bitbucketminer.model;

import java.util.List;

public class Page<T> {
    private List<T> values;
    private Integer pagelen;
    private String next;
    private String previous;

    public List<T> getValues() {
        return values;
    }
    public void setValues(List<T> values) {
        this.values = values;
    }

    public Integer getPageLen() {
        return pagelen;
    }

    public void setPageLen(Integer pageLen) {
        this.pagelen = pageLen;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
