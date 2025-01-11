package com.tcc.welfare.Welfare.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcc.welfare.Welfare.enums.ObjetiveType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Diet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private ObjetiveType objetiveType;

    private String informations;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void newDiet(User user){
        this.user = user;
    }

    public Diet(Date startDate, Date endDate, ObjetiveType objetiveType, String informations) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.objetiveType = objetiveType;
        this.informations = informations;
    }
}