package searchengine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lemma",indexes = @Index(columnList = "lemma, site_id"))


@RequiredArgsConstructor
public class Lemma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Site site;
    @Column(name = "lemma", nullable = false, columnDefinition = "Varchar(255)")
    private String lemma;
    @Column(name = "frequency", nullable = false)
    private Integer frequency;
    @OneToMany(mappedBy = "lemma")
    private List<Indexes> indexes;
}
