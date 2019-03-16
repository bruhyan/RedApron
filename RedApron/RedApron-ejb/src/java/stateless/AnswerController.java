/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Answer;
import entity.Enquiry;
import entity.Staff;
import exceptions.AnswerNotFoundException;
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
@Local(AnswerControllerLocal.class)
public class AnswerController implements AnswerControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    public Answer createNewAnswer(Answer answer) {
        em.persist(answer);
        em.flush();

        return answer;
    }

    @Override
    public Answer retrieveAnswerById(Long answerId) throws AnswerNotFoundException {
        Answer answer = em.find(Answer.class, answerId);

        if (answer != null) {
            return answer;
        } else {
            throw new AnswerNotFoundException("Answer ID " + answerId + " does not exist");
        }
    }

    public void deleteAnswerById(Long answerId) throws AnswerNotFoundException {
        Answer answer = retrieveAnswerById(answerId);

        em.remove(answer);

    }
    
    public Enquiry getEnquiryFromAnswerId(Long answerId) throws AnswerNotFoundException {
        Answer answer = retrieveAnswerById(answerId);
        
        Query query = em.createQuery("SELECT e FROM Enquiry e WHERE e.answer = :answer");
        return (Enquiry) query.getSingleResult();
        
    }
    
    public Staff getStaffFromAnswerId(Long answerId) throws AnswerNotFoundException {
        Answer answer = retrieveAnswerById(answerId);
        
        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.answers = :answer");
        return (Staff)query.getSingleResult();
    }

}
