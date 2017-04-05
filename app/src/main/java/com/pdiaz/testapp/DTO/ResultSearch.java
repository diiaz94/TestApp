package com.pdiaz.testapp.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class ResultSearch {
    @SerializedName("contents")
    @Expose
    private List<Content> content;

    public ResultSearch(List<Content> content) {
        this.content = content;
    }

    public ResultSearch() {

    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}
