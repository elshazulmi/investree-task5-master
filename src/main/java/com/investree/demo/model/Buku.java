package com.investree.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "buku")
public class Buku implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "namaBuku", nullable = false, length = 100)
    private String namaBuku;
    @Column(name = "jenisBuku", nullable = false, length = 100)
    private String jenisBuku;
    @Column(name = "penulis", nullable = false, length = 150)
    private String penulis;
    @Column(name = "penerbit", nullable = false, length = 100)
    private String penerbit;

//    @ManyToOne
//    @JoinColumn(name = "id_staff", referencedColumnName = "id")
//    StaffPerpustakaan staffPerpustakaan;

    @OneToMany(mappedBy = "buku", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<PemijamanBuku> pemijamanBukus;

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public String getJenisBuku() {
        return jenisBuku;
    }

    public void setJenisBuku(String jenisBuku) {
        this.jenisBuku = jenisBuku;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public List<PemijamanBuku> getPemijamanBukus() {
        return pemijamanBukus;
    }

    public void setPemijamanBukus(List<PemijamanBuku> pemijamanBukus) {
        this.pemijamanBukus = pemijamanBukus;
    }
}
