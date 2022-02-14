package com.investree.demo.view.impl;

import com.investree.demo.config.Config;
import com.investree.demo.controller.StudentController;
import com.investree.demo.model.Buku;
import com.investree.demo.model.RegisterModel;
import com.investree.demo.model.StaffPerpustakaan;
import com.investree.demo.model.oauth.Role;
import com.investree.demo.model.oauth.User;
import com.investree.demo.repository.StaffRepository;
import com.investree.demo.repository.oauth.RoleRepository;
import com.investree.demo.repository.oauth.UserRepository;
import com.investree.demo.view.RegisterService;
import com.investree.demo.view.email.EmailService;
import com.investree.demo.view.email.MailRequest;
import com.investree.demo.view.email.MailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    Config config = new Config();

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    @Override
    public Map registerManual(RegisterModel registerModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // admin
            User user1 = new User();
            user1.setPassword(registerModel.getPassword());
            //user1.setUsername(user.getEmail().toLowerCase());
            user1.setFullname(registerModel.getFullname());
            //user1.setWsToken(UUID.randomUUID().toString()+UUID.randomUUID().toString());
            //step 1 :
            user1.setEnabled(false); // matikan user
            user1.setOtp(config.convertDateToString(new Date()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.HOUR_OF_DAY, 1);
            user1.setOtpExpiredDate(cal.getTime());
            //Validation belum

            String password = passwordEncoder.encode(registerModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = roleRepository.findByNameIn(roleNames);

            user1.setRoles(r);
            user1.setPassword(password);
            user1.setUsername(registerModel.getUsername());
//            if(user1.getCurrency() == null){
//                user1.setCurrencyDesc("New Zeland");
//            }
            User obj = userRepository.save(user1);
            if (registerModel.getStaffPerpustakaan() != null){
                registerModel.getStaffPerpustakaan().setUser(obj);
                staffRepository.save(registerModel.getStaffPerpustakaan());
            }

            map.put("data", obj);
            map.put(config.getCode(), config.code_sukses);
            map.put(config.getMessage(), config.message_sukses);
            sendEmail(user1.getOtp(), user1.getUsername());
            return map;
        } catch (Exception e) {
            LOG.error("Error registerManual=", e);
            map.put(config.getCode(), config.code_server);
            map.put(config.getMessage(), e.getLocalizedMessage());
            return map;
        }

    }

    public void sendEmail(String otp, String username ) {
        MailRequest request = new MailRequest();
        request.setName("OTP Code");
        request.setTo("elshayulianizulmi@gmail.com");
        request.setFrom("elshayulianizulmi@gmail.com");
        request.setSubject("OTP Password");

        Map<String, Object> model = new HashMap<>();
        model.put("otp", "http://localhost:8081/api/register/registerActivation/"+ otp);
        model.put("username", username);
        MailResponse ma =  emailService.sendEmail(request, model);
        System.out.println("MailResponse 1="+ma.getMessage());

    }
}
