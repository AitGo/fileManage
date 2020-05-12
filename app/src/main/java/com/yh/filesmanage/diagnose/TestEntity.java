package com.yh.filesmanage.diagnose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "test")
public class TestEntity {

    @Id
    private String id;

    @Generated(hash = 697670540)
    public TestEntity(String id) {
        this.id = id;
    }

    @Generated(hash = 1020448049)
    public TestEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
