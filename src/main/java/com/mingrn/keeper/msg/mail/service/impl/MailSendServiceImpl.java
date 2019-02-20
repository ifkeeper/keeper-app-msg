package com.mingrn.keeper.msg.mail.service.impl;

import com.mingrn.keeper.msg.mail.service.MailSendService;
import com.mingrn.keeper.msg.mail.enums.MailTemplateEnums;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 邮件消息发送业务类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-02-20 09:35
 */
@Service
public class MailSendServiceImpl implements MailSendService {

    @Value("${spring.mail.from.user}")
    private String mailFrom;

    @Value("${spring.mail.from.nickname}")
    private String mailNickname;

    @Resource
    private JavaMailSender mailSender;

    @Override
    public void sendTextMessage(String subject, String msg, String... receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMessage(String subject, String htmlContent, String... receiver) {
        MimeMessage message = mailSender.createMimeMessage();
        mimeMessageHelperCreate(message, subject, htmlContent, receiver);
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMessage(String subject, MailTemplateEnums mailTemplate, String... receiver) {
        // TODO: 2019-02-19 模板信息
    }

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

    @Override
    public void sendAttachmentsMessage(String subject, MailTemplateEnums mailTemplate, File[] files, String... receiver) {
        // TODO: 2019-02-19 模板邮件信息
    }

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
