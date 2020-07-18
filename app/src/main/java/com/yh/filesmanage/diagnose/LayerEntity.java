package com.yh.filesmanage.diagnose;

import java.util.List;

public class LayerEntity {

    private String index;
    private String state;
    private List<FileInfo> items;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<FileInfo> getItems() {
        return items;
    }

    public void setItems(List<FileInfo> items) {
        this.items = items;
    }
}
