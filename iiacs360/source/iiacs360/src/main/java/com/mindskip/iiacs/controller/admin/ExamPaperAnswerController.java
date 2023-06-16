package com.mindskip.iiacs.controller.admin;

import com.mindskip.iiacs.base.BaseApiController;
import com.mindskip.iiacs.base.RestResponse;
import com.mindskip.iiacs.domain.ExamPaper;
import com.mindskip.iiacs.domain.ExamPaperAnswer;
import com.mindskip.iiacs.domain.Subject;
import com.mindskip.iiacs.domain.User;
import com.mindskip.iiacs.domain.enums.UserStatusEnum;
import com.mindskip.iiacs.service.*;
import com.mindskip.iiacs.utility.DateTimeUtil;
import com.mindskip.iiacs.utility.ExamUtil;
import com.mindskip.iiacs.utility.PageInfoHelper;
import com.mindskip.iiacs.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.mindskip.iiacs.viewmodel.admin.paper.ExamPaperAnswerPageRequestVM;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController("AdminExamPaperAnswerController")
@RequestMapping(value = "/api/admin/examPaperAnswer")
public class ExamPaperAnswerController extends BaseApiController {

    private final ExamPaperAnswerService examPaperAnswerService;
    private final SubjectService subjectService;
    private final UserService userService;
    private final ExamPaperService examPaperService;

    private final Integer FIRSTOUTUSERS = 1;
    private final Integer THREEOUTUSERS = 1;


    @Autowired
    public ExamPaperAnswerController(ExamPaperAnswerService examPaperAnswerService, SubjectService subjectService, UserService userService, ExamPaperService examPaperService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.examPaperService = examPaperService;

    }


    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageJudgeList(@RequestBody ExamPaperAnswerPageRequestVM model) {
        int srcPageIndex = model.getPageIndex();
        int srcPageSize = model.getPageSize();
        model.setPageSize(1000000);
        PageInfo<ExamPaperAnswer> pageInfo = examPaperAnswerService.adminPage(model);
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
            Subject subject = subjectService.selectById(vm.getSubjectId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            User user = userService.selectById(e.getCreateUser());
            vm.setUserName(user.getUserName());
            vm.setUserStatus(user.getStatus());
            return vm;
        });


        // 过滤page内的list，学生和试卷一样的进行合并
        List<ExamPaperAnswerPageResponseVM> srcList = new ArrayList<ExamPaperAnswerPageResponseVM>();
        srcList = page.getList();

        List<ExamPaperAnswerPageResponseVM> desList = new ArrayList<ExamPaperAnswerPageResponseVM>();

