package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@RabbitListener(queues = "sms")
@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private Environment env;

    @RabbitHandler
    public void sendSms(Map<String, String> map) {
        String mobile = map.get("mobile");
        String template_code = env.getProperty("template_code");
        String sign_name = env.getProperty("sign_name");
        String checkcode = map.get("checkcode");
        try {
            smsUtil.sendSms(mobile, template_code, sign_name, "{\"checkcode\":\""+checkcode+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println("手机号：" + map.get("mobile"));
        System.out.println("验证码：" + map.get("checkcode"));
    }
}
