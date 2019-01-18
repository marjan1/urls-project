package com.eadvocate.persistence.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * OppositeSideAdvocateDto model class used for database interaction.
 */
@Data
@Entity
public class OppositeSideAdvocate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String address;

    private String phone;

    @NotNull
    private String email;

    public OppositeSideAdvocate() {
        super();
    }
}
