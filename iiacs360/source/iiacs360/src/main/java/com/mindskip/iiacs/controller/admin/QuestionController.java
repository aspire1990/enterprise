package com.mindskip.iiacs.controller.admin;

import com.mindskip.iiacs.base.BaseApiController;
import com.mindskip.iiacs.base.PoiUtil;
import com.mindskip.iiacs.base.RestResponse;
import com.mindskip.iiacs.base.SystemCode;
import com.mindskip.iiacs.domain.Question;
import com.mindskip.iiacs.domain.TextContent;
import com.mindskip.iiacs.domain.enums.QuestionTypeEnum;
import com.mindskip.iiacs.domain.question.QuestionObject;
import com.mindskip.iiacs.service.QuestionService;
import com.mindskip.iiacs.service.TextContentService;
import com.mindskip.iiacs.service.impl.QuestionServiceImpl;
import com.mindskip.iiacs.utility.*;
import com.mindskip.iiacs.viewmodel.admin.question.QuestionEditItemVM;
import com.mindskip.iiacs.viewmodel.admin.question.QuestionEditRequestVM;
import com.mindskip.iiacs.viewmodel.admin.question.QuestionPageRequestVM;
import com.mindskip.iiacs.viewmodel.admin.question.QuestionResponseVM;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController("AdminQuestionController")
@RequestMapping(value = "/api/admin/question")
public class QuestionController extends BaseApiController {

    private final QuestionService questionService;
    private final TextContentService textContentService;
    private final static String EXT = "jpg,jpeg,gif,png";
    private final static Integer MAX_SIZE = 1024*1024*10;

//    @Value("${iiacs.data-filter.path}")
    private String filePath = this.getClass().getResource("/").getPath();
//    private  String filePath = "/opt/app/iiacs/excel/";
//    private String filterPath = this.getClass().getResource("").getPath();//System.getProperty("java.class.path");

