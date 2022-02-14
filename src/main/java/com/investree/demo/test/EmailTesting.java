package com.investree.demo.test;

import com.investree.demo.repository.PeminjamanRepository;
import com.investree.demo.repository.StaffRepository;
import com.investree.demo.view.email.EmailService;
import com.investree.demo.view.email.MailRequest;
import com.investree.demo.view.email.MailResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailTesting {

    @Autowired
    private EmailService emailService;
    @Value("${SHOWFILEPATH:}")//FILE_SHOW_RUL
    private String SHOWFILEPATH;

    @Value("${spring.mail.username:}")//FILE_SHOW_RUL
    private String emailSender;
    @Test
    public void sendEmail( ) {
        MailRequest request = new MailRequest();
        request.setName("OTP Code");
        request.setTo("elshayulianizulmi@gmail.com");
        request.setFrom("elshayulianizulmi@gmail.com");
        request.setSubject("OTP Password");

        Map<String, Object> model = new HashMap<>();
        model.put("otp", "http://localhost:8081/api/registerActivation/12345");
        model.put("username", "Elsha YPZ");
        MailResponse ma =  emailService.sendEmail(request, model);
        System.out.println("MailResponse 1="+ma.getMessage());

    }

}
