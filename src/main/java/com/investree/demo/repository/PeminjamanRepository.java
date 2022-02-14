package com.investree.demo.repository;

import com.investree.demo.model.PemijamanBuku;

import com.investree.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PeminjamanRepository extends JpaRepository<PemijamanBuku, Long> {

    @Query(value = "SELECT c From PemijamanBuku c where c.student = :student ")
    public Page<PemijamanBuku> getByUser(Student student, Pageable pageable);

    @Query(value = "SELECT c From PemijamanBuku c")
    public Page<PemijamanBuku> getDataPeminjaman(Pageable pageable);

    @Query(value = "SELECT c From PemijamanBuku c where c.status = :status")
    public Page<PemijamanBuku> getPeminjamanByStatus(String status, Pageable pageable);
}
