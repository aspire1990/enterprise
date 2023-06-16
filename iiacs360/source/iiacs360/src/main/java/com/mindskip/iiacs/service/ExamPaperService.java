package com.mindskip.iiacs.service;

import com.mindskip.iiacs.domain.ExamPaper;
import com.mindskip.iiacs.domain.User;
import com.mindskip.iiacs.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mindskip.iiacs.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.mindskip.iiacs.viewmodel.student.dashboard.PaperFilter;
import com.mindskip.iiacs.viewmodel.student.dashboard.PaperInfo;
import com.mindskip.iiacs.viewmodel.student.exam.ExamPaperPageVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ExamPaperService extends BaseService<ExamPaper> {

    PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM);

    PageInfo<ExamPaper> studentPage(ExamPaperPageVM requestVM);

    ExamPaper savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, User user);

    ExamPaperEditRequestVM examPaperToVM(Integer id);

    List<PaperInfo> indexPaper(PaperFilter paperFilter);

    Integer selectAllCount();

    List<Integer> selectMothCount();

    ExamPaperEditRequestVM examPaperToVMPageIndex(Integer id, Integer pageIndex);

    List<ExamPaper> selectExamPaperAll();

}
