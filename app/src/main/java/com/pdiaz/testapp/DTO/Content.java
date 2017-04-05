package com.pdiaz.testapp.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class Content {
    @SerializedName("mainContent")
    @Expose
    private List<MainContent> mainContent;


    @SerializedName("records")
    @Expose
    private List<Record> records;

    public Content(List<MainContent> mainContent, List<Record> records) {
        this.mainContent = mainContent;
        this.records = records;
    }

    public List<MainContent> getMainContent() {
        return mainContent;
    }

    public void setMainContent(List<MainContent> mainContent) {
        this.mainContent = mainContent;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
