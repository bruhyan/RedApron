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
import exceptions.EnquiryNotFoundException;
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
        query.setParameter("answer", answer);
        return (Enquiry) query.getSingleResult();

    }

    @Override
    public Answer getAnswerFromEnquiryId(Long enquiryId) throws EnquiryNotFoundException {
        Query query = em.createQuery("SELECT a FROM Answer a WHERE a.enquiry.enquiryId = :enquiry");
        query.setParameter("enquiry", enquiryId);

        Answer answer = (Answer) query.getSingleResult();

        answer.getStaff();

        return answer;

    }

    public Staff getStaffFromAnswerId(Long answerId) throws AnswerNotFoundException {
        Answer answer = retrieveAnswerById(answerId);

        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.answers = :answer");
        return (Staff) query.getSingleResult();
    }
    
    @Override
    public void updateAnswer(Answer answer) throws AnswerNotFoundException{
        Answer answerToUpdate = retrieveAnswerById(answer.getAnswerId());
        answerToUpdate.setText(answer.getText());
        answerToUpdate.setStaff(answer.getStaff());
        answerToUpdate.setEnquiry(answer.getEnquiry());
    }

}
