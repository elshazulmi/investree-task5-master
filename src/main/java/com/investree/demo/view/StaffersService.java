package com.investree.demo.view;

import com.investree.demo.model.StaffPerpustakaan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface StaffersService {

    public Page<StaffPerpustakaan> getStaffers(Pageable pageable);

    public Map insert(StaffPerpustakaan perpustakaan);

    public Map update(StaffPerpustakaan perpustakaan, Long id);

    public Map delete(Long id);
}
