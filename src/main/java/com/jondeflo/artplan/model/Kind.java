package com.jondeflo.artplan.model;

import javax.persistence.*;

@Entity
@Table(name = "s_kind", schema = "public")
public class Kind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "kind", nullable = false)
    private String kind;

    public String getKind() {
        return kind;
    }
}
