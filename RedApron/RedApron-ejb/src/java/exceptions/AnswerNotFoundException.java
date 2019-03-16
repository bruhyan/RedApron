package exceptions;



public class AnswerNotFoundException extends Exception
{
    public AnswerNotFoundException()
    {
    }
    
    
    
    public AnswerNotFoundException(String msg)
    {
        super(msg);
    }
}