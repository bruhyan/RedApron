/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import entity.Staff;
import enumeration.Role;
import exceptions.StaffNotFoundException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import stateless.StaffControllerLocal;

/**
 *
 * @author matthealoo
 */
@Singleton
@LocalBean
public class DataInitialisation {

    @EJB
    private StaffControllerLocal staffController;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public DataInitialisation() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            staffController.retrieveStaffByEmail("systemadmin@redapron.com");
        }
        catch(StaffNotFoundException ex)
        {
            initializeData();
        }
    }

    private void initializeData() {
        
        staffController.createNewStaff(new Staff("test1","one", "systemadmin@redapron.com", "password",Role.SYSTEM_ADMIN));
        staffController.createNewStaff(new Staff("test2","two", "custsupp@redapron.com", "password", Role.CUSTOMER_SUPPORT));
        staffController.createNewStaff(new Staff("test3","three", "prodman@redapron.com", "password", Role.PRODUCT_MANAGER));
    }
    
    
    
}
