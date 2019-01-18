package com.eadvocate.persistence.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CourtDto model class used for database interaction.
 */
@Data
@Entity
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    private String name;

    private String address;

    @Size(max = 45)
    private String archivePhone;

    @NotNull
    private String archiveEmail;


}


