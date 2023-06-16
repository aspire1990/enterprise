package com.mindskip.iiacs.controller.student;

import com.mindskip.iiacs.base.BaseApiController;
import com.mindskip.iiacs.base.RestResponse;
import com.mindskip.iiacs.domain.ExamPaper;
import com.mindskip.iiacs.service.ExamPaperAnswerService;
import com.mindskip.iiacs.service.ExamPaperService;
import com.mindskip.iiacs.utility.DateTimeUtil;
import com.mindskip.iiacs.utility.PageInfoHelper;
import com.mindskip.iiacs.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mindskip.iiacs.viewmodel.student.exam.ExamPaperPageResponseVM;
import com.mindskip.iiacs.viewmodel.student.exam.ExamPaperPageVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
public class ExamPaperController extends BaseApiController {

    private final ExamPaperService examPaperService;
    private final ExamPaperAnswerService examPaperAnswerService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ExamPaperController(ExamPaperService examPaperService, ExamPaperAnswerService examPaperAnswerService, ApplicationEventPublisher eventPublisher) {
        this.examPaperService = examPaperService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.eventPublisher = eventPublisher;
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(@RequestBody @Valid ExamPaperPageVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.studentPage(model);
        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperPageResponseVM vm = modelMapper.map(e, ExamPaperPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    // by zhm 添加学生获取页数的代码
    @RequestMapping(value = "/selectPageIndex", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@RequestBody @Valid ExamPaperPageVM model) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVMPageIndex(model.getId(), model.getPageIndex());
        if (vm.getTitleItems() == null && vm.getLimitDateTime() == null) {
            return RestResponse.fail(-1, "试卷已做完");
        }
        return RestResponse.ok(vm);
    }
}
