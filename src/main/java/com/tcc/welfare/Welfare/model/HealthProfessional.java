package com.tcc.welfare.Welfare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tcc.welfare.Welfare.enums.AppUserRole;
import com.tcc.welfare.Welfare.enums.HealthProfessionalType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
//@Getter
//@Setter
@NoArgsConstructor
//@EqualsAndHashCode
@Entity
public class HealthProfessional implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    private HealthProfessionalType type;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;

    private Boolean locked = false;
    private Boolean enabled = false;

    @JsonIgnore
    @OneToMany(mappedBy = "healthProfessional")
    private Set<User> user = new HashSet<>();

    public HealthProfessional(String firstName, String lastName, String email, String password, int age, HealthProfessionalType type, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.type = type;
        this.appUserRole = appUserRole;
    }

    public HealthProfessional(String email, String password, AppUserRole appUserRole) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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