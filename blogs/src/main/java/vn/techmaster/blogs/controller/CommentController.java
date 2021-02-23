package vn.techmaster.blogs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blogs.exception.PostException;
import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.request.CommentRequest;
import vn.techmaster.blogs.service.AuthenService;
import vn.techmaster.blogs.service.PostService;

@Controller
public class CommentController {
    
    @Autowired
    private AuthenService authenService;
    @Autowired
    private PostService postService;

    @PostMapping("/comment")
    public String handlePostComment(
        @ModelAttribute CommentRequest commentRequest,
        HttpServletRequest request){

            UserInfo user = authenService.getLoggedUser(request);
            if (user != null){
                try{
                    postService.addComment(commentRequest, user.getId());
                } catch (PostException e){
                    e.printStackTrace();
                }
                return "redirect:/post/" + commentRequest.getPost_id();
            } else {
                return Route.HOME;
            }
    }
}
