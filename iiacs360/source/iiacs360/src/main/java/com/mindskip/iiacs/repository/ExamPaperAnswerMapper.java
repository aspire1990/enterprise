package com.mindskip.iiacs.repository;

import com.mindskip.iiacs.domain.ExamPaperAnswer;
import com.mindskip.iiacs.domain.other.KeyValue;
import com.mindskip.iiacs.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExamPaperAnswerMapper extends BaseMapper<ExamPaperAnswer> {

    List<ExamPaperAnswer> studentPage(ExamPaperAnswerPageVM requestVM);

    Integer selectAllCount();

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    ExamPaperAnswer getByPidUid(@Param("pid") Integer paperId, @Param("uid") Integer uid);

    List<ExamPaperAnswer> adminPage(com.mindskip.iiacs.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM requestVM);
}
