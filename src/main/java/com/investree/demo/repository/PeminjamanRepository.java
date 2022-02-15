package com.investree.demo.repository;

import com.investree.demo.model.Buku;
import com.investree.demo.model.PemijamanBuku;

import com.investree.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PeminjamanRepository extends JpaRepository<PemijamanBuku, Long> {

    @Query(value = "SELECT c From PemijamanBuku c where c.student = :student ")
    public Page<PemijamanBuku> getByUser(Student student, Pageable pageable);

    @Query(value = "SELECT c From PemijamanBuku c")
    public Page<PemijamanBuku> getDataPeminjaman(Pageable pageable);

    @Query(value = "SELECT c From PemijamanBuku c where c.status = :status")
    public Page<PemijamanBuku> getPeminjamanByStatus(String status, Pageable pageable);

//    @Query(value = "select c from PemijamanBuku c WHERE c.status = :status and c.buku = :buku")
//    public PemijamanBuku getbyStatus(String status, Long id);

//    @Query(value = "select c from PeminjamanBuku c WHERE c.tglPeminjaman >= :tglPeminjaman AND c.tglPengembalian < :tglPengembalian")
//    public PemijamanBuku getbyDate(@Param("tglPeminjaman")Date tglPeminjaman);


}
