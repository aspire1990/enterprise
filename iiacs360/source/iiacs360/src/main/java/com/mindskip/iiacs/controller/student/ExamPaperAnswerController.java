package com.mindskip.iiacs.controller.student;

import com.mindskip.iiacs.base.BaseApiController;
import com.mindskip.iiacs.base.RestResponse;
import com.mindskip.iiacs.domain.*;
import com.mindskip.iiacs.domain.enums.ExamPaperAnswerStatusEnum;
import com.mindskip.iiacs.domain.enums.UserStatusEnum;
import com.mindskip.iiacs.event.CalculateExamPaperAnswerCompleteEvent;
import com.mindskip.iiacs.event.UserEvent;
import com.mindskip.iiacs.service.*;
import com.mindskip.iiacs.utility.DateTimeUtil;
import com.mindskip.iiacs.utility.ExamUtil;
import com.mindskip.iiacs.utility.PageInfoHelper;
import com.mindskip.iiacs.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mindskip.iiacs.viewmodel.student.exam.ExamPaperReadVM;
import com.mindskip.iiacs.viewmodel.student.exam.ExamPaperSubmitVM;
import com.mindskip.iiacs.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.mindskip.iiacs.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.InternetAddressEditor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController("StudentExamPaperAnswerController")
@RequestMapping(value = "/api/student/exampaper/answer")
public class ExamPaperAnswerController extends BaseApiController {

    private final ExamPaperAnswerService examPaperAnswerService;
    private final ExamPaperService examPaperService;
    private final SubjectService subjectService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;


    @Autowired
    public ExamPaperAnswerController(ExamPaperAnswerService examPaperAnswerService, ExamPaperService examPaperService, SubjectService subjectService, ApplicationEventPublisher eventPublisher, UserService userService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.examPaperService = examPaperService;
        this.subjectService = subjectService;
        this.eventPublisher = eventPublisher;
        this.userService = userService;


    }


    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageList(@RequestBody @Valid ExamPaperAnswerPageVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperAnswer> pageInfo = examPaperAnswerService.studentPage(model);
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
            Subject subject = subjectService.selectById(vm.getSubjectId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/answerSubmit", method = RequestMethod.POST)
    public RestResponse answerSubmit(@RequestBody @Valid ExamPaperSubmitVM examPaperSubmitVM) {
        System.out.println("zhm -1-1-1");
        Integer timeIsOver = examPaperSubmitVM.getTimeIsOver();
        User user = getCurrentUser();
        ExamPaperAnswerInfo examPaperAnswerInfo = examPaperAnswerService.calculateExamPaperAnswer(examPaperSubmitVM, user);
        if (null == examPaperAnswerInfo) {
            return RestResponse.fail(2, "试卷不能重复做");
        }
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        Integer userScore = examPaperAnswer.getUserScore();
        String scoreVm = ExamUtil.scoreToVM(userScore);
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 提交试卷：" + examPaperAnswerInfo.getExamPaper().getName()
                + " 得分：" + scoreVm
                + " 耗时：" + ExamUtil.secondToVM(examPaperAnswer.getDoTime());
        userEventLog.setContent(content);
        userEventLog.setTimes(0);
        userEventLog.setScore(Integer.valueOf(scoreVm));
        userEventLog.setFinish(0);
        eventPublisher.publishEvent(new CalculateExamPaperAnswerCompleteEvent(examPaperAnswerInfo));
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        if (timeIsOver == 1 && examPaperAnswer.getPaperName().indexOf("视频") == -1) {
            return RestResponse.fail(-1, "时间到，请考生离开座位");
        }

        // 视频题，若用户分数为0，直接提示用户淘汰信息
        if (examPaperAnswer.getPaperName().indexOf("视频") != -1) {
            if (examPaperAnswer.getUserScore() <= 0) {
                // 视频试卷答题失败
                user.setStatus(UserStatusEnum.Disable.getCode());
                user.setModifyTime(new Date());
                userService.updateByIdFilter(user);
                return RestResponse.fail(-2, "答题错误，请等待老师安排退场");
            } else {
                // 试卷Id+1
                int newExamPaperId = examPaperAnswer.getExamPaperId() + 1;
                ExamPaper newPaperObje = examPaperService.selectById(newExamPaperId);
                if (newPaperObje.getName().indexOf("视频") != -1) {
                    String httpMsg = "http://localhost:8001/#/do?id=" + newPaperObje.getId().toString();
                    return RestResponse.fail(2, httpMsg);
                }
            }
        }
        return RestResponse.ok(scoreVm);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody @Valid ExamPaperSubmitVM examPaperSubmitVM) {
        boolean notJudge = examPaperSubmitVM.getAnswerItems().stream().anyMatch(i -> i.getDoRight() == null && i.getScore() == null);
        if (notJudge) {
            return RestResponse.fail(2, "有未批改题目");
        }

        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(examPaperSubmitVM.getId());
        ExamPaperAnswerStatusEnum examPaperAnswerStatusEnum = ExamPaperAnswerStatusEnum.fromCode(examPaperAnswer.getStatus());
        if (examPaperAnswerStatusEnum == ExamPaperAnswerStatusEnum.Complete) {
            return RestResponse.fail(3, "试卷已完成");
        }
        String score = examPaperAnswerService.judge(examPaperSubmitVM);
        User user = getCurrentUser();
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 批改试卷：" + examPaperAnswer.getPaperName() + " 得分：" + score;
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(score);
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperReadVM> read(@PathVariable Integer id) {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(id);
        ExamPaperReadVM vm = new ExamPaperReadVM();
        ExamPaperEditRequestVM paper = examPaperService.examPaperToVM(examPaperAnswer.getExamPaperId());
        ExamPaperSubmitVM answer = examPaperAnswerService.examPaperAnswerToVM(examPaperAnswer.getId());
        vm.setPaper(paper);
        vm.setAnswer(answer);
        return RestResponse.ok(vm);
    }


}
