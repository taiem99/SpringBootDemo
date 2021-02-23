package vn.techmaster.blogs.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.techmaster.blogs.exception.AuthenException;
import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.request.LoginRequest;

public interface AuthenService {
    public UserInfo login(LoginRequest loginRequest) throws AuthenException;
    public boolean isLogged(HttpServletRequest request);
    public UserInfo getLoggedUser(HttpServletRequest request);
    public void setLoggedCookie(HttpServletResponse response, UserInfo user);
    public void clearLoggedCookie(HttpServletResponse response);
}
