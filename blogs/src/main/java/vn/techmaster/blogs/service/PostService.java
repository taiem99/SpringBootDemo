package vn.techmaster.blogs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import vn.techmaster.blogs.exception.PostException;
import vn.techmaster.blogs.model.entity.Post;
import vn.techmaster.blogs.model.entity.Tag;
import vn.techmaster.blogs.model.entity.User;
import vn.techmaster.blogs.request.CommentRequest;
import vn.techmaster.blogs.request.PostRequest;

public interface PostService {
    public List<Post> findAll();
    public Page<Post> findAllPaging(int page, int pageSize);

    public List<Post> getAllPostOfUser(User user);
    public List<Post> getAllPostsByUserId(Long user_id);

    public void createNewPost(PostRequest postRequest) throws PostException;
    public Optional<Post> findById(Long id);
    public void deletePostById(Long id);
    public void updatePost(PostRequest postRequest) throws PostException;

    public void addComment(CommentRequest commentRequest, Long user_id) throws PostException;
    
    public List<Tag> getAllTags();

    public List<Post> searchPost(String term, int limit, int offset);

    public void reindexFullText();

    public void generateSampleData();
}
