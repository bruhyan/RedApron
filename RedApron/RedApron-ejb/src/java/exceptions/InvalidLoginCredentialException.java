package exceptions;



public class InvalidLoginCredentialException extends Exception
{
    public InvalidLoginCredentialException()
    {
    }
    
    
    
    public InvalidLoginCredentialException(String msg)
    {
        super(msg);
    }
}