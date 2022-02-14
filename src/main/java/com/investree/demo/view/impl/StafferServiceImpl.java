package com.investree.demo.view.impl;

import com.investree.demo.model.Buku;
import com.investree.demo.model.StaffPerpustakaan;
import com.investree.demo.repository.StaffRepository;
import com.investree.demo.view.StaffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class StafferServiceImpl implements StaffersService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    @Transactional
    public Page<StaffPerpustakaan> getStaffers(Pageable pageable) {
        return staffRepository.getStaffPerpus(pageable);
    }

    @Override
    @Transactional
    public Map insert(StaffPerpustakaan perpustakaan) {
        Map map = new HashMap();
        try{
            StaffPerpustakaan staff = staffRepository.save(perpustakaan);
            map.put("data", staff);
            map.put("succesCode", 200);
            map.put("statusMessage", "Sukses");
            return map;
        }
        catch (Exception ex){
            ex.printStackTrace();
            map.put("successCode", 500);
            map.put("statusMessage", "Gagal");
            return map;
        }
    }

    @Override
    public Map update(StaffPerpustakaan perpustakaan, Long id) {
        Map map = new HashMap();
        try {
            StaffPerpustakaan staff = staffRepository.getById(id);
            //staff.setFullName(perpustakaan.getFullName());
            staff.setDivisi(perpustakaan.getDivisi());
            StaffPerpustakaan doUpdate = staffRepository.save(staff);
            map.put("data", doUpdate);
            map.put("statusCode", 200);
            map.put("message", "Success");
            return map;
        }
        catch (Exception ex){
            ex.printStackTrace();
            map.put("statusCode", 500);
            map.put("message", "failed");
            return map;
        }
    }

    @Override
    public Map delete(Long id) {
        Map map = new HashMap<>();
        try{
            StaffPerpustakaan staff = staffRepository.getById(id);
            if(staff == null){
                map.put("statusCode", "404");
                map.put("statusMessage", "data id tidak ditemuakan");
                return map;
            }
            staffRepository.deleteById(staff.getId());
            map.put("statusCode", "200");
            map.put("statusMessage", "Delete Sukses");
            return map;
        }
        catch (Exception ex){
            ex.printStackTrace();
            map.put("statusCode", "500");
            map.put("statusMessage", ex);
            return map;
        }
    }
}
