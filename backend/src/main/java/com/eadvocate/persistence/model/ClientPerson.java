package com.eadvocate.persistence.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * ClientPerson model class used for database interaction.
 */
@Entity
@Table(name = "client_person")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Data
public class ClientPerson extends Client {

    public String personalId;

    public String embg;

}
