package vn.techmaster.blogs.controller;

import java.net.CookieManager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.blogs.entity.Post;
import vn.techmaster.blogs.repository.CommentRepository;
import vn.techmaster.blogs.repository.PostRepository;
import vn.techmaster.blogs.repository.UserRepository;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CookieManager cookieManager;

    @Autowired
    CommentRepository commentRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    PostRepository postRepo;

    @GetMapping
    public String getAddComment(Long id, Model model, HttpServletRequest request){
        model.addAttribute("postId", id);
        model.addAttribute("comment", new Post());
        return "comment";
    }


}
