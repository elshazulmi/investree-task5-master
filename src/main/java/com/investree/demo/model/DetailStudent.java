package com.investree.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "studentdetail")
public class DetailStudent {

    @Getter
    @Setter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nim", nullable = false, length = 50)
    private String nim;
    @Column(name = "alamatStudent", nullable = true, length = 255)
    private String alamatStudent;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student", referencedColumnName = "id")
    private Student student;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getAlamatStudent() {
        return alamatStudent;
    }

    public void setAlamatStudent(String alamatStudent) {
        this.alamatStudent = alamatStudent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
