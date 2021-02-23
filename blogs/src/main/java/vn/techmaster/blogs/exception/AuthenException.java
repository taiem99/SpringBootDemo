package vn.techmaster.blogs.exception;

public class AuthenException extends Exception {

    private static final long serialVersionUID = 2430283849291408816L;
    
    public AuthenException(String message){
        super(message);
    }
}
