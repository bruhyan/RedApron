/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Staff;
import exceptions.StaffNotFoundException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