        int itemNums = 0;
        for (ExamPaperAnswerPageResponseVM item : srcList) {
             ++itemNums;
            boolean isAddItem = true;
            for (int j = 0; j < desList.size(); ++j){
                if (desList.get(j).getPaperName().equals(item.getPaperName())
                        && desList.get(j).getUserName().equals(item.getUserName())) {

                    if (desList.get(j).getAnswerNums() == null) {
                        // 应为destList,第一次不进次循环，所以答题数实际上是从10开始
                        desList.get(j).setAnswerNums(0);
                    }
                    int questionCorrect = desList.get(j).getQuestionCorrect()
                            + item.getQuestionCorrect();

                    int answerNums = desList.get(j).getAnswerNums()+10;
                    desList.get(j).setAnswerNums(answerNums);

                    desList.get(j).setQuestionCorrect(questionCorrect);


                    int questionUserScore = Integer.valueOf(desList.get(j).getUserScore())
                            + Integer.valueOf(item.getUserScore());

                    desList.get(j).setUserScore(Integer.toString(questionUserScore));

                    try {
                        // 时间处理
                        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat formatTime = new SimpleDateFormat("mm分 ss秒");
                        //设置要读取的时间字符串格式
                        Date itemDoTime = formatTime.parse(item.getDoTime());
                        Date desListDoTime = formatTime.parse(desList.get(j).getDoTime());
                        if (itemDoTime.getTime() > desListDoTime.getTime()) {
                            desList.get(j).setDoTime(item.getDoTime());
                        }

                        Date itemCreateTime = formatDate.parse(item.getCreateTime());
                        Date desListCreateTime = formatDate.parse(desList.get(j).getCreateTime());
                        if (itemCreateTime.getTime() > desListCreateTime.getTime()) {
                            desList.get(j).setCreateTime(item.getCreateTime());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    isAddItem = false;
                    break;
                }
            }
            if (isAddItem == false) {
                continue;
            } else {
                item.setAnswerNums(10);
                desList.add(item);
            }
        }

        //        PageInfo<ExamPaperAnswerPageResponseVM> res = page;
//        res.setList(null);
        // 根据页数返回数据量
        int start = (srcPageIndex * srcPageSize) - srcPageSize;
        List<ExamPaperAnswerPageResponseVM> resList = new ArrayList<ExamPaperAnswerPageResponseVM>();
        for(; start < desList.size(); ++start){
            if (resList.size() >= srcPageSize) {
                break;
            }
            resList.add(desList.get(start));
        }


        page.setList(resList);

        page.setPageNum(srcPageIndex);
        page.setPageSize(srcPageSize);
        page.setSize(srcPageSize);
        page.setEndRow(srcPageSize*srcPageIndex);
        page.setTotal(desList.size());
        page.setPages(desList.size()/srcPageSize);

        page.setPrePage(srcPageIndex - 1);
        page.setNextPage(srcPageIndex + 1);

        // isFirstPage
        if (srcPageIndex == 1) {
            page.setIsFirstPage(true);
        } else {
            page.setIsFirstPage(false);
        }
        // isLastPage
        if ((desList.size() - (srcPageIndex*srcPageSize)) <= 0) {
            page.setIsFirstPage(true);
        } else {
            page.setIsFirstPage(false);
        }

        // hasPreviousPage
        if (srcPageIndex == 1) {
            page.setHasPreviousPage(false);
        } else {
            page.setHasPreviousPage(true);
        }

        // hasNextPage
        if ((desList.size() - (srcPageIndex * srcPageSize)) > 0) {
            page.setHasNextPage(true);
        } else {
            page.setHasNextPage(false);
        }

        page.setNavigateFirstPage(1);

        float floatSize = desList.size() / srcPageSize;
        int intSize = desList.size() / srcPageSize;
        int desSize = 0;
        if ((floatSize - (float) intSize) > 0) {
            desSize = intSize + 1;
        } else {
            desSize = intSize;
        }

        page.setNavigateLastPage(desSize);


        int navigatePageNums[] = new int[desSize];
        for (int i = 1; i <= desSize; ++i) {
            navigatePageNums[i-1] = i;
        }
        page.setNavigatepageNums(navigatePageNums);

        // by zhm add 获取试卷列表，判断是否淘汰用户
        DoUserOut(page);

        return RestResponse.ok(page);
    }

    private void DoUserOut(PageInfo<ExamPaperAnswerPageResponseVM> page) {

        List<ExamPaperAnswerPageResponseVM> srcExamPaperList
                = new ArrayList<ExamPaperAnswerPageResponseVM>();
        srcExamPaperList.addAll(page.getList());
        List<ExamPaper> examPaperList = examPaperService.selectExamPaperAll();
        for (ExamPaper itemPaper : examPaperList) {
//            String paperLimitEndTime = DateTimeUtil.dateFormat(itemPaper.getLimitEndTime());
            Date nowTime = new Date(System.currentTimeMillis());
            long fiveSeconds = 5*1000;
            Date afterDate = new Date(nowTime.getTime() + fiveSeconds);
//            String currentTimeMillis = DateTimeUtil.dateFormat(afterDate);

            long millisecond = afterDate.getTime() - itemPaper.getLimitEndTime().getTime();
            if (millisecond < 0) {
                continue;
            }
            if (itemPaper.getName().equals("积分赛")) {
                // 积分赛，淘汰用户
                PointsRaceOut(srcExamPaperList);

            } else if (itemPaper.getName().equals("淘汰赛")) {

            } else if (itemPaper.getName().equals("竞速赛")) {
                // 淘汰竞速赛用户
                SpeedRaceOut(srcExamPaperList);

            } else {}
        }

        return  ;
    }

    // 积分赛淘汰
    private void PointsRaceOut(List<ExamPaperAnswerPageResponseVM> pamExamPaperList) { //,PageInfo<ExamPaperAnswerPageResponseVM> examPaper
        List<ExamPaperAnswerPageResponseVM> srcExamPaperList
                = new ArrayList<ExamPaperAnswerPageResponseVM>();
        srcExamPaperList.addAll(pamExamPaperList);
        // 1. 淘汰没参加考试的全部淘汰
        List<User> removeUser = userService.getUsers();
        List<User> allStudent = userService.getUsers();
        for (int index = 0; index < removeUser.size(); ++index) {
            if (removeUser.get(index).getRealName().equals("管理员")) {
                removeUser.remove(index);
                allStudent.remove(index);
            }
        }

        for (ExamPaperAnswerPageResponseVM itemPaper : srcExamPaperList) {
            if (!itemPaper.getPaperName().equals("积分赛")){
                continue;
            }

            // 帅选未参加考试考生

            Iterator<User> itemRemoveUser = removeUser.iterator();
            while (itemRemoveUser.hasNext()) {
                User temp = itemRemoveUser.next();
                if (temp.getUserName().equals(itemPaper.getUserName())) {
                    itemRemoveUser.remove();
                }
            }
        }


        // 禁用没参加考试的考生
        for (User itemUser : removeUser) {
            itemUser.setStatus(UserStatusEnum.Disable.getCode());
            itemUser.setModifyTime(new Date());
            userService.updateByIdFilter(itemUser);
        }


        // 2. 剩余考生进行排序, 设置状态为禁用
        //https://www.jianshu.com/p/389d98e4d7cf
        int examUserCount = allStudent.size() - removeUser.size();
        // 参加考试的学生人数小于50,本轮就不需要进行淘汰
        if (examUserCount <= FIRSTOUTUSERS) {
            return;
        }

//        List<ExamPaperAnswerPageResponseVM> examPaperList = examPaper.getList();
        Iterator<ExamPaperAnswerPageResponseVM> itemExamPaper = srcExamPaperList.iterator();
        while (itemExamPaper.hasNext()) {
            ExamPaperAnswerPageResponseVM temp = itemExamPaper.next();
            if (!temp.getPaperName().equals("积分赛")) {
                itemExamPaper.remove();
            }
        }
//        int examPaperListSize = examPaperList.size();
//        for (int i = 0; i < examPaperListSize; ++i) {
//            if (examPaperList.get(i).getPaperName().equals("积分赛")) {
//                examPaperList.remove(i);
//            }
//        }

        srcExamPaperList.sort((o1, o2) -> Integer.valueOf(o1.getUserScore()) - Integer.valueOf(o2.getUserScore()));
        // 还需要淘汰的人数
        if ((examUserCount - FIRSTOUTUSERS) <= 0) {
            return;
        }
        for (int index = 0 ; index < FIRSTOUTUSERS; ++index) {
            String outUserName = srcExamPaperList.get(index).getUserName();
            for (User userItem : allStudent) {
                boolean isRemove = false;
                if (userItem.getUserName().equals(outUserName)) {
                    userItem.setStatus(UserStatusEnum.Disable.getCode());
                    userItem.setModifyTime(new Date());
                    userService.updateByIdFilter(userItem);
                    isRemove = true;
                }
                if (isRemove) {
                    break;
                }
            }
        }

        return;

    }

    // 竞速赛淘汰
    private void SpeedRaceOut(List<ExamPaperAnswerPageResponseVM> pamExamPaperList) {//PageInfo<ExamPaperAnswerPageResponseVM> examPaper) {
        List<ExamPaperAnswerPageResponseVM> srcExamPaperList
                = new ArrayList<ExamPaperAnswerPageResponseVM>();
        srcExamPaperList.addAll(pamExamPaperList);
        // 1. 淘汰没参加考试的全部淘汰
        List<User> removeUser = userService.getUsers();
        List<User> allStudent = userService.getUsers();
        for (int index = 0; index < removeUser.size(); ++index) {
            if (removeUser.get(index).getRealName().equals("管理员")) {
                removeUser.remove(index);
                allStudent.remove(index);
            }
        }

        for (ExamPaperAnswerPageResponseVM itemPaper : srcExamPaperList) {
            if (!itemPaper.getPaperName().equals("竞速赛")){
                continue;
            }

            // 筛选未参加考试考生
            Iterator<User> itemRemoveUser = removeUser.iterator();
            while (itemRemoveUser.hasNext()) {
                User temp = itemRemoveUser.next();
                if (temp.getUserName().equals(itemPaper.getUserName())) {
                    itemRemoveUser.remove();
                }
            }
        }


        // 禁用没参加考试的考生
        for (User itemUser : removeUser) {
            itemUser.setStatus(UserStatusEnum.Disable.getCode());
            itemUser.setModifyTime(new Date());
            userService.updateByIdFilter(itemUser);
        }


        // 2. 剩余考生进行排序, 设置状态为禁用
        //https://www.jianshu.com/p/389d98e4d7cf
//        List<User> allUser = userService.getUsers();
        int examUserCount = allStudent.size() - removeUser.size();
        // 参加考试的学生人数小于50,本轮就不需要进行淘汰
        if (examUserCount <= THREEOUTUSERS) {
            return;
        }

//        List<ExamPaperAnswerPageResponseVM> examPaperList = examPaper.getList();
        Iterator<ExamPaperAnswerPageResponseVM> itemExamPaper = srcExamPaperList.iterator();
        while (itemExamPaper.hasNext()) {
            ExamPaperAnswerPageResponseVM temp = itemExamPaper.next();
            if (!temp.getPaperName().equals("竞速赛")) {
                itemExamPaper.remove();
            }
        }

        // 加权，前10名提前交卷的考生，依次加分10，9，8，7....
        srcExamPaperList.sort((o1, o2) -> formatDatetoInt(o1.getDoTime()) - formatDatetoInt(o2.getDoTime()));

        int tenPoints = 10;
        Integer addPointsNums = (srcExamPaperList.size() - tenPoints > 0 )? tenPoints : srcExamPaperList.size();
        for (int i = 0; i < addPointsNums; ++i) {
            Integer score = Integer.valueOf(srcExamPaperList.get(i).getUserScore()) + tenPoints;
            srcExamPaperList.get(i).setUserScore(score.toString());
            --tenPoints;
        }

        // 还需要淘汰的人数
        if ((examUserCount - THREEOUTUSERS) <= 0) {
            return;
        }
        srcExamPaperList.sort((o1, o2) -> Integer.valueOf(o1.getUserScore()) - Integer.valueOf(o2.getUserScore()));
        for (int index = 0 ; index < THREEOUTUSERS; ++index) {
            String outUserName = srcExamPaperList.get(index).getUserName();
            for (User userItem : allStudent) {
                boolean isRemove = false;
                if (userItem.getUserName().equals(outUserName)) {
                    userItem.setStatus(UserStatusEnum.Disable.getCode());
                    userItem.setModifyTime(new Date());
                    userService.updateByIdFilter(userItem);
                    isRemove = true;
                }
                if (isRemove) {
                    break;
                }
            }
        }

        return;
    }


    private int formatDatetoInt(String src) {
        int result = 0;
        int hourNum = 0;
        int minuteNum = 0;
        int secondsNum = 0;
        if (src.indexOf("时") != -1) {
            String hour = StringUtils.substringBefore(src, "时").trim();
            src = StringUtils.substringBeforeLast(src, "时");
            hourNum = Integer.valueOf(hour);
        }

        if (src.indexOf("分") != -1) {
            String minute = StringUtils.substringBefore(src, "分").trim();
            src = StringUtils.substringBeforeLast(src, "分");
            minuteNum = Integer.valueOf(minute);
        }


        String seconds = StringUtils.substringBefore(src, "秒").trim();
        secondsNum = Integer.valueOf(seconds);
        result = (hourNum*3600) + (minuteNum*60) + (secondsNum);

        return result;
    }

}
