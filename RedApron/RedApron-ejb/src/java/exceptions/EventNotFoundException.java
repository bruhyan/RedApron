package exceptions;



public class EventNotFoundException extends Exception
{
    public EventNotFoundException()
    {
    }
    
    
    
    public EventNotFoundException(String msg)
    {
        super(msg);
    }
}