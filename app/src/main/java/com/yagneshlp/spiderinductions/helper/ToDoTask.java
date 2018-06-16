package com.yagneshlp.spiderinductions.helper;

public class ToDoTask {
    public String title,subTitle,priority;

    public ToDoTask(){
        title =" ";
        subTitle = " ";
        priority=" ";

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitlee() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPriority() { return priority;    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


}
