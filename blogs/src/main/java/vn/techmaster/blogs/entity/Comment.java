package vn.techmaster.blogs.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "comment")
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private LocalDateTime lastUpdate;

    @PrePersist
    public void prePersist(){
        this.lastUpdate = LocalDateTime.now();
    }

    public Comment(String content){
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User commenter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
