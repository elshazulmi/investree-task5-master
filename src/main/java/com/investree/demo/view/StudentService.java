package com.investree.demo.view;

import com.investree.demo.model.PemijamanBuku;
import com.investree.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StudentService {

    public Page<Student> getStudent(Pageable pageable);

    public Map insert(Student student );

    public Map update(Student student, Long id);

    public Map delete(Long id);

    public Page<PemijamanBuku> getByUser(Student student, Pageable pageable);

}
