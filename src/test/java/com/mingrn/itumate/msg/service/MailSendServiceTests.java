package com.mingrn.itumate.msg.service;

import com.mingrn.itumate.commons.utils.file.FileWrap;
import com.mingrn.itumate.msg.mail.enums.MailTemplateEnums;
import com.mingrn.itumate.msg.mail.service.MailSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSendServiceTests {

    @Autowired
    private MailSendService mailSendService;

    @Test
    public void sendTextMessage() {
        mailSendService.sendTextMessage("侣行网测试文本邮件", "简单文本内容消息", "itumate@qq.com");
    }

    @Test
    public void sendHtmlMessage() {
        Map<String, Object> data = new HashMap<>();
        data.put("user", "Linda");
        mailSendService.sendHtmlMessage("侣行网测试HTML邮件", MailTemplateEnums.REGISTER_VERIFY, data, "941426515@qq.com");
    }

    @Test
    public void sendAttachmentsMessage() throws MessagingException {
        Map<String, Object> data = new HashMap<>();
        data.put("user", "Linda");
        FileWrap[] fileWraps = new FileWrap[1];
        fileWraps[0] = new FileWrap(new File("/Users/mingrn/Downloads/整体流程.png"));

        mailSendService.sendAttachmentsMessage("侣行网测试HTML邮件", MailTemplateEnums.REGISTER_VERIFY, data, fileWraps, "itumate@qq.com");
    }
}