    @Autowired
    public QuestionController(QuestionService questionService, TextContentService textContentService) {
        this.questionService = questionService;
        this.textContentService = textContentService;
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionResponseVM>> pageList(@RequestBody QuestionPageRequestVM model) {
        PageInfo<Question> pageInfo = questionService.page(model);
        PageInfo<QuestionResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            QuestionResponseVM vm = modelMapper.map(q, QuestionResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            vm.setScore(ExamUtil.scoreToVM(q.getScore()));
            TextContent textContent = textContentService.selectById(q.getInfoTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            vm.setShortTitle(clearHtml);
            return vm;
        });
        return RestResponse.ok(page);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody @Valid QuestionEditRequestVM model) {
        RestResponse validQuestionEditRequestResult = validQuestionEditRequestVM(model);
        if (validQuestionEditRequestResult.getCode() != SystemCode.OK.getCode()) {
            return validQuestionEditRequestResult;
        }

        if (null == model.getId()) {
            questionService.insertFullQuestion(model, getCurrentUser().getId());
        } else {
            questionService.updateFullQuestion(model);
        }

        return RestResponse.ok();
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionEditRequestVM> select(@PathVariable Integer id) {
        QuestionEditRequestVM newVM = questionService.getQuestionEditRequestVM(id);
        return RestResponse.ok(newVM);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        Question question = questionService.selectById(id);
        question.setDeleted(true);
        questionService.updateByIdFilter(question);
        return RestResponse.ok();
    }

    private RestResponse validQuestionEditRequestVM(QuestionEditRequestVM model) {
        int qType = model.getQuestionType().intValue();
        boolean requireCorrect = qType == QuestionTypeEnum.SingleChoice.getCode() || qType == QuestionTypeEnum.TrueFalse.getCode();
        if (requireCorrect) {
            if (StringUtils.isBlank(model.getCorrect())) {
                String errorMsg = ErrorUtil.parameterErrorFormat("correct", "不能为空");
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }

        if (qType == QuestionTypeEnum.GapFilling.getCode()) {
            Integer fillSumScore = model.getItems().stream().mapToInt(d -> ExamUtil.scoreFromVM(d.getScore())).sum();
            Integer questionScore = ExamUtil.scoreFromVM(model.getScore());
            if (!fillSumScore.equals(questionScore)) {
                String errorMsg = ErrorUtil.parameterErrorFormat("score", "空分数和与题目总分不相等");
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }
        return RestResponse.ok();
    }

    @RequestMapping(value = "/uploadQuestionExcel", method = RequestMethod.POST)
    public RestResponse uploadQuestionExcet(@RequestParam(name = "file", required = false) MultipartFile file) throws IOException {

        List<QuestionEditRequestVM> vmList = ImportExcelData(file);
        for (QuestionEditRequestVM item : vmList) {

            RestResponse validQuestionEditRequestResult = validQuestionEditRequestVM(item);
            if (validQuestionEditRequestResult.getCode() != SystemCode.OK.getCode()) {
                return validQuestionEditRequestResult;
            }

            if (null == item.getId()) {
                questionService.insertFullQuestion(item, getCurrentUser().getId());
            } else {
                questionService.updateFullQuestion(item);
            }

        }

        return RestResponse.ok();
    }


    private List<QuestionEditRequestVM> ImportExcelData(MultipartFile file) throws IOException {

        if (file == null) {
            return null;
        }

        if (file.getSize() > MAX_SIZE) {
            return null;
        }

        // 文件后缀检查
        String fileName = file.getOriginalFilename().substring(
                0, file.getOriginalFilename().lastIndexOf("."));
        String suffix = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!suffix.equals("xls") && !suffix.equals("xlsx")) {
            return null;
        }

        // 存储文件目录
        File savePath = new File(filePath);
        if (!savePath.exists()) {
            //若不存在该目录，则创建目录
            savePath.mkdirs();
        }

        // 通过UUID生成唯一文件名
        String monthPath = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String saveFileName = String.format("%s-%s.%s", fileName, monthPath,suffix);

        // 将文件保存指定目录
        String desFile = String.format("%s/%s", savePath, saveFileName);
        File tempFile = new File(desFile);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        file.transferTo(new File(desFile));

        List<QuestionEditRequestVM> res = new ArrayList<>();

        // 读取文件
        List<Map<String, Object>> srcList = PoiUtil.importExcel(desFile, 0);
        for (Map<String, Object> item : srcList) {
            QuestionEditRequestVM vm = new QuestionEditRequestVM();
            for (String key : item.keySet()) {
                if (key.equals("id")) {

                    vm.setId(null);
                } else if (key.equals("questionType")) {

                    String temp = item.get(key).toString();
                    if (temp == null || temp.isEmpty()) {
                        continue;
                    }
                    Double doubleObj = Double.parseDouble(temp);
                    vm.setQuestionType(doubleObj.intValue());
                } else if (key.equals("subjectId")) {

                    String temp = item.get(key).toString();
                    if (temp == null || temp.isEmpty()) {
                        continue;
                    }
                    Double doubleObj = Double.parseDouble(temp);
                    vm.setSubjectId(doubleObj.intValue());
                } else if (key.equals("title")) {

                    String temp = item.get(key).toString();
                    if (temp.isEmpty()) {
                        break;
                    }
                    vm.setTitle(temp);
                } else if (key.equals("items")) {

                    if (item.get(key).toString().equals("judgment")) {
                        List<QuestionEditItemVM> editItemList = new ArrayList<>();
                        QuestionEditItemVM vmNum1 = new QuestionEditItemVM();
                        vmNum1.setPrefix("A");
                        vmNum1.setContent("是");
                        QuestionEditItemVM vmNum2 = new QuestionEditItemVM();
                        vmNum2.setPrefix("B");
                        vmNum2.setContent("否");
                        editItemList.add(vmNum1);
                        editItemList.add(vmNum2);
                        vm.setItems(editItemList);
                    }
                } else if (key.equals("analyze")) {

                    vm.setAnalyze(item.get(key).toString());
                } else if (key.equals("correctArray")) {

                    vm.setCorrectArray(null);
                } else if (key.equals("correct")) {

                    vm.setCorrect(item.get(key).toString());
                } else if (key.equals("score")) {

                    String temp = item.get(key).toString();
                    if (temp == null || temp.isEmpty()) {
                        continue;
                    }
                    Double doubleObj = Double.parseDouble(temp);
                    int tempScore = doubleObj.intValue();
                    vm.setScore(String.valueOf(tempScore));
                } else if (key.equals("difficult")) {

                    String temp = item.get(key).toString();
                    if (temp == null || temp.isEmpty()) {
                        continue;
                    }
                    Double doubleObj = Double.parseDouble(temp);
                    vm.setDifficult(doubleObj.intValue());
                } else if (key.equals("itemOrder")) {

                    String temp = item.get(key).toString();
                    if (temp == null || temp.isEmpty()) {
                        continue;
                    }
                    Double doubleObj = Double.parseDouble(temp);
                    vm.setItemOrder(doubleObj.intValue());

                } else {
                    return  res;
                }
            }
            res.add(vm);
        }

        return res;
    }
}
