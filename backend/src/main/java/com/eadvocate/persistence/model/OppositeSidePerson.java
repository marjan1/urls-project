package com.eadvocate.persistence.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * OppositeSidePersonDto model class used for database interaction.
 */
@Entity
@Table(name = "opposite_side_person")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Data
public class OppositeSidePerson extends OppositeSide{

    public String embg;
}
