package com.yupi.fishoj.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.fishoj.common.ErrorCode;
import com.yupi.fishoj.exception.BusinessException;
import com.yupi.fishoj.mapper.QuestionSubmitMapper;
import com.yupi.fishoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.fishoj.model.entity.Question;
import com.yupi.fishoj.model.entity.QuestionSubmit;
import com.yupi.fishoj.model.entity.User;
import com.yupi.fishoj.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.fishoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.fishoj.service.QuestionService;
import com.yupi.fishoj.service.QuestionSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author abc
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2026-03-26 19:52:27
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // todo 校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());

        // 设置初始化判题状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交失败");
        }
        return questionSubmit.getId();
    }
    
}




