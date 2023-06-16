package com.mindskip.iiacs.service.impl;

import com.mindskip.iiacs.domain.TextContent;
import com.mindskip.iiacs.domain.enums.ExamPaperTypeEnum;
import com.mindskip.iiacs.domain.exam.ExamPaperQuestionItemObject;
import com.mindskip.iiacs.domain.exam.ExamPaperTitleItemObject;
import com.mindskip.iiacs.domain.other.KeyValue;
import com.mindskip.iiacs.repository.ExamPaperMapper;
import com.mindskip.iiacs.repository.QuestionMapper;
import com.mindskip.iiacs.service.ExamPaperService;
import com.mindskip.iiacs.service.QuestionService;
import com.mindskip.iiacs.service.SubjectService;
import com.mindskip.iiacs.service.TextContentService;
import com.mindskip.iiacs.service.enums.ActionEnum;
import com.mindskip.iiacs.utility.DateTimeUtil;
import com.mindskip.iiacs.utility.JsonUtil;
import com.mindskip.iiacs.utility.ModelMapperSingle;
import com.mindskip.iiacs.utility.ExamUtil;
import com.mindskip.iiacs.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.mindskip.iiacs.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.mindskip.iiacs.viewmodel.admin.exam.ExamPaperTitleItemVM;
import com.mindskip.iiacs.viewmodel.admin.question.QuestionEditRequestVM;
import com.mindskip.iiacs.viewmodel.student.dashboard.PaperFilter;
import com.mindskip.iiacs.viewmodel.student.dashboard.PaperInfo;
import com.mindskip.iiacs.viewmodel.student.exam.ExamPaperPageVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.iiacs.domain.ExamPaper;
import com.mindskip.iiacs.domain.Question;
import com.mindskip.iiacs.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class ExamPaperServiceImpl extends BaseServiceImpl<ExamPaper> implements ExamPaperService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final TextContentService textContentService;
    private final QuestionService questionService;
    private final SubjectService subjectService;

    @Autowired
    public ExamPaperServiceImpl(ExamPaperMapper examPaperMapper, QuestionMapper questionMapper, TextContentService textContentService, QuestionService questionService, SubjectService subjectService) {
        super(examPaperMapper);
        this.examPaperMapper = examPaperMapper;
        this.questionMapper = questionMapper;
        this.textContentService = textContentService;
        this.questionService = questionService;
        this.subjectService = subjectService;
    }


    @Override
    public PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.page(requestVM));
    }

    @Override
    public PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.taskExamPage(requestVM));
    }

    @Override
    public PageInfo<ExamPaper> studentPage(ExamPaperPageVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.studentPage(requestVM));
    }


    /* param data 指定字符串
     * param str  需要定位的特殊字符或者字符串
     * param num  第n次出现
     * return 第n次出现的位置索引
     */
    public int getIndexOf(String data, String str, int num) {
        Pattern pattern = Pattern.compile(str);
        Matcher findMatcher = pattern.matcher(data);
        //标记遍历字符串的位置
        int indexNum = 0;
        while (findMatcher.find()) {
            indexNum++;
            if (indexNum == num){
                break;
            }
        }
        if (indexNum != num) {
            return -1;
        }
        return findMatcher.start();
    }

    @Override
    @Transactional
    public ExamPaper savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, User user) {
        ActionEnum actionEnum = (examPaperEditRequestVM.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        Date now = new Date();
        List<ExamPaperTitleItemVM> titleItemsVM = examPaperEditRequestVM.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
        String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);

        ExamPaper examPaper;
        if (actionEnum == ActionEnum.ADD) {
            examPaper = modelMapper.map(examPaperEditRequestVM, ExamPaper.class);
            TextContent frameTextContent = new TextContent(frameTextContentStr, now);
//            textContentService.insertByFilter(frameTextContent);
            // by zhm start add 遍历frameTextContent, 插入表t_text_content_paper

            String src = frameTextContent.getContent();
            // { 出现第二次，前边的所有字符
            Integer index_head = src.indexOf("{", 2);
            // ] 出现第一次，后边的位置
            Integer index_end = src.indexOf("]");
            String sub_head = src.substring(0, index_head);
            String sub_content = src.substring(index_head, index_end);
            String sub_end = src.substring(index_end);

            // 遍历 src_content , 10个}进行截取
            Integer pageIndex = 1;
//            String src_temp = src_content;
//            String res_content = "";
            Integer index_sub_init = getIndexOf(sub_content,"}", 10);
            if (index_sub_init != -1) {
                frameTextContent.setContent(sub_head+sub_content.substring(0, index_sub_init + 1)+sub_end);
                sub_content = sub_content.substring(index_sub_init + 2);
            } else {
                // 不足十道题
                frameTextContent.setContent(src);
                sub_content = "";
            }
            frameTextContent.setPageIndex(pageIndex);
            frameTextContent.setFrameTextContentId(0);
            textContentService.insertByFilterPaper(frameTextContent);

            // 第一次插入数据后，frame_text_content_id 为0 此处更新id的值
            frameTextContent.setFrameTextContentId(frameTextContent.getId());
            textContentService.updateByIdframeTextContentId(frameTextContent);

            // by zhm start end

            examPaper.setFrameTextContentId(frameTextContent.getId());
            Integer temp_frameTextContent_id = frameTextContent.getId();
            examPaper.setCreateTime(now);
            examPaper.setCreateUser(user.getId());
            examPaper.setDeleted(false);
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            examPaperMapper.insertSelective(examPaper);

            // 遍历数据，继续插入t_text_content_paper后面的数据
            while (true) {
                if (sub_content.isEmpty()) {
                    break;
                }
                Integer index = getIndexOf(sub_content,"}", 10);
                String res_content = "";
                if (index != -1) {
                    res_content = sub_content.substring(0, index + 1);
                    if (sub_content.length() > index +2 ){
                        sub_content = sub_content.substring(index+2);
                    } else {
                        sub_content = "";
                    }
                    // 处理temp
                } else {
                    // 直接处理src_temp
                    res_content = sub_content;
                    sub_content = "";
                }
                ++pageIndex;
                TextContent ftContent = new TextContent(sub_head + res_content + sub_end, now);
                ftContent.setPageIndex(pageIndex);
                ftContent.setFrameTextContentId(temp_frameTextContent_id);
                textContentService.insertByFilterPaper(ftContent);
            }

        } else {
            examPaper = examPaperMapper.selectByPrimaryKey(examPaperEditRequestVM.getId());
            TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
            frameTextContent.setContent(frameTextContentStr);
            textContentService.updateByIdFilter(frameTextContent);
            modelMapper.map(examPaperEditRequestVM, examPaper);
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            examPaperMapper.updateByPrimaryKeySelective(examPaper);
        }
        return examPaper;
    }

    @Override
    public ExamPaperEditRequestVM examPaperToVM(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(id);
        ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
//        vm.setLevel(examPaper.getGradeLevel());

        // by zhm start 搜索出来所有的content， 拼成如下串
//        [{"name":"标题paper1","questionItems":[{"id":4,"itemOrder":1},{"id":3,"itemOrder":2}]}]
        TextContent frameTextContent = textContentService.selectByIdPaper(examPaper.getFrameTextContentId());
        // by zhm end

//        TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> q.getId()))
                .collect(Collectors.toList());
        List<Question> questions = questionMapper.selectByIds(questionIds);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
            ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);
            List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            tTitleVM.setQuestionItems(questionItemsVM);
            return tTitleVM;
        }).collect(Collectors.toList());
        vm.setTitleItems(examPaperTitleItemVMS);
        vm.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }

    @Override
    public List<PaperInfo> indexPaper(PaperFilter paperFilter) {
        return examPaperMapper.indexPaper(paperFilter);
    }


    @Override
    public Integer selectAllCount() {
        return examPaperMapper.selectAllCount();
    }

    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = examPaperMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }

    private void examPaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
