package com.url.persistence.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User model class used for database interaction.
 */
@Entity
@Data
@Table(name = "user")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    @NotNull
    @Column(length = 60)
    private String password;

    private String phone;



    @Column(name = "ACCOUNT_GROUP_LEVEL_ID")
    private Short accountGroupLevel;

    public User() {
        super();
    }






}