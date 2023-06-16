package com.mindskip.iiacs.service;

import com.mindskip.iiacs.domain.TaskExam;
import com.mindskip.iiacs.domain.User;
import com.mindskip.iiacs.viewmodel.admin.task.TaskPageRequestVM;
import com.mindskip.iiacs.viewmodel.admin.task.TaskRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TaskExamService extends BaseService<TaskExam> {

    PageInfo<TaskExam> page(TaskPageRequestVM requestVM);

    void edit(TaskRequestVM model, User user);

    TaskRequestVM taskExamToVM(Integer id);

    List<TaskExam> getByGradeLevel(Integer gradeLevel);
}
