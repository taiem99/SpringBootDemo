package vn.techmaster.blogs.service;

import java.util.List;

import vn.techmaster.blogs.entity.Post;
import vn.techmaster.blogs.entity.User;

public interface PostServiceInterface {
    public List<Post> getAllPostOfUser(User user);

	public Post findByUserAndId(User user, Long id);
}
