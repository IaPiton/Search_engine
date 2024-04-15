package searchengine.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


@Data
@Entity
@Table(name = "Page")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @PrimaryKeyJoinColumn
    @Column(name = "path", columnDefinition = "TEXT", nullable = false)
    private String path;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;




}
