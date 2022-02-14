package com.investree.demo.controller;

import com.investree.demo.model.StaffPerpustakaan;
import com.investree.demo.view.StaffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/staffers")
public class StafferController {

    @Autowired
    private StaffersService staffersService;

    @GetMapping("/listStaff")
    public ResponseEntity<Map> list(){
        Pageable showData = (Pageable) PageRequest.of(0,10);
        Page<StaffPerpustakaan> listStaff = staffersService.getStaffers(showData);
        Map list = new HashMap<>();
        list.put("data", listStaff);
        list.put("statuscode", 200);
        list.put("statusMessage", "Sukses menampilkan data student");
        return new ResponseEntity<Map>(list, HttpStatus.OK);
    }

    @PostMapping("/simpanStaff")
    public ResponseEntity<Map> save(@RequestBody StaffPerpustakaan staffPerpustakaan) {
        Map save = staffersService.insert(staffPerpustakaan);
        return new ResponseEntity<Map>(save, HttpStatus.OK);
    }

    @PutMapping("/updateStaff/{id}")
    public ResponseEntity<Map> update(@PathVariable(value = "id") Long id, @RequestBody StaffPerpustakaan staffPerpustakaan){
        Map update = staffersService.update(staffPerpustakaan,id);
        return new ResponseEntity<Map>(update, HttpStatus.OK);
    }

    @DeleteMapping("/deleteStaff/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map map = staffersService.delete(id);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}
