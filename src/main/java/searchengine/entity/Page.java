package searchengine.entity;

import lombok.Data;


import javax.persistence.*;


@Entity
@Table(name = "Page")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @PrimaryKeyJoinColumn
    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;



}
