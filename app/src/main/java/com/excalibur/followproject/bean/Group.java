package com.excalibur.followproject.bean;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lieniu on 2017/9/29.
 */

@Entity
public class Group implements Serializable {

    Long id;
    String name;
    String message;
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 689505426)
    public Group(Long id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }
    @Generated(hash = 117982048)
    public Group() {
    }
}
