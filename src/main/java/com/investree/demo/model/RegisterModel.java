package com.investree.demo.model;

import lombok.Data;

@Data
public class RegisterModel {

//     "password" : "1234",
//             "fullname" : "Ananda Omesh",
//             "username" : "andada",
//             "staffPerpustakaan" : {
//        "divisi" : "Arsip"
//    }

    private String password;
    private String fullname;
    private String username;

    private StaffPerpustakaan staffPerpustakaan;
}
