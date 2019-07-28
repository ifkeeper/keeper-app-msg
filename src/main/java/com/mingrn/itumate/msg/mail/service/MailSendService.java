package com.mingrn.itumate.msg.mail.service;

import com.mingrn.itumate.commons.utils.file.FileWrap;
import com.mingrn.itumate.msg.mail.enums.MailTemplateEnums;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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
     * <p>
     * 参数 {@code data} 是 Email 模板数据, 具体模板见 {@link MailTemplateEnums mailTemplate}.
     * 模板使用的是 Freemarker, 模板接受 Map 型数据. 如下示例:
     * {@code
     *   Map<String, Object> data = new HashMap<>();
     *   data.put("userName", "Linda");
     *
     *   MailSendService mailSendService = new MailSendServiceImpl();
     *   mailSendService.sendHtmlMessage("注册邀请函", MailTemplateEnums.REGISTER_VERIFY, data, "linda@mail.com")
     * }
     * <p>
     * 具体 {@code data} 数据见响应模板
     *
     * @param subject      邮件标题
     * @param mailTemplate 邮件 HTML 模板
     * @param data         模板数据, 具体数据见模板
     * @param receiver     接收者,支持数组
     */
    void sendHtmlMessage(String subject, MailTemplateEnums mailTemplate, Map<String, Object> data, String... receiver);

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
     * <p>
     * 参数 {@code data} 是 Email 模板数据, 具体模板见 {@link MailTemplateEnums mailTemplate}.
     * 模板使用的是 Freemarker, 模板接受 Map 型数据. 如下示例:
     * {@code
     *   Map<String, Object> data = new HashMap<>();
     *   data.put("userName", "Linda");
     *
     *   MailSendService mailSendService = new MailSendServiceImpl();
     *   mailSendService.sendAttachmentsMessage("注册邀请函", MailTemplateEnums.REGISTER_VERIFY, data, new File("..."), "linda@mail.com")
     * }
     * <p>
     * @param subject      邮件标题
     * @param mailTemplate 邮件 HTML 模板
     * @param data         模板数据, 具体数据见模板
     * @param files        文件
     * @param receiver     接收者,支持数组
     * @throws MessagingException
     */
    void sendAttachmentsMessage(String subject, MailTemplateEnums mailTemplate, Map<String, Object> data, FileWrap[] files, String... receiver) throws MessagingException;

    /**
     * 发送带附件 HTML 邮件
     * <p>
     * 可参考 {@link #sendAttachmentsMessage(String, MailTemplateEnums, Map, FileWrap[], String...)}
     *
     * @param subject     邮件标题
     * @param htmlContent 邮件HTML 信息
     * @param files       文件
     * @param receiver    接收者,支持数组
     * @param useHtml     是否使用HTML
     * @throws MessagingException
     */
    void sendAttachmentsMessage(String subject, String htmlContent, FileWrap[] files, Boolean useHtml, String... receiver) throws MessagingException;

    /**
     * 发送带附件 HTML 邮件
     * <p>
     * 参数 {@code data} 是 Email 模板数据, 具体模板见 {@link MailTemplateEnums mailTemplate}.
     * 模板使用的是 Freemarker, 模板接受 Map 型数据. 如下示例:
     * {@code
     *   Map<String, Object> data = new HashMap<>();
     *   data.put("userName", "Linda");
     *
     *   MultipartFile[] multipartFiles = ...;
     *
     *   MailSendService mailSendService = new MailSendServiceImpl();
     *   mailSendService.sendAttachmentsMessage("注册邀请函", MailTemplateEnums.REGISTER_VERIFY, data, multipartFiles, "linda@mail.com")
     * }
     * <p>
     * 具体 {@code data} 数据见响应模板
     *
     * @param subject        邮件标题
     * @param mailTemplate   邮件模板
     * @param data           模板数据, 具体数据见模板
     * @param multipartFiles 附件, {@link MultipartFile multipartFiles}
     * @param receiver       接收者,支持数组
     * @throws MessagingException
     * @throws IOException
     */
    void sendAttachmentsMessage(String subject, MailTemplateEnums mailTemplate, Map<String, Object> data, MultipartFile[] multipartFiles, String... receiver) throws MessagingException, IOException;

    /**
     * 发送带附件 HTML 邮件
     * <p>
     * 可参考 {@link #sendAttachmentsMessage(String, MailTemplateEnums, Map, MultipartFile[], String...)}
     *
     * @param subject        邮件标题
     * @param htmlContent    HTML内容
     * @param multipartFiles 附件, {@link MultipartFile multipartFiles}
     * @param useHtml        是否使用HTML
     * @param receiver       接收者,支持数组
     * @throws MessagingException
     * @throws IOException
     */
    void sendAttachmentsMessage(String subject, String htmlContent, MultipartFile[] multipartFiles, Boolean useHtml, String... receiver) throws MessagingException, IOException;

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
    /*void sendInlineResourceMail(String subject, String htmlContent, String[] resourceIds, File[] files, String... receiver);*/

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
     * <p>
     * 示例如下:
     * {@code
     *     String html = "";
     *
     *     Map<String, Object> data = new HashMap<>();
     *     data.put("resourceId", "UUID");
     *     data.put("file", new File(...));
     *
     *     List<Map<String, Object> list = new ArrayList<>();
     *     list.add(data);
     *
     *     MailSendService mailSendService = new MailSendServiceImpl();
     *     mailSendService.sendInlineResourceMail("注册邀请函", html, list, "linda@mail.com")
     * }
     *
     * @param subject     邮件标题
     * @param htmlContent 邮件HTML 信息
     * @param data        资源数据
     * @param receiver    接收者,支持数组
     */
    /*void sendInlineResourceMail(String subject, String htmlContent, List<Map<String, Object>> data, String... receiver);*/
}