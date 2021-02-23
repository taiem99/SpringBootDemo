package vn.techmaster.blogs.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blogs.exception.PostException;
import vn.techmaster.blogs.model.dto.PostPOJO;
import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.Comment;
import vn.techmaster.blogs.model.entity.Post;
import vn.techmaster.blogs.model.entity.Tag;
import vn.techmaster.blogs.model.mapper.PostMapper;
import vn.techmaster.blogs.model.mapper.UserMapper;
import vn.techmaster.blogs.request.CommentRequest;
import vn.techmaster.blogs.request.IdRequest;
import vn.techmaster.blogs.request.PostRequest;
import vn.techmaster.blogs.service.AuthenService;
import vn.techmaster.blogs.service.PostService;

@Controller
public class PostController {
    @Autowired
    private AuthenService authenService;

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String getAllPosts(Model model, HttpServletRequest request){
        UserInfo user = authenService.getLoggedUser(request);
        if (user != null){
            model.addAttribute("user", user);
            List<Post> posts = postService.getAllPostsByUserId(user.getId());
            model.addAttribute("posts", posts);
            return Route.ALLPOSTS;
        } else {
            return Route.REDIRECT_HOME;
        }
    }

    @GetMapping("/post")
    public String createEditPostForm(Model model, HttpServletRequest request){
        UserInfo user = authenService.getLoggedUser(request);
        if(user != null){
            PostRequest postRequest = new PostRequest();
            postRequest.setUser_id(user.getId());
            model.addAttribute("post", postRequest);
            model.addAttribute("user", user);
            List<Tag> tags = postService.getAllTags();
            model.addAttribute("allTags", tags);
            return Route.POST;
        } else {
            return Route.REDIRECT_HOME;
        }
    }

    @PostMapping("/post")
    public String createEditPostSubmit(@Valid @ModelAttribute("post") PostRequest postRequest,
        BindingResult bindingResult,
        Model model,
        HttpServletRequest request){
            UserInfo user = authenService.getLoggedUser(request);
            if(bindingResult.hasErrors()){
                List<Tag> tags = postService.getAllTags();
                model.addAttribute("tags", tags);
                return Route.POST;
            }
            if (user != null && user.getId() == postRequest.getUser_id()){
                try {
                    if (postRequest.getId() == null){
                        postService.createNewPost(postRequest);
                    } else {
                        postService.updatePost(postRequest);
                    }
                } catch (PostException e){
                    return Route.REDIRECT_HOME;
                }
                return Route.REDIRECT_POSTS;
            } else {
                return Route.REDIRECT_HOME;
            }   
    }

    @GetMapping("/post/{id}")
    public String getPostAndComment(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        Optional<Post> oPost = postService.findById(id);
        if(oPost.isPresent()){
            Post post = oPost.get();
            PostPOJO postPojo = PostMapper.INSTANCE.postToPostPOJO(post);
            model.addAttribute("post", postPojo);

            Set<Tag> tags = post.getTags();
            model.addAttribute("tags", tags);

            List<Comment> comments = post.getComments();
            model.addAttribute("comments", comments);

            UserInfo user = authenService.getLoggedUser(request);
            if(user != null ){
                model.addAttribute("user", user);
                model.addAttribute("commentRequest", new CommentRequest(postPojo.getId()));
            } else {
                model.addAttribute("commentRequest", new CommentRequest());
            }
            return Route.POST_COMMENT;
        } else {
            return Route.REDIRECT_HOME;
        }
    }


    @PostMapping("/post/delete")
    public String deletePost(@ModelAttribute IdRequest idRequest, HttpServletRequest request){
        UserInfo user = authenService.getLoggedUser(request);
        if (user != null){
            postService.deletePostById(idRequest.getId());
        }
        return Route.REDIRECT_POSTS;
    }

    @PostMapping("/post/edit")
    public String editPost(@ModelAttribute IdRequest idRequest, Model model, HttpServletRequest request){
        UserInfo user = authenService.getLoggedUser(request);
        Optional<Post> oPost = postService.findById(idRequest.getId());
        if(oPost.isPresent() && user != null){
            Post post = oPost.get();
            PostRequest postRequest = PostMapper.INSTANCE.postToPostRequest(post);
            model.addAttribute("post", postRequest);
            List<Tag> tags = postService.getAllTags();
            model.addAttribute("allTags", tags);
            UserInfo userInfo = UserMapper.INSTANCE.userToUserInfo(post.getUser());
            model.addAttribute("user", userInfo);
            return Route.POST;
        } else {
            return Route.REDIRECT_POSTS;
        }
    }
}
