package com.investree.demo.controller;

import com.investree.demo.config.Config;
import com.investree.demo.model.oauth.User;
import com.investree.demo.repository.oauth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegisterConfirmController {

    @Autowired
    public UserRepository userRepo;

    Config config = new Config();

    @GetMapping(value = { "/registerActivation/{tokenotp}"})
    public String index(Model model, @PathVariable String  tokenotp) {
        User user = userRepo.findOneByOTP(tokenotp);
        if (null == user) {
            System.out.println("user null: tidak ditemukan");
            model.addAttribute("erordesc", "User not found for code " + tokenotp);
            model.addAttribute("title", "");
            return "register";
        }
        String today = config.convertDateToString(new Date());

        String dateToken = config.convertDateToString(user.getOtpExpiredDate());
        if (Long.parseLong(today) > Long.parseLong(dateToken)) {
            model.addAttribute("erordesc", "Your token is expired. Please Get token again.");
            model.addAttribute("title", "");
            return "register";
        }
        user.setEnabled(true);
        userRepo.save(user);
        model.addAttribute("title", "Congratulations, " + user.getUsername() + ", you have successfully registered.");
        model.addAttribute("erordesc", "");
        return "register";
    }
}
