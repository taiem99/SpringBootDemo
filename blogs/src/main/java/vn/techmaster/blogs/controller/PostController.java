package vn.techmaster.blogs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import vn.techmaster.blogs.entity.Post;
import vn.techmaster.blogs.entity.User;
import vn.techmaster.blogs.repository.UserRepository;
import vn.techmaster.blogs.security.CookieManager;
import vn.techmaster.blogs.service.PostServiceInterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private CookieManager cookieManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostServiceInterface postService;

    @GetMapping
    public String getAllPosts(HttpServletRequest request, Model model) {
        List<Post> posts = new ArrayList<>();
        String loginEmail = cookieManager.getAuthenticatedEmail(request);
        Optional<User> user = userRepo.findByEmail(loginEmail);
        if(user.isPresent()){
            posts = postService.getAllPostOfUser(user.get());
        }
        model.addAttribute("user", user.get());
        model.addAttribute("posts", posts);
        return Router.ALLPOSTS;
    }

    @GetMapping("/new-post")
    public String getCreatePost(Model model, HttpServletRequest request){
        String userEmail = cookieManager.getAuthenticatedEmail(request);
        Optional<User> user = userRepo.findByEmail(userEmail);
        if(user.isPresent()){
            model.addAttribute("user", user.get());
            model.addAttribute("post", new Post());
        }
      return "newpost";
    }
    
    @PostMapping("/save")
    public String saveNewPost(@ModelAttribute Post post, BindingResult result, Model model, HttpServletRequest request){
        User user = null;
        String userEmail = cookieManager.getAuthenticatedEmail(request);
        Optional<User> userOptional = userRepo.findByEmail(userEmail);
        if(userOptional.isPresent()){
            user = userOptional.get();
        }
        if(post.getId() == null){
            user.addPost(post);
            userRepo.save(user);
        } else {
            Post postTmp = postService.findByUserAndId(user, post.getId());
            postTmp.setTitle(post.getTitle());
            postTmp.setContent(post.getContent());
            userRepo.save(user);
        }
        return Router.REDIRECT_POSTS;
    }


    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        User user = null;
        Post post = null;
        String userEmail = cookieManager.getAuthenticatedEmail(request);
        Optional<User> userOptional = userRepo.findByEmail(userEmail);
        if(userOptional.isPresent()){
            user = userOptional.get();
            post = postService.findByUserAndId(user, id);
        }

        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        User user = null;
        Post post = null;
        Optional<User> userOptional = userRepo.findByEmail(cookieManager.getAuthenticatedEmail(request));
        if(userOptional.isPresent()){
            user = userOptional.get();
            post = postService.findByUserAndId(user, id);
        }

        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "post";
    }
}
