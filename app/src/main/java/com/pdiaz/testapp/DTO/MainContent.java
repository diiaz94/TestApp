package com.pdiaz.testapp.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class MainContent {
    @SerializedName("contents")
    @Expose
    private List<Content> contents;

    public MainContent(List<Content> contents) {
        this.contents = contents;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
