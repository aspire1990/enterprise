package com.mindskip.iiacs.service;

import com.mindskip.iiacs.domain.Subject;
import com.mindskip.iiacs.viewmodel.admin.education.SubjectPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SubjectService extends BaseService<Subject> {

    List<Subject> getSubjectByName(String name);

    List<Subject> allSubject();

    PageInfo<Subject> page(SubjectPageRequestVM requestVM);
}
