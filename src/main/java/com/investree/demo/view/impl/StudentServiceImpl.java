package com.investree.demo.view.impl;

import com.investree.demo.model.Buku;
import com.investree.demo.model.DetailStudent;
import com.investree.demo.model.PemijamanBuku;
import com.investree.demo.model.Student;
import com.investree.demo.repository.PeminjamanRepository;
import com.investree.demo.repository.StudentDetailRepository;
import com.investree.demo.repository.StudentRepository;
import com.investree.demo.view.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl  implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentDetailRepository studentDetailRepository;
    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Override
    @Transactional
    public Page<Student> getStudent(Pageable pageable) {
       return studentRepository.getStudent(pageable);
    }

    @Override
    @Transactional
    public Map insert(Student student){
        Map map = new HashMap();
        try {
            Student saveStudent = studentRepository.save(student);
            if(student.getStudentDetail() != null){
                DetailStudent detail = studentDetailRepository.save(student.getStudentDetail());
                detail.setStudent(saveStudent);
                studentDetailRepository.save(detail);
            }
            map.put("data", saveStudent);
            map.put("code", "200");
            map.put("status", "success");
            return map;
        } catch (Exception e) {
            map.put("code", "500");
            map.put("status", "failed");
            return map;
        }
    }

    @Override
    @Transactional
    public Map update(Student student, Long id) {
        Map map = new HashMap();
        try{
            Student update = studentRepository.getbyID(id);
            if(update == null ){
                map.put("statusCode", "404");
                map.put("statusMessage", "Data id tidak ditemukan");
                return map;
            }
            update.setFirstName(student.getFirstName());
            update.setLastName(student.getLastName());
            update.setPhoto(student.getPhoto());
            Student doUpdate = studentRepository.save(update);
            map.put("data", doUpdate);
            map.put("statusCode", 200);
            map.put("statusMessage", "success");
            return map;
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("statusCode", 500);
            map.put("statusMessage", "failed");
            return map;
        }

    }

    @Override
    public Map delete(Long id) {
        Map map = new HashMap<>();
        try{
            Student student = studentRepository.getbyID(id);
            if(student == null){
                map.put("statusCode", "404");
                map.put("statusMessage", "Data tidak ditemukan");
                return map;
            }
            student.setDeleted_date(new Date());
            studentRepository.save(student);

            map.put("statusCode", 200);
            map.put("statusMessage", "success");
            return map;
        }
        catch (Exception e){

            e.printStackTrace();
            map.put("statusCode", "500");
            map.put("statusMessage", e);
            return map;

        }
        //return null;
    }

    @Override
    public Page<PemijamanBuku> getByUser(Student student, Pageable pageable) {
        return peminjamanRepository.getByUser(student, pageable);
    }
}
