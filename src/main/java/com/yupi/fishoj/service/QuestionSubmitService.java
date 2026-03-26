package com.yupi.fishoj.service;

import com.yupi.fishoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.fishoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.fishoj.model.entity.User;

/**
 * @author abc
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2026-03-26 19:52:27
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
}
