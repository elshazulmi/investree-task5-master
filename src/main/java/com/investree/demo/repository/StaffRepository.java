package com.investree.demo.repository;

import com.investree.demo.model.StaffPerpustakaan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffPerpustakaan, Long> {

    @Query(value = "SELECT c From StaffPerpustakaan c")
    public Page<StaffPerpustakaan> getStaffPerpus(Pageable pageable);
}
