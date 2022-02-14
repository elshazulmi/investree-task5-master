package com.investree.demo.controller;

import com.investree.demo.model.PemijamanBuku;
import com.investree.demo.model.Student;
import com.investree.demo.repository.StudentRepository;
import com.investree.demo.view.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    public StudentRepository studentRepository;

    @GetMapping("/listStudent")
    public ResponseEntity<Map> list(@RequestParam() Integer page,
                                    @RequestParam() Integer size){
        Pageable showData = (Pageable) PageRequest.of(page,size);
        Page<Student> listStudent = studentService.getStudent(showData);
        Map list = new HashMap<>();
        list.put("data", listStudent);
        list.put("statuscode", 200);
        list.put("statusMessage", "Sukses menampilkan data student");
        return new ResponseEntity<Map>(list, HttpStatus.OK);
    }

    @PostMapping("/simpan")
    public ResponseEntity<Map> save(@RequestBody Student student) {
        //Student murid1 = new Student(1, "Udin", "Bambang");
        Map save = studentService.insert(student);
        //Log.info( "is created new  "  +student);
        return new ResponseEntity<Map>(save, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map> update(@PathVariable(value = "id") Long id,@RequestBody Student student){
        Map update = studentService.update(student,id);
        return new ResponseEntity<Map>(update, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = studentService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/listByUser")
    public ResponseEntity<Map> listByUser(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam() Long student_id) {
        Pageable show_data = PageRequest.of(page, size);
        Student student = studentRepository.getbyID(student_id);
        Map map = new HashMap<>();
        if (student == null){
            map.put("statusCode", "404");
            map.put("statusMessage", "Data tidak ditemukan");
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
        Page<PemijamanBuku> listPinjam = studentService.getByUser(student, show_data);
        map.put("data", listPinjam);
        map.put("statuscode", 200);
        map.put("statusMessage", "Sukses menampilkan data student");
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }








}
