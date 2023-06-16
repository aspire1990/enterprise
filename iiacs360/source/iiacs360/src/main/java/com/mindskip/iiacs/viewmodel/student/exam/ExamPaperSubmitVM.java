package com.mindskip.iiacs.viewmodel.student.exam;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


public class ExamPaperSubmitVM {

    @NotNull
    private Integer id;

    @NotNull
    private Integer doTime;

    private String score;

    @NotNull
    @Valid
    private List<ExamPaperSubmitItemVM> answerItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoTime() {
        return doTime;
    }

    public void setDoTime(Integer doTime) {
        this.doTime = doTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<ExamPaperSubmitItemVM> getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(List<ExamPaperSubmitItemVM> answerItems) {
        this.answerItems = answerItems;
    }

    private Integer pageIndex;
    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }


    private Integer timeIsOver;
    public Integer getTimeIsOver() {
        return timeIsOver;
    }

    public void setTimeIsOver(Integer timeIsOver) {
        this.timeIsOver = timeIsOver;
    }


}
