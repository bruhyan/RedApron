package exceptions;



public class ReviewNotFoundException extends Exception
{
    public ReviewNotFoundException()
    {
    }
    
    
    
    public ReviewNotFoundException(String msg)
    {
        super(msg);
    }
}