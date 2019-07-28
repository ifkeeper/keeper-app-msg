package com.mingrn.itumate.msg.web;

import com.mingrn.itumate.global.annotation.Checked;
import com.mingrn.itumate.global.annotation.ParamsIsNotNull;
import com.mingrn.itumate.global.result.ResponseMsgUtil;
import com.mingrn.itumate.global.result.Result;
import com.mingrn.itumate.msg.mail.enums.MailTemplateEnums;
import com.mingrn.itumate.msg.mail.service.MailSendService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 邮件发送
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-02-23 15:01
 */
@RestController
@RequestMapping("/mailSendApi")
@Api(description = "Mail Message Send API")
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
    @ApiOperation(value = "发送简单文本邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "subject", value = "邮件主题"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "msg", value = "邮件文本信息"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiver", value = "接收者邮件,支持数组")
    })
    public Result sendTextMessage(@RequestParam @ParamsIsNotNull String subject,
                                  @RequestParam @ParamsIsNotNull String msg, @RequestParam @ParamsIsNotNull String... receiver) {
        mailSendService.sendTextMessage(subject, msg, receiver);
        return ResponseMsgUtil.success();
    }

    /**
     * @param subject     邮件标题
     * @param htmlContent 邮件HTML 信息
     * @param receiver    接收者,支持数组
     */
    @Checked
    @PostMapping("/sendHtmlMessage")
    @ApiOperation(value = "发送 HTML 邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "subject", value = "邮件主题"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "htmlContent", value = "HTML内容"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiver", value = "接收者邮件,支持数组")
    })
    public Result sendHtmlMessage(@RequestParam @ParamsIsNotNull String subject,
                                  @RequestParam @ParamsIsNotNull String htmlContent,
                                  @RequestParam @ParamsIsNotNull String... receiver) {
        mailSendService.sendHtmlMessage(subject, htmlContent, receiver);
        return ResponseMsgUtil.success();
    }

    /**
     * 发送 HTML 模板邮件
     *
     * @param subject          邮件标题
     * @param receiverMail     接收者邮件
     * @param receiverNickName 接收者用户名称
     * @param templateEnums    邮件HTML模板 信息
     */
    @Checked
    @PostMapping("/sendHtmlMessageWithTemplate")
    @ApiOperation(value = "发送 HTML 模板邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "subject", value = "邮件主题"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiverMail", value = "接收者邮件"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiverNickName", value = "接收者用户名称"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = MailTemplateEnums.class, required = true, name = "templateEnums", value = "邮件模板")
    })
    public Result sendHtmlMessageWithTemplate(@RequestParam @ParamsIsNotNull String subject,
                                              @RequestParam @ParamsIsNotNull String receiverMail,
                                              @RequestParam @ParamsIsNotNull String receiverNickName,
                                              @RequestParam @ParamsIsNotNull MailTemplateEnums templateEnums) {

        Map<String, Object> data = new HashMap<>(1);
        data.put("userName", receiverNickName);

        mailSendService.sendHtmlMessage(subject, templateEnums, data, receiverMail);
        return ResponseMsgUtil.success();
    }


    /**
     * 发送带附件 HTML 邮件
     *
     * @param subject       邮件标题
     * @param receiverMail  接收者邮件
     * @param htmlContent   HTML邮件内容
     * @param multipartFile 附件
     */
    @Checked
    @PostMapping(value = "/sendAttachmentsMessage", consumes = "multipart/*", headers = "Content-Type=multipart/form-data")
    @ApiOperation(value = "发送带附件 HTML 模板邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "subject", value = "邮件主题"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiverMail", value = "接收者邮件"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiverNickName", value = "接收者用户名称"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "htmlContent", value = "HTML邮件内容")
    })
    public Result sendAttachmentsMessage(@RequestParam @ParamsIsNotNull String subject,
                                         @RequestParam @ParamsIsNotNull String receiverMail,
                                         @RequestParam @ParamsIsNotNull String htmlContent,
                                         @ApiParam(value = "上传文件") @RequestParam(name = "multipartFile", required = false) MultipartFile multipartFile) throws IOException, MessagingException {

        mailSendService.sendAttachmentsMessage(subject, htmlContent, new MultipartFile[]{multipartFile}, true,receiverMail);
        return ResponseMsgUtil.success();
    }


    /**
     * 发送带附件 HTML 模板邮件
     *
     * @param subject          邮件标题
     * @param receiverMail     接收者邮件
     * @param receiverNickName 接收者用户名称
     * @param templateEnums    邮件HTML模板 信息
     * @param multipartFile    附件
     */
    @Checked
    @PostMapping(value = "/sendAttachmentsMessageWithTemplate", consumes = "multipart/*", headers = "Content-Type=multipart/form-data")
    @ApiOperation(value = "发送带附件 HTML 模板邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "subject", value = "邮件主题"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiverMail", value = "接收者邮件"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, required = true, name = "receiverNickName", value = "接收者用户名称"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = MailTemplateEnums.class, required = true, name = "templateEnums", value = "邮件模板")
    })
    public Result sendAttachmentsMessageWithTemplate(@RequestParam @ParamsIsNotNull String subject,
                                                     @RequestParam @ParamsIsNotNull String receiverMail,
                                                     @RequestParam @ParamsIsNotNull String receiverNickName,
                                                     @RequestParam @ParamsIsNotNull MailTemplateEnums templateEnums,
                                                     @ApiParam(value = "上传文件") @RequestParam(name = "multipartFile", required = false) MultipartFile multipartFile) throws IOException, MessagingException {

        Map<String, Object> data = new HashMap<>(1);
        data.put("user", receiverNickName);

        mailSendService.sendAttachmentsMessage(subject, templateEnums, data, new MultipartFile[]{multipartFile}, receiverMail);
        return ResponseMsgUtil.success();
    }
}