package com.investree.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "peminjaman")
public class PemijamanBuku implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "tglPeminjaman", nullable = true)
    private Date tglPeminjaman;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "buku_id", referencedColumnName = "id")
    Buku buku;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    Student student;

    @Getter
    @Setter
    @Column(name = "tglPengembalian", nullable = true)
    private Date tglPengembalian;

    @Getter
    @Setter
    @Column(name = "status", nullable = false, length = 50)
    private String status;


}
