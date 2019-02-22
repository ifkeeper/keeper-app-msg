package com.mingrn.keeper.msg;

import com.mingrn.keeper.msg.mail.enums.MailTemplateEnums;
import com.mingrn.keeper.msg.mail.service.MailSendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@EnableAsync
@RestController
@EnableDiscoveryClient
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
        mailSendService.sendHtmlMessage(subject, MailTemplateEnums.REGISTER_VERIFY, receiver);
        return "success";
    }

}
