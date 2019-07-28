package com.mingrn.itumate.msg.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailSendControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private MockHttpSession session;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendControllerTests.class);

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        session = new MockHttpSession();
    }

    @Test
    public void sendTextMessage() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("user", "Linda");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/mailSendApi/sendTextMessage")
                .param("subject", "侣行网测试HTML邮件")
                .param("msg", "简单文本内容消息")
                .param("receiver", "itumate@qq.com")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        LOGGER.info(mvcResult.getResponse().getContentAsString());
    }
}