//        Integer gradeLevel = subjectService.levelBySubjectId(examPaperEditRequestVM.getSubjectId());
        Integer questionCount = titleItemsVM.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        Integer score = titleItemsVM.stream().
                flatMapToInt(t -> t.getQuestionItems().stream()
                        .mapToInt(q -> ExamUtil.scoreFromVM(q.getScore()))
                ).sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
//        examPaper.setGradeLevel(gradeLevel);
        List<String> dateTimes = examPaperEditRequestVM.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            examPaper.setLimitStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
            examPaper.setLimitEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }

    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = modelMapper.map(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = modelMapper.map(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }


    @Override
    public ExamPaperEditRequestVM examPaperToVMPageIndex(Integer id, Integer pageIndex) {
        ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(id);
        ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
        TextContent frameTextContent = textContentService.selectByFtcidPageIndex(examPaper.getFrameTextContentId(), pageIndex);
        // 所有题都做完了
        if (frameTextContent == null) {
            vm.setTitleItems(null);
            vm.setScore("0");
            vm.setLimitDateTime(null);
            return vm;
        }
//        TextContent frameTextContent = textContentService.selectById(1);

        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> q.getId()))
                .collect(Collectors.toList());
        List<Question> questions = questionMapper.selectByIds(questionIds);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
            ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);
            List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            tTitleVM.setQuestionItems(questionItemsVM);
            return tTitleVM;
        }).collect(Collectors.toList());
        vm.setTitleItems(examPaperTitleItemVMS);
        vm.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }

    @Override
    public List<ExamPaper> selectExamPaperAll() {
        return examPaperMapper.selectExamPaperAll();
    }
}
