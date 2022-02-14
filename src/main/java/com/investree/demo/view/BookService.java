package com.investree.demo.view;

import com.investree.demo.model.Buku;
import com.investree.demo.model.PemijamanBuku;
import com.investree.demo.repository.PeminjamanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface BookService {
    public Page<Buku> getBuku(Pageable pageable);

    public Map insert(Buku buku);

    public Map update(Buku buku, Long id);

    public Map delete(Long id);

    public Map insert(PemijamanBuku pemijamanBuku, Long id);

    public Map update(Long id_peminjaman);

    public Map deletePinjam(Long id);

    public Page<PemijamanBuku> getDataPeminjaman(Pageable pageable);

    public Page<PemijamanBuku> getPeminjamanByStatus(String status, Pageable pageable);
}
