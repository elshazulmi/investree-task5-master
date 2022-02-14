package com.investree.demo.repository;

import com.investree.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

   @Query(value = "SELECT c From Student c", nativeQuery = false)
    public Page<Student>  getStudent(Pageable pageable);

   @Query(value = "select c from Student c WHERE c.id = :id")
    public Student getbyID(@Param("id") Long id);



}
