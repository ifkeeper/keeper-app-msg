package com.mingrn.keeper.msg;

import com.mingrn.keeper.msg.mail.service.MailSendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@EnableAsync
@RestController
@SpringBootApplication
@ComponentScan(basePackages = "com.mingrn.keeper")
public class KeeperAppMsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeeperAppMsgApplication.class, args);
    }

    @Resource
    private MailSendService mailSendService;

    @PostMapping("/sendHtmlMessage")
    public String sendHtmlMessage(@RequestParam String subject, @RequestParam String htmlContent, @RequestParam String... receiver) {
        for (int i = 0; i < 100; i++) {
            mailSendService.sendHtmlMessage(subject, htmlContent, receiver);
        }
        return "success";
    }

}
