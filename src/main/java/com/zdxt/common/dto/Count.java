package com.zdxt.common.dto;

import java.io.Serializable;

public class Count implements Serializable {
    private Integer contact;
    private Integer activity;
    private Integer banner;
    private Integer resources;
    private Integer indexNews;

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getBanner() {
        return banner;
    }

    public void setBanner(Integer banner) {
        this.banner = banner;
    }

    public Integer getResources() {
        return resources;
    }

    public void setResources(Integer resources) {
        this.resources = resources;
    }

    public Integer getIndexNews() {
        return indexNews;
    }

    public void setIndexNews(Integer indexNews) {
        this.indexNews = indexNews;
    }

    public Integer getGermanyNews() {
        return germanyNews;
    }

    public void setGermanyNews(Integer germanyNews) {
        this.germanyNews = germanyNews;
    }

    private Integer germanyNews;

    @Override
    public String toString() {
        return "Count{" +
                "contact=" + contact +
                ", activity=" + activity +
                ", banner=" + banner +
                ", resources=" + resources +
                ", indexNews=" + indexNews +
                ", germanyNews=" + germanyNews +
                '}';
    }
}
