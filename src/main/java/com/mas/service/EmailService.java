package com.mas.service;

import com.mas.domain.Mail;
import com.mas.domain.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendMessage(User receiver, String subject, String templateName) throws MessagingException, IOException, TemplateException {
        Mail mail = new Mail();
        mail.setMailFrom("denis.sosulev@gmail.com");
        mail.setMailTo(receiver.getEmail());
        mail.setMailSubject(subject);
        Map<String, Object> model = new HashMap<>();
        model.put("userName", receiver.getUserName());
        model.put("lastName", receiver.getLastName());
        model.put("login", receiver.getLogin());
        mail.setModel(model);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Template template = freemarkerConfig.getTemplate(templateName+".ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getMailSubject());
        helper.setFrom(mail.getMailFrom());
        emailSender.send(message);
    }

    public void sendMessage(User receiver, String subject, String templateName, Map<String, Object> model) throws MessagingException, IOException, TemplateException {
        Mail mail = new Mail();
        mail.setMailFrom("denis.sosulev@gmail.com");
        mail.setMailTo(receiver.getEmail());
        mail.setMailSubject(subject);
        mail.setModel(model);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Template template = freemarkerConfig.getTemplate(templateName+".ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getMailSubject());
        helper.setFrom(mail.getMailFrom());
        emailSender.send(message);

    }
}
