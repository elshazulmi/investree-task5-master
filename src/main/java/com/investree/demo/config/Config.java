package com.investree.demo.config;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Data
//buat variable global
public class Config {

    String code = "status", message = "message";
    public String code_notFound ="404";

    public String codeRequired ="403";
    public String isRequired =" is Required";

    public String code_sukses = "200";
    public String code_server = "500";
    public String code_null = "1";
    public String message_sukses = "sukses";
    public String statusPinjam = "pinjam";
    public String statusKembali = "kembalikan";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String convertDateToString(Date date) throws NumberFormatException {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date);
        return strDate;

    }
}
