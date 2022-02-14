package com.investree.demo.repository;

import com.investree.demo.model.DetailStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDetailRepository extends JpaRepository<DetailStudent, Long> {

}
