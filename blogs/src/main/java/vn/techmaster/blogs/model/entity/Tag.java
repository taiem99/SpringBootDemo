package vn.techmaster.blogs.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    private Long id;

    private String name;

    public Tag(String name){
        this.name = name;
    }

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    List<Post> posts = new ArrayList<>();
}
