package vn.techmaster.blogs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.blogs.repository.UserRepository;
import vn.techmaster.blogs.request.LoginRequest;

@Service
public class AuthenService implements AuthenServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void login(LoginRequest loginRequest) throws AuthenException {
        var user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.isPresent()){
            if(!user.get().getPassword().equals(loginRequest.getPassword())){
                throw new AuthenException("Wrong password!");
            }
        } else {
            throw new AuthenException("User with email " + loginRequest.getEmail() + " does not exist!");
        }
    }
    
}
