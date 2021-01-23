package vn.techmaster.blogs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.techmaster.blogs.entity.Post;
import vn.techmaster.blogs.entity.User;

@Service
public class PostService implements PostServiceInterface {

    @Override
	public List<Post> getAllPostOfUser(User user) {
		return user.getPosts();
	}

	@Override
	public Post findByUserAndId(User user, Long id) {
		List<Post> posts = getAllPostOfUser(user);
		Optional<Post> post = posts.stream().filter(p -> p.getId() == id).findFirst();
		if(post != null){
			return post.get();
		}
		return null;
	}
    
}
