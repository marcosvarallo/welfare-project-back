package com.tcc.welfare.Welfare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tcc.welfare.Welfare.enums.AppUserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
//@Getter
//@Setter
@NoArgsConstructor
//@EqualsAndHashCode
@Entity
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private int age;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private Boolean locked = false;
    private Boolean enabled = true;

    @ManyToOne
    //@JsonIgnore // testar isso aqui para edit user
    @JoinColumn(name = "health_professional_id", referencedColumnName = "id")
    private HealthProfessional healthProfessional;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Training> training = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Diet> diet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Measures> measures = new HashSet<>();

    public User(String username, String password, String firstName, String lastName, int age, HealthProfessional healthProfessional, Set<Training> training, Set<Diet> diet, Set<Measures> measures, Long phoneNumber, AppUserRole appUserRole) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.healthProfessional = healthProfessional;
        this.training = training;
        this.diet = diet;
        this.measures = measures;
        this.phoneNumber = phoneNumber;
        this.appUserRole = appUserRole;
    }

    public User(String username, String password, AppUserRole appUserRole) {
        this.username = username;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    public void newUser(HealthProfessional healthProfessional){
        this.healthProfessional = healthProfessional;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
