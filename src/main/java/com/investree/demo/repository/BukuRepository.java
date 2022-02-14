package com.investree.demo.repository;

import com.investree.demo.model.Buku;
import com.investree.demo.model.PemijamanBuku;
import com.investree.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {

    @Query(value = "SELECT c From Buku c", nativeQuery = false)
    public Page<Buku> getBook(Pageable pageable);

    @Query(value = "select c from Buku c WHERE c.id = :id")
    public Buku getbyID(@Param("id") Long id);


}
