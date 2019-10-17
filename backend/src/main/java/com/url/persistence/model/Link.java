package com.url.persistence.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @OneToMany(mappedBy="link")
    private Set<LinkParam> linkParams;
}
