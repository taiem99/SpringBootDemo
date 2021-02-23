package vn.techmaster.blogs.service;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.blogs.exception.AuthenException;
import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.User;
import vn.techmaster.blogs.model.mapper.UserMapper;
import vn.techmaster.blogs.reponsitory.UserRepository;
import vn.techmaster.blogs.request.LoginRequest;

@Service
public class AuthenServiceImpl implements AuthenService {

    @Autowired
    private UserRepository userRepo;

    private static final String LOGIN_COOKIE  = "loginsuccess";

    @Override
    public UserInfo login(LoginRequest loginRequest) throws AuthenException{
        var optionalUser = userRepo.findByEmail(loginRequest.getEmail());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(!user.getPassword().equals(loginRequest.getPassword())){
                throw new AuthenException("wrong password!");
            } else {
                return UserMapper.INSTANCE.userToUserInfo(user);
            }
        } else {
            throw new AuthenException("User with email " + loginRequest.getEmail() + " does not exist");
        }
    }

    @Override
    public boolean isLogged(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(var cookie:cookies){
                if(cookie.getName().equals(LOGIN_COOKIE)){
                    Long userId = Long.parseLong(cookie.getValue());
                    Optional<User> user = userRepo.findById(userId);
                    return user.isPresent();
                }
            }
        }
        return false;
    }

    @Override
    public UserInfo getLoggedUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(var cookie:cookies){
                if (cookie.getName().equals(LOGIN_COOKIE)){
                    Long userId = Long.parseLong(cookie.getValue());
                    Optional<User> optionalUser = userRepo.findById(userId);
                        if(optionalUser.isPresent()){
                            return UserMapper.INSTANCE.userToUserInfo(optionalUser.get());
                        } else {
                            return null;
                        }
                }
            }
        }
        return null;
    }

    @Override
    public void setLoggedCookie(HttpServletResponse response, UserInfo user) {
        Cookie loginCookie = new Cookie(LOGIN_COOKIE, String.valueOf(user.getId()));
        loginCookie.setMaxAge((30 * 60));
        loginCookie.setPath("/");
        response.addCookie(loginCookie);
    }

    @Override
    public void clearLoggedCookie(HttpServletResponse response) {
        Cookie loginCookie = new Cookie(LOGIN_COOKIE, null);
        loginCookie.setMaxAge(0);
        loginCookie.setHttpOnly(true);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);

    }
    
}
