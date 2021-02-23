package vn.techmaster.blogs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private Long post_id;
    private String content;

    public CommentRequest(Long post_id){
        this.post_id = post_id;
    }
}
