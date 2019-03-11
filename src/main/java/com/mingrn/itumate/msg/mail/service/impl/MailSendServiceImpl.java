package com.mingrn.itumate.msg.mail.service.impl;

import com.mingrn.itumate.commons.utils.ftl.FreemarkerUtil;
import com.mingrn.itumate.msg.mail.service.MailSendService;
import com.mingrn.itumate.msg.mail.enums.MailTemplateEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件消息发送业务类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-02-20 09:35
 */
@Service("mailSendService")
public class MailSendServiceImpl implements MailSendService {

    @Value("${spring.mail.from.user}")
    private String mailFrom;

    @Value("${spring.mail.from.nickname}")
    private String mailNickname;

    @Resource
    private JavaMailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendServiceImpl.class);

    @Async
    @Override
    public void sendTextMessage(String subject, String msg, String... receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    @Async
    @Override
    public void sendHtmlMessage(String subject, MailTemplateEnums mailTemplate, String... receiver) {
        Map<String, Object> data = new HashMap<>(1);
        data.put("user", "小明的姨夫");
        String htmlContent = MailTemplateEnums.genTemplateHtml(mailTemplate, data);
        sendHtmlMessage(subject, htmlContent, receiver);
    }

    @Async
    @Override
    public void sendHtmlMessage(String subject, String htmlContent, String... receiver) {
        Map<String, Object> data = new HashMap<>(1);
        data.put("user", "小明的姨夫");
        htmlContent = FreemarkerUtil.genFtl2String("register-verify.ftl", data);
        LOGGER.info("----------开始发送邮件---------");
        long start = System.currentTimeMillis();
        MimeMessage message = mailSender.createMimeMessage();
        mimeMessageHelperCreate(message, subject, htmlContent, receiver);
        mailSender.send(message);
        LOGGER.info("----------邮件发送完成---------");
        LOGGER.info("用时：{}  毫秒", System.currentTimeMillis() - start);
    }

    @Async
    @Override
    public void sendAttachmentsMessage(String subject, MailTemplateEnums mailTemplate, File[] files, String... receiver) {
        // TODO: 2019-02-19 模板邮件信息
    }

    @Async
    @Override
    public void sendAttachmentsMessage(String subject, String htmlContent, File[] files, String... receiver) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            for (File file : files) {
                String fileName = file.getName();
                FileSystemResource fileSystemResource = new FileSystemResource(file);
                helper.addAttachment(fileName, fileSystemResource);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendInlineResourceMail(String subject, String htmlContent, String[] resourceIds, File[] files, String... receiver) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = mimeMessageHelperCreate(message, subject, htmlContent, receiver);
            for (File file : files) {
                String fileName = file.getName();
                FileSystemResource fileSystemResource = new FileSystemResource(file);
                helper.addInline("", fileSystemResource);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    /**
     * HTML 邮件发送通用方法
     *
     * @param message     Mime 消息类型邮件 {@link MimeMessage}
     * @param subject     主题
     * @param htmlContent HTML 消息内容
     * @param receiver    接收者
     */
    private MimeMessageHelper mimeMessageHelperCreate(MimeMessage message, String subject, String htmlContent, String... receiver) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom, MimeUtility.decodeText(mailNickname));
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            return helper;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
