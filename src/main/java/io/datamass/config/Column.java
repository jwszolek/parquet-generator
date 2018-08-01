package io.datamass.config;

import java.util.List;

public class Column {
    private String name;
    private Integer uniquenessPercentage;
    private Boolean allowNulls;

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    private List<String> type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUniquenessPercentage() {
        return uniquenessPercentage;
    }

    public void setUniquenessPercentage(Integer uniquenessPercentage) {
        this.uniquenessPercentage = uniquenessPercentage;
    }

    public Boolean getAllowNulls() {
        return allowNulls;
    }

    public void setAllowNulls(Boolean allowNulls) {
        this.allowNulls = allowNulls;
    }

}
