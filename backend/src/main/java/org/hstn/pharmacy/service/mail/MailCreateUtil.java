package org.hstn.pharmacy.service.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.entity.entityUser.User; // Імпортуйте клас User
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailCreateUtil {

    private final Configuration freemarkerConfiguration;

    public String createConfirmationMail(User user, String link){ // Змініть параметри

        try {
            Template template = freemarkerConfiguration.getTemplate("confirm_registration_mail.ftlh");
            Map<String,Object> model = new HashMap<>(); // Використовуйте String як ключ для кращої типобезпеки
            model.put("user", user); // Передайте об'єкт user
            model.put("link", link);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
        } catch (Exception e) {
            throw new IllegalStateException("Error while processing email template", e);
        }
    }
}