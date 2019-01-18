package com.eadvocate.persistence.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * ClientCompany model class used for database interaction.
 */
@Entity
@Table(name = "client_company")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Data
public class ClientCompany extends Client{

    @NotNull
    @Size(max = 7)
    public String embs;

    @Size(max = 13)
    public String edb;

    @Size(max = 100)
    public String managerName;
}
