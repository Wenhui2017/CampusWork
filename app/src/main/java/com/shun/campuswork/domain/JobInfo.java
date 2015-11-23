package com.shun.campuswork.domain;

/**
 * Created by shun99 on 2015/11/23.
 */
public class JobInfo {
    public String name;

    public JobInfo(){
    }

    public JobInfo(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
