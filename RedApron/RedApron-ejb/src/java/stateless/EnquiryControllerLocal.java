/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Enquiry;
import exceptions.EnquiryNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
@Local
public interface EnquiryControllerLocal {
    
     public Enquiry persist(Enquiry enquiry);
     public Enquiry retrieveEnquiryById(Long enquiryId) throws EnquiryNotFoundException;
    
}
