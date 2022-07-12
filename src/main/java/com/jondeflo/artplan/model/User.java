package com.jondeflo.artplan.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "public")
public class User implements UserDetails {

    public User() { }

    public User(String name, String passsword) {
        this.name = name;
        this.password = passsword;
        this.role = "ROLE_USER";
        this.failedAttempts = 0;
        this.firstFailTime = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    @Column(name = "failedAttempts")
    private Integer failedAttempts;

    @Column(name = "firstFailTime")
    private Date firstFailTime;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Transient
    private String role;

    public String getUsername()
    {
        return this.name;
    }

    public String getPassword()
    {
        return this.password;
    }

    public Integer getFailedAttempts() { return this.failedAttempts; }

    public Date getFirstFailTime() { return this.firstFailTime; }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public void setFailedAttempts(Integer amount)
    {
        this.failedAttempts = amount;
    }

    public void setFirstFailTime(Date date)
    {
        this.firstFailTime = date;
    }

    public Long getId()
    {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
