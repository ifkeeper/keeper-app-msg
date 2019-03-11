package com.mingrn.itumate.msg.web;

import com.mingrn.itumate.global.annotation.Checked;
import com.mingrn.itumate.global.annotation.ParamsIsNotNull;
import com.mingrn.itumate.global.result.ResponseMsgUtil;
import com.mingrn.itumate.global.result.Result;
import com.mingrn.itumate.msg.mail.enums.MailTemplateEnums;
import com.mingrn.itumate.msg.mail.service.MailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邮件发送
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-02-23 15:01
 */
@RestController
@RequestMapping("/mailSendApi")
public class MailSendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendController.class);

    @Resource(name = "mailSendService")
    private MailSendService mailSendService;

    /**
     * 发送简单文本邮件
     *
     * @param subject  邮件标题
     * @param msg      邮件文本信息
     * @param receiver 接收者,支持数组
     */
    @Checked
    @PostMapping("/sendTextMessage")
    public Result sendTextMessage(@RequestParam @ParamsIsNotNull String subject,
                                  @RequestParam @ParamsIsNotNull String msg, @RequestParam @ParamsIsNotNull String... receiver) {
        mailSendService.sendTextMessage(subject, msg, receiver);
        return ResponseMsgUtil.success();
    }

    /**
     * 发送 HTML 邮件
     *
     * @param subject     邮件标题
     * @param htmlContent 邮件HTML 信息
     * @param receiver    接收者,支持数组
     */
    @Checked
    @PostMapping("/sendHtmlMessage")
    public Result sendHtmlMessage(@RequestParam @ParamsIsNotNull String subject,
                                  @RequestParam @ParamsIsNotNull String htmlContent,
                                  @RequestParam @ParamsIsNotNull String... receiver) {
        mailSendService.sendHtmlMessage(subject, htmlContent, receiver);
        return ResponseMsgUtil.success();
    }

    /**
     * 发送 HTML 模板邮件
     *
     * @param subject       邮件标题
     * @param templateEnums 邮件HTML模板 信息
     * @param receiver      接收者,支持数组
     */
    @Checked
    @PostMapping("/sendHtmlMessageWithFtl")
    public Result sendHtmlMessageWithFtl(@RequestParam @ParamsIsNotNull String subject,
                                         @RequestParam @ParamsIsNotNull MailTemplateEnums templateEnums,
                                         @RequestParam @ParamsIsNotNull String... receiver) {
        mailSendService.sendHtmlMessage(subject, templateEnums, receiver);
        return ResponseMsgUtil.success();
    }
}
