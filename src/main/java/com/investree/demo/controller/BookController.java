package com.investree.demo.controller;

import com.investree.demo.model.Buku;
import com.investree.demo.model.PemijamanBuku;
import com.investree.demo.view.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/listBooks")
    public ResponseEntity<Map> list(){
        Pageable showData = (Pageable) PageRequest.of(0,10);
        Page<Buku> listBuku = bookService.getBuku(showData);
        Map list = new HashMap<>();
        list.put("data", listBuku);
        list.put("statuscode", 200);
        list.put("statusMessage", "Sukses menampilkan data student");
        return new ResponseEntity<Map>(list, HttpStatus.OK);
    }

    @PostMapping("/simpan")
    public ResponseEntity<Map> save(@RequestBody Buku buku){
        Map save = bookService.insert(buku);
        return new ResponseEntity<Map>(save, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map> update(@PathVariable(value = "id") Long id,@RequestBody Buku buku){
        Map update = bookService.update(buku,id);
        return new ResponseEntity<Map>(update, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
        Map hapus = bookService.delete(id);
        return new ResponseEntity<Map>(hapus, HttpStatus.OK);
    }

    @PostMapping("/simpanTransaksi/{id}")
    public ResponseEntity<Map> save(@PathVariable(value = "id") Long id, @RequestBody PemijamanBuku pemijamanBuku){
        Map save = bookService.insert(pemijamanBuku, id);
        return new ResponseEntity<Map>(save, HttpStatus.OK);
    }

    @PutMapping("/updateTransaksi/{id}")
    public ResponseEntity<Map> update(@PathVariable(value = "id") Long id_peminjaman){
        Map update = bookService.update(id_peminjaman);
        return new ResponseEntity<Map>(update, HttpStatus.OK);
    }

    @DeleteMapping("/deletePinjam/{id}")
    public ResponseEntity<Map> deletePinjam(@PathVariable(value = "id") Long id) {
        Map hapus = bookService.deletePinjam(id);
        return new ResponseEntity<Map>(hapus, HttpStatus.OK);
    }

    @GetMapping("/listPeminjaman")
    public ResponseEntity<Map> listPeminjaman(@RequestParam() Integer page,
                                              @RequestParam() Integer size,
                                              @RequestParam(required = false) String status){
        Pageable showData = (Pageable) PageRequest.of(page, size);
        Page<PemijamanBuku> listPeminjaman = null;
        if (status != null && !status.isEmpty()){
            listPeminjaman = bookService.getPeminjamanByStatus(status,showData);
        }
        else
        {
            listPeminjaman = bookService.getDataPeminjaman(showData);
        }
        Map list = new HashMap<>();
        list.put("data", listPeminjaman);
        list.put("statuscode", 200);
        list.put("statusMessage", "Sukses menampilkan data student");
        return new ResponseEntity<Map>(list, HttpStatus.OK);
    }


}
