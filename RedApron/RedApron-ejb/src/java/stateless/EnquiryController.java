/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Answer;
import entity.Enquiry;
import exceptions.EnquiryNotFoundException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author matthealoo
 */
@Stateless
@Local(EnquiryControllerLocal.class)

public class EnquiryController implements EnquiryControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    @Override
    public Enquiry createNewEnquiry(Enquiry enquiry) {
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

    @Override
    public List<Enquiry> retrieveAllEnquiries() {

        Query query = em.createQuery("SELECT e FROM Enquiry e");

        List<Enquiry> enquiries = query.getResultList();

        for (Enquiry enquiry : enquiries) {
            enquiry.getSubscriber();
            enquiry.getAnswer();
        }

        return enquiries;

    }

    @Override
    public List<Enquiry> retrieveEnquiryBySubscriber(Long subscriberId) {
        Query query = em.createQuery("SELECT e FROM Enquiry e WHERE e.subscriber.subscriberId := subscriberId");
        query.setParameter("subscriberId", subscriberId);
        List<Enquiry> enquiryEntities = query.getResultList();
        return enquiryEntities;
    }

    @Override
    public void deleteEnquiry(Long enquiryId) throws EnquiryNotFoundException {

        Enquiry enquiry = retrieveEnquiryById(enquiryId);
        em.remove(enquiry);

    }

    @Override
    public void updateEnquiry(Enquiry enquiry) throws EnquiryNotFoundException {
        
        Enquiry enquiryToUpdate = retrieveEnquiryById(enquiry.getEnquiryId());
        
        

        enquiryToUpdate.setAnswer(enquiry.getAnswer());
        enquiryToUpdate.setText(enquiry.getText());

    }
}
