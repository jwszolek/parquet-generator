package io.datamass.config;

public class Column {
    private String name;
    private Integer uniquenessPercentage;
    private Boolean allowNulls;

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
