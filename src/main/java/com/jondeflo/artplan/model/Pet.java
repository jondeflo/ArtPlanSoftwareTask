package com.jondeflo.artplan.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pets", schema = "public")
public class Pet {

    public Pet(String name, Kind kind, Date birthdate, boolean sex, User owner) {
        this.name = name;
        this.kind = kind;
        this.birthdate = birthdate;
        this.sex = sex;
        this.owner = owner;
    }

    public Pet() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique=true)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kind_id")
    private Kind kind;

    @Column(name = "sex")
    private boolean sex;

    @Column(name = "birthdate")
    private Date birthdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind.getKind();
    }

    public boolean isSex() {
        return sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public User getOwner() { return owner; }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setOwner(User user) {this.owner = user; }

}
