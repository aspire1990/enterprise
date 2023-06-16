package com.mindskip.iiacs.viewmodel.admin.education;

import com.mindskip.iiacs.base.BasePage;



public class SubjectPageRequestVM extends BasePage {
    private Integer id;
//    private Integer level;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Integer getLevel() {
//        return level;
//    }
//
//    public void setLevel(Integer level) {
//        this.level = level;
//    }
    public String getName() {
    return name;
}

    public void setLevel(String name) {
        this.name = name;
    }
}
