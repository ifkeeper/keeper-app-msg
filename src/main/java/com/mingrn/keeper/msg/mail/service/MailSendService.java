package com.mingrn.keeper.msg.mail.service;

import com.mingrn.keeper.msg.mail.enums.MailTemplateEnums;

import java.io.File;

/**
 * 邮件消息发送业务类接口
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-02-20 09:35
 */
public interface MailSendService {

    /**
     * 发送简单文本邮件
     *
     * @param subject  邮件标题
     * @param msg      邮件文本信息
     * @param receiver 接收者,支持数组
     */
    void sendTextMessage(String subject, String msg, String... receiver);

    /**
     * 发送 HTML 邮件
     *
     * @param subject      邮件标题
     * @param mailTemplate 邮件 HTML 模板
     * @param receiver     接收者,支持数组
     */
    void sendHtmlMessage(String subject, MailTemplateEnums mailTemplate, String... receiver);

    /**
     * 发送 HTML 邮件
     *
     * @param subject     邮件标题
     * @param htmlContent 邮件HTML 信息
     * @param receiver    接收者,支持数组
     */
    void sendHtmlMessage(String subject, String htmlContent, String... receiver);

    /**
     * 发送带附件 HTML 邮件
     *
     * @param subject      邮件标题
     * @param mailTemplate 邮件 HTML 模板
     * @param files        文件
     * @param receiver     接收者,支持数组
     */
    void sendAttachmentsMessage(String subject, MailTemplateEnums mailTemplate, File[] files, String... receiver);

    /**
     * 发送带附件 HTML 邮件
     *
     * @param subject     邮件标题
     * @param htmlContent 邮件HTML 信息
     * @param files       文件
     * @param receiver    接收者,支持数组
     */
    void sendAttachmentsMessage(String subject, String htmlContent, File[] files, String... receiver);

    /**
     * 发送带静态资源 HTML 邮件
     * 静态资源一般图片,发送邮件时参数 <code>resourceIds</code>
     * 要与 <code>files</code> 想对应。如发送图片时 HTML 格式如下:
     * <pre class="code">
     *     <html>
     *         <body>
     *             这是有图片的邮件：<img src=\'cid:" + resourceId + "\' >
     *         </body>
     *     </html>
     * </pre>
     * 如果有多个静态资源就写多个 "<img />" 并且 cid 与对应附件对应即可,
     * 具体见:{@link org.springframework.mail.javamail.MimeMessageHelper#addInline(String, File)}
     *
     * @param subject     邮件标题
     * @param htmlContent 邮件HTML 信息
     * @param files       文件
     * @param receiver    接收者,支持数组
     */
    void sendInlineResourceMail(String subject, String htmlContent, String[] resourceIds, File[] files, String... receiver);
}