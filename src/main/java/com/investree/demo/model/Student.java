package com.investree.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
@Where(clause = "deleted_date is null")
public class Student extends AbstractDate{

    @Setter
    @Getter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 150)
    private String lastName;
    @Column(name = "angkatan", nullable = false)
    private int angkatan;
    @Column(name = "jurusan", nullable = false, length = 50)
    private String jurusan;
    @Column(name = "fakultas", nullable = false, length = 50)
    private String fakultas;

    @Getter
    @Setter
    private String photo;

    @OneToOne(mappedBy = "student")
    private DetailStudent studentDetail;

    @OneToMany(mappedBy = "student")
    List<PemijamanBuku> pemijamanBukus;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DetailStudent getStudentDetail() {
        return studentDetail;
    }

    public void setStudentDetail(DetailStudent studentDetail) {
        this.studentDetail = studentDetail;
    }

    public int getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(int angkatan) {
        this.angkatan = angkatan;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public List<PemijamanBuku> getPemijamanBukus() {
        return pemijamanBukus;
    }

    public void setPemijamanBukus(List<PemijamanBuku> pemijamanBukus) {
        this.pemijamanBukus = pemijamanBukus;
    }
}
