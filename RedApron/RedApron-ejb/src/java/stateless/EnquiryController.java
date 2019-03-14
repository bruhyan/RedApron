/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Answer;
import entity.Enquiry;
import exceptions.AnswerNotFoundException;
import exceptions.EnquiryNotFoundException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matthealoo
 */
@Stateless
@Local(EnquiryControllerLocal.class)

public class EnquiryController implements EnquiryControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    public Enquiry persist(Enquiry enquiry) {
        em.persist(enquiry);
        em.flush();
        
        return enquiry;
    }
    
    @Override
    public Enquiry retrieveEnquiryById(Long enquiryId) throws EnquiryNotFoundException {
        Enquiry enquiry = em.find(Enquiry.class, enquiryId);

        if (enquiry != null) {
            return enquiry;
        } else {
            throw new EnquiryNotFoundException("Enquiry ID " + enquiryId + " does not exist");
        }
    }

    
}
