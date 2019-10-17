package com.url.persistence.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "link_param")
public class LinkParam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    private String param;


    @ManyToOne
    @JoinColumn(name="link_id", nullable=false)
    private Link link;
}
