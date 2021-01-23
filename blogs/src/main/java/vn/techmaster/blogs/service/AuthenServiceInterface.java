package vn.techmaster.blogs.service;

import vn.techmaster.blogs.request.LoginRequest;

public interface AuthenServiceInterface {
    public void login(LoginRequest request) throws AuthenException;
}
