/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Answer;
import static entity.Answer_.staff;
import entity.Staff;
import exceptions.StaffNotFoundException;
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
@Local(StaffControllerLocal.class)
public class StaffController implements StaffControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    public Staff persist(Staff staff) {
        em.persist(staff);
        return staff;
    }

    public Staff retrieveStaffById(long id) throws StaffNotFoundException{
        Staff staff = em.find(Staff.class, id);

        if (staff != null) {
            return staff;
        } else {
            throw new StaffNotFoundException("Staff does not exist!");
        }

    }
    
    public List<Answer> retrieveStaffAnswers(Long staffId){
        Staff staff = em.find(Staff.class, staffId);
        Query query = em.createQuery("SELECT r FROM Answer r WHERE r.staff=:staff");
        query.setParameter("staff", staff);
        
        return query.getResultList();
        
        
    }
            
}
