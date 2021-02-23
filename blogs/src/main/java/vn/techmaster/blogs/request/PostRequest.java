package vn.techmaster.blogs.request;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.techmaster.blogs.model.entity.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private Long id;

    @NotNull
    @Size(min = 10, max = 300, message = "Title must be with 10 and 300")
    private String title;

    @NotNull
    @Size(min = 20, max = 5000, message = ("Content must be with 20 and 5000"))
    private String content;
    private Long user_id;
    private Set<Tag> tags; 
}
