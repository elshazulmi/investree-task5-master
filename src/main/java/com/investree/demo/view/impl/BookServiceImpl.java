package com.investree.demo.view.impl;

import com.investree.demo.config.Config;
import com.investree.demo.model.Buku;
import com.investree.demo.model.PemijamanBuku;
import com.investree.demo.model.Student;
import com.investree.demo.repository.BukuRepository;
import com.investree.demo.repository.PeminjamanRepository;
import com.investree.demo.repository.StudentRepository;
import com.investree.demo.view.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BukuRepository bukuRepository;

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private StudentRepository studentRepository;

    Config config = new Config();

    @Override
    @Transactional
    public Page<Buku> getBuku(Pageable pageable) {
        return bukuRepository.getBook(pageable);
    }

    @Override
    @Transactional
    public Map insert(Buku buku) {
        Map map = new HashMap();
        try{
            Buku book = bukuRepository.save(buku);
            map.put("data", book);
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
    @Transactional
    public Map update(Buku buku, Long id) {
        Map map = new HashMap();
        try{
            Buku book = bukuRepository.getbyID(id);
            book.setNamaBuku(buku.getNamaBuku());
            book.setJenisBuku(buku.getJenisBuku());
            book.setJumlahBuku(buku.getJumlahBuku());
            Buku doUpdate = bukuRepository.save(book);
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
    @Transactional
    public Map delete(Long id) {
        Map map = new HashMap<>();
        try{
            Buku buku = bukuRepository.getbyID(id);
            if(buku == null){
                map.put("statusCode", "404");
                map.put("statusMessage", "Data tidak ditemukan");
                return map;
            }
            bukuRepository.delete(buku);
            map.put("statusCode", 200);
            map.put("statusMessage", "success");
        }
        catch (Exception ex){
            ex.printStackTrace();
            map.put("statusCode", "500");
            map.put("statusMessage", ex);
            return map;
        }
        return null;
    }


    @Override
    @Transactional
    public Map insert(PemijamanBuku pemijamanBuku, Long id) {
        Map map = new HashMap();
        try{
            Buku buku = bukuRepository.getbyID(id);
            Student student = studentRepository.getbyID(id);

            if(buku == null ){
                map.put("statusCode", "404");
                map.put("statusMessage", "Buku Id Not Found");
                return map;
            }
            if(buku.getJumlahBuku() != null && buku.getJumlahBuku() <= 0 ){
                map.put("statusCode", "501");
                map.put("statusMessage", "Stok buku kosong");
                return map;
            }
            int jmlbku = buku.getJumlahBuku();
            int jmlPjm = pemijamanBuku.getJumlahPinjam();
            if (jmlbku < jmlPjm){
                map.put("statusCode", "501");
                map.put("statusMessage", "Stok buku Kurang Dari Jumlah Yang diPinjam");
                return map;
            }
            if (student == null){
                map.put("statusCode", "404");
                map.put("statusMessage", "Student Id Not Found");
                return map;
            }
            pemijamanBuku.setTglPeminjaman(new Date());
            pemijamanBuku.setStatus(config.statusPinjam);
            pemijamanBuku.setBuku(buku);
            pemijamanBuku.setStudent(student);
            PemijamanBuku obj = peminjamanRepository.save(pemijamanBuku);
            buku.setJumlahBuku(jmlbku - jmlPjm);
            bukuRepository.save(buku);
            map.put("data", obj);
            map.put("statusCode", "200");
            map.put("statusMessage", "Sukses");
            return map;
        }
        catch (Exception ex){
            ex.printStackTrace();
            map.put("statusCode", "500");
            map.put("statusMessage", ex);
            return map;
        }
    }

    @Override
    public Map update( Long id_peminjaman) {
        Map map = new HashMap();
        try{
            PemijamanBuku pinjam = peminjamanRepository.getById(id_peminjaman);
            Buku buku = bukuRepository.getbyID(pinjam.getBuku().getId());
            if(pinjam == null ){
                map.put("statusCode", "404");
                map.put("statusMessage", "Data id tidak ditemukan");
                return map;
            }
            pinjam.setTglPengembalian(new Date());
            pinjam.setStatus(config.statusKembali);
            buku.setJumlahBuku(buku.getJumlahBuku() + pinjam.getJumlahPinjam());
            bukuRepository.save(buku);
            pinjam.setJumlahPinjam(pinjam.getJumlahPinjam()-pinjam.getJumlahPinjam());
            peminjamanRepository.save(pinjam);

            map.put("data", "sukses");
            map.put("statusCode", "200");
            map.put("statusMessage", "Update Sukses");
            return map;
        }
        catch (Exception ex){
            ex.printStackTrace();
            map.put("statusCode", "500");
            map.put("statusMessage", ex);
            return map;
        }
    }

    @Override
    public Map deletePinjam(Long id) {
        Map map = new HashMap();
        try{
            PemijamanBuku pinjam = peminjamanRepository.getById(id);
            if(pinjam == null){
                map.put("statusCode", "404");
                map.put("statusMessage", "data id tidak ditemuakan");
                return map;
            }

            peminjamanRepository.deleteById(pinjam.getId());
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

    @Override
    public Page<PemijamanBuku> getDataPeminjaman(Pageable pageable) {
        return peminjamanRepository.getDataPeminjaman(pageable);
    }

    @Override
    public Page<PemijamanBuku> getPeminjamanByStatus(String status,Pageable pageable) {
        return peminjamanRepository.getPeminjamanByStatus(status, pageable);
    }
}
