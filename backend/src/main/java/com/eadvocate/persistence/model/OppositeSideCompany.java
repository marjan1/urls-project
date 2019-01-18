package com.eadvocate.persistence.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * OppositeSideCompanyDto model class used for database interaction.
 */
@Entity
@Table(name = "opposite_side_company")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Data
public class OppositeSideCompany extends OppositeSide {

    public String embs;

    public String edb;
}
