package com.eadvocate.persistence.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * OppositeSide model class used for database interaction.
 */
@Entity
@Table(name = "opposite_side")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class OppositeSide {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    private String name;

    private String address;

    @Size(max = 45)
    private String phone;

    @NotNull
    private String email;

    public OppositeSide() { super();
    }
}
