package com.yh.filesmanage.diagnose;

import java.util.List;

/**
 * @创建者 ly
 * @创建时间 2020/7/17
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class ResponseList<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
