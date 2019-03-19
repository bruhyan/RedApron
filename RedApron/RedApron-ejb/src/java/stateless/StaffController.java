/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Answer;
import entity.Enquiry;
import entity.Staff;
import exceptions.EnquiryNotFoundException;
import exceptions.InvalidLoginCredentialException;
import exceptions.StaffNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author matthealoo
 */
@Stateless
@Local(StaffControllerLocal.class)


public class StaffController implements StaffControllerLocal {

    @EJB
    private EnquiryControllerLocal enquiryControllerLocal;

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;
    

    public Staff createNewStaff(Staff staff) {
        em.persist(staff);
        em.flush();
        return staff;
    }

    @Override
    public Staff retrieveStaffById(long id) throws StaffNotFoundException {
        Staff staff = em.find(Staff.class, id);

        if (staff != null) {
            return staff;
        } else {
            throw new StaffNotFoundException("Staff does not exist!");
        }

    }
    
    @Override
    public Staff retrieveStaffByEmail(String email) throws StaffNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try
        {
            return (Staff)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new StaffNotFoundException("Staff Email " + email + " does not exist!");
        }
    }
    
    
      @Override
    public List<Staff> retrieveAllStaffs()
    {
        Query query = em.createQuery("SELECT s FROM Staff s");
        
        return query.getResultList();
    }

    @Override
    public List<Answer> retrieveStaffAnswers(Long staffId) throws StaffNotFoundException {
        Staff staff = retrieveStaffById(staffId);
        Query query = em.createQuery("SELECT r FROM Answer r WHERE r.staff=:staff");
        query.setParameter("staff", staff);

        return query.getResultList();

    }
    
    @Override
    public void answerEnquiry(String answerContent, Long enquiryId, Long staffId) throws StaffNotFoundException, EnquiryNotFoundException{
        Staff staff = retrieveStaffById(staffId);
        Answer answer = new Answer(answerContent);
        Enquiry enquiry = enquiryControllerLocal.retrieveEnquiryById(enquiryId);
        
        answer.setStaff(staff);
        answer.setEnquiry(enquiry);
        staff.addAnswer(answer);
        enquiry.setAnswer(answer);
    }

    @Override
    public void deleteStaff(Long staffId) throws StaffNotFoundException{
        Staff staff = retrieveStaffById(staffId);
        em.remove(staff);
    }
    
     @Override
    public Staff staffLogin(String email, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Staff staff = retrieveStaffByEmail(email);
            
            if(staff.getPassword().equals(password))
            {
                return staff;
            }
            else
            {
                throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
            }
        }
        catch(StaffNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

}
