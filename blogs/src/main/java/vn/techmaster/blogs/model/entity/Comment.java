package vn.techmaster.blogs.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
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

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;

    private LocalDateTime createAt;
    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
    }

    public Comment(String content){
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    public void setUser(User user){
        user.getComments().add(this);
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
