package vn.techmaster.blogs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blogs.request.LoginRequest;
import vn.techmaster.blogs.security.CookieManager;
import vn.techmaster.blogs.service.AuthenException;
import vn.techmaster.blogs.service.AuthenServiceInterface;

@Controller
public class HomeController {
    @Autowired
    private AuthenServiceInterface authenService;

    @Autowired
    private CookieManager cookieManager;

    public static final String LOGIN_REQUEST = "loginRequest";

    @GetMapping
    public String getHome(){
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpServletRequest request){
        if(cookieManager.getAuthenticatedEmail(request) != null){
            return Router.REDIRECT_POSTS;
        }
        model.addAttribute(LOGIN_REQUEST, new LoginRequest());
        return Router.LOGIN_TEMPLATE;  
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute LoginRequest loginRequest, BindingResult result,
        Model model, HttpServletResponse response){
            if(!result.hasErrors()){
                try {
                    authenService.login(loginRequest);
                    cookieManager.setAuthenticated(response, loginRequest.getEmail());
                    return Router.REDIRECT_POSTS;
                } catch (AuthenException e){
                    model.addAttribute(LOGIN_REQUEST, new LoginRequest(loginRequest.getEmail(), ""));
                    model.addAttribute("errorMessage", e);
                    return Router.LOGIN_TEMPLATE;
                } 
            } else {
                model.addAttribute(LOGIN_REQUEST, new LoginRequest());
                model.addAttribute("errorMessage", "Submit is invalid");
                return Router.LOGIN_TEMPLATE;
            }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        cookieManager.setNotAuthenticated(response);
        return Router.REDIRECT_HOME;
    }
}
