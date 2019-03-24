/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Staff;
import exceptions.StaffNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import stateless.StaffControllerLocal;

/**
 *
 * @author matthealoo
 */
@Named(value = "myProfileManagedBean")
@RequestScoped
public class MyProfileManagedBean {

    @EJB
    private StaffControllerLocal staffController;

    private Staff staff;

    
    /**
     * Creates a new instance of MyProfileManagedBean
     */
    public MyProfileManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try {
        staff = staffController.retrieveStaffById(1); //Need to pass in the current staff that logged in
        } catch (StaffNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
    public void saveStaff(ActionEvent event)
    {
        try {
            staffController.updateStaff(staff);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff profile " + staff.getStaffId() + " updated successfully", null));
        } catch (StaffNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    
}
