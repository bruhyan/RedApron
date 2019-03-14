package exceptions;



public class EnquiryNotFoundException extends Exception
{
    public EnquiryNotFoundException()
    {
    }
    
    
    
    public EnquiryNotFoundException(String msg)
    {
        super(msg);
    }
}