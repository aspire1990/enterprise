package com.mindskip.iiacs.controller.student;

import com.mindskip.iiacs.base.BaseApiController;
import com.mindskip.iiacs.base.RestResponse;
import com.mindskip.iiacs.domain.ExamPaperQuestionCustomerAnswer;
import com.mindskip.iiacs.domain.Subject;
import com.mindskip.iiacs.domain.TextContent;
import com.mindskip.iiacs.domain.question.QuestionObject;
import com.mindskip.iiacs.service.ExamPaperQuestionCustomerAnswerService;
import com.mindskip.iiacs.service.QuestionService;
import com.mindskip.iiacs.service.SubjectService;
import com.mindskip.iiacs.service.TextContentService;
import com.mindskip.iiacs.utility.DateTimeUtil;
import com.mindskip.iiacs.utility.HtmlUtil;
import com.mindskip.iiacs.utility.JsonUtil;
import com.mindskip.iiacs.utility.PageInfoHelper;
import com.mindskip.iiacs.viewmodel.admin.question.QuestionEditRequestVM;
import com.mindskip.iiacs.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.mindskip.iiacs.viewmodel.student.question.answer.QuestionAnswerVM;
import com.mindskip.iiacs.viewmodel.student.question.answer.QuestionPageStudentRequestVM;
import com.mindskip.iiacs.viewmodel.student.question.answer.QuestionPageStudentResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("StudentQuestionAnswerController")
@RequestMapping(value = "/api/student/question/answer")
public class QuestionAnswerController extends BaseApiController {

    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final QuestionService questionService;
    private final TextContentService textContentService;
    private final SubjectService subjectService;

    @Autowired
    public QuestionAnswerController(ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, QuestionService questionService, TextContentService textContentService, SubjectService subjectService) {
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.questionService = questionService;
        this.textContentService = textContentService;
        this.subjectService = subjectService;
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionPageStudentResponseVM>> pageList(@RequestBody QuestionPageStudentRequestVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperQuestionCustomerAnswer> pageInfo = examPaperQuestionCustomerAnswerService.studentPage(model);
        PageInfo<QuestionPageStudentResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            Subject subject = subjectService.selectById(q.getSubjectId());
            QuestionPageStudentResponseVM vm = modelMapper.map(q, QuestionPageStudentResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            TextContent textContent = textContentService.selectById(q.getQuestionTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            vm.setShortTitle(clearHtml);
            vm.setSubjectName(subject.getName());
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionAnswerVM> select(@PathVariable Integer id) {
        QuestionAnswerVM vm = new QuestionAnswerVM();
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.selectById(id);
        ExamPaperSubmitItemVM questionAnswerVM = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(examPaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(examPaperQuestionCustomerAnswer.getQuestionId());
        vm.setQuestionVM(questionVM);
        vm.setQuestionAnswerVM(questionAnswerVM);
        return RestResponse.ok(vm);
    }

}
