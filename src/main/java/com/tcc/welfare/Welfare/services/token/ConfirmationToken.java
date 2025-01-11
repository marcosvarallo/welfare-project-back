package com.tcc.welfare.Welfare.services.token;

import com.tcc.welfare.Welfare.model.HealthProfessional;
import com.tcc.welfare.Welfare.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

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

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "health_professional_id"
    )
    private HealthProfessional healthProfessional;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, HealthProfessional healthProfessional) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.healthProfessional = healthProfessional;
    }
}
