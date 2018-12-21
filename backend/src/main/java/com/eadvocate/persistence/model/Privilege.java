package com.eadvocate.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100)
    private String name;

    @ManyToMany(mappedBy = "privileges", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles;

    public Privilege() {
        super();
    }

    public Privilege(final String name) {
        super();
        this.name = name;
    }


}
