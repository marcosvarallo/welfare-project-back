package com.tcc.welfare.Welfare.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Measures implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    private float weight;
    private double height;
    private float imc;
    private float fatMassPercentage;
    private float leanMassPercentage;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void newMeasure(User user){
        this.user = user;
    }

    public Measures(Date date, float weight, float height, float imc, float fatMassPercentage, float leanMassPercentage) {
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.imc = imc;
        this.fatMassPercentage = fatMassPercentage;
        this.leanMassPercentage = leanMassPercentage;
    }
}
