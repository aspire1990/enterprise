package com.mindskip.iiacs.repository;

import com.mindskip.iiacs.domain.Subject;
import com.mindskip.iiacs.viewmodel.admin.education.SubjectPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper  extends BaseMapper<Subject> {

    List<Subject> getSubjectByName(String name);

    List<Subject> allSubject();

    List<Subject> page(SubjectPageRequestVM requestVM);

}
