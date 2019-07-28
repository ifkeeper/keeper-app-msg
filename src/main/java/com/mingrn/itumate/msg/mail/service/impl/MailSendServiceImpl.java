package com.mingrn.itumate.msg.mail.service.impl;

import com.mingrn.itumate.commons.utils.file.FileUpDownUtils;
import com.mingrn.itumate.commons.utils.file.FileWrap;
import com.mingrn.itumate.msg.mail.service.MailSendService;
import com.mingrn.itumate.msg.mail.enums.MailTemplateEnums;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        LOGGER.info("----------开始发送简单文本邮件---------");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
        LOGGER.info("----------简单文本邮件发送完成---------");
    }

    @Async
    @Override
    public void sendHtmlMessage(String subject, MailTemplateEnums mailTemplate, Map<String, Object> data, String... receiver) {
        String htmlContent = MailTemplateEnums.genTemplateHtml(mailTemplate, data);
        this.sendHtmlMessage(subject, htmlContent, receiver);
    }

    @Async
    @Override
    public void sendHtmlMessage(String subject, String htmlContent, String... receiver) {
        LOGGER.info("----------开始发送HTML邮件---------");
        long start = System.currentTimeMillis();
        MimeMessage message = mailSender.createMimeMessage();
        mimeMessageHelperCreate(message, subject, htmlContent, true, receiver);
        mailSender.send(message);
        LOGGER.info("----------HTML邮件发送完成---------");
        LOGGER.info("用时：{}  毫秒", System.currentTimeMillis() - start);
    }

    @Async
    @Override
    public void sendAttachmentsMessage(String subject, MailTemplateEnums mailTemplate, Map<String, Object> data, FileWrap[] files, String... receiver) throws MessagingException {
        String htmlContent = MailTemplateEnums.genTemplateHtml(mailTemplate, data);
        sendAttachmentsMessage(subject, htmlContent, files, true, receiver);
    }

    @Async
    @Override
    public void sendAttachmentsMessage(String subject, String htmlContent, FileWrap[] files, Boolean useHtml, String... receiver) throws MessagingException {
        LOGGER.info("----------开始发送附件HTML邮件---------");
        long start = System.currentTimeMillis();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = mimeMessageHelperCreate(message, subject, htmlContent, useHtml, receiver);
        for (FileWrap file : files) {
            helper.addAttachment(file.getName(), file.getFile());
        }
        mailSender.send(message);
        LOGGER.info("----------附件HTML邮件发送完成---------");
        LOGGER.info("用时：{}  毫秒", System.currentTimeMillis() - start);
    }

    @Override
    public void sendAttachmentsMessage(String subject, MailTemplateEnums mailTemplate, Map<String, Object> data, MultipartFile[] multipartFiles, String... receiver) throws MessagingException, IOException {
        String htmlContent = MailTemplateEnums.genTemplateHtml(mailTemplate, data);
        this.sendAttachmentsMessage(subject, htmlContent, multipartFiles, true, receiver);
    }

    @Override
    public void sendAttachmentsMessage(String subject, String htmlContent, MultipartFile[] multipartFiles, Boolean useHtml, String... receiver) throws MessagingException, IOException {
        List<FileWrap> fileWraps = new ArrayList<>(multipartFiles.length);
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile != null && StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                String fileName = multipartFile.getOriginalFilename();
                String fileRealPath = FileUpDownUtils.TMP_DIR + File.separator + FileUpDownUtils.getTempFileName(fileName);
                File file = new File(fileRealPath);
                file.delete();
                multipartFile.transferTo(file);
                fileWraps.add(new FileWrap(fileName, file));
            }
        }
        this.sendAttachmentsMessage(subject, htmlContent, fileWraps.toArray(new FileWrap[fileWraps.size()]), useHtml, receiver);
    }

    /*@Async
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
            LOGGER.error(e.getMessage(), e);
        }
    }*/

    /*@Async
    @Override
    public void sendInlineResourceMail(String subject, String htmlContent, List<Map<String, Object>> data, String... receiver) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = mimeMessageHelperCreate(message, subject, htmlContent, receiver);
            for (Map<String, Object> resource : data) {
                File file = (File) resource.get("file");
                FileSystemResource fileSystemResource = new FileSystemResource(file);
                helper.addInline(resource.get("resourceId").toString(), fileSystemResource);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }*/

    /**
     * HTML 邮件发送通用方法
     *
     * @param message     Mime 消息类型邮件 {@link MimeMessage}
     * @param subject     主题
     * @param htmlContent HTML 消息内容
     * @param useHtml     是否使用HTML
     * @param receiver    接收者
     */
    private MimeMessageHelper mimeMessageHelperCreate(MimeMessage message, String subject, String htmlContent, Boolean useHtml, String... receiver) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom, MimeUtility.decodeText(mailNickname));
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(htmlContent, useHtml);
            return helper;
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
