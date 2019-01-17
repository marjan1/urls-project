package com.eadvocate.persistence.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * AdvocateCompany model class used for database interaction.
 */
@Entity
@Data
@Table(name = "advocate_company")
public class AdvocateCompany {

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

    @Size(max = 100)
    private String embs;

    @Size(max = 100)
    private String edbs;

    private String license;

    private String digitalSignature;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;



    public AdvocateCompany() {
        super();
    }






}