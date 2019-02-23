package com.mingrn.keeper.msg.mail.enums;

import com.mingrn.keeper.commons.utils.ftl.FreemarkerUtil;
import com.mingrn.keeper.global.annotation.Checked;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 模板枚举类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-02-19 21:36
 */
public enum MailTemplateEnums {
    /**
     * 账号激活验证
     */
    REGISTER_VERIFY("register-verify.ftl");

    private String template;

    MailTemplateEnums(String template) {
        this.template = template;
    }

    @Checked
    public static String genTemplateHtml(MailTemplateEnums templateEnums, Map<String, Object> data) {
        return FreemarkerUtil.genFtl2String(templateEnums.getTemplate(), data);
    }

    public String getTemplate() {
        return template;
    }
}