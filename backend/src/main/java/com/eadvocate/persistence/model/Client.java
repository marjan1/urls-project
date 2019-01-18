package com.eadvocate.persistence.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Client model class used for database interaction.
 */
@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Client {

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

    @NotNull
    private String accountNumber;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "advocate_company_id")
    private AdvocateCompany advocateCompany;

    public Client() {
        super();
    }
}
