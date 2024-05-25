package searchengine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "page", indexes = @Index(name = "path_index", columnList = "path"))
@RequiredArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "path", columnDefinition = "TEXT", nullable = false)
    private String path;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Indexes> indexes = new ArrayList<>();

}
