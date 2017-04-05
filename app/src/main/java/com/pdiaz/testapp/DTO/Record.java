package com.pdiaz.testapp.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class Record {

    @SerializedName("attributes")
    @Expose
    private Attribute attribute;

    public Record(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
