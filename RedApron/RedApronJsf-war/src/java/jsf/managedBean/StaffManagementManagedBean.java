/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Staff;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import stateless.StaffControllerLocal;

/**
 *
 * @author MX
 */
@Named(value = "staffManagementManagedBean")
@RequestScoped
public class StaffManagementManagedBean {

    @EJB
    private StaffControllerLocal staffControllerLocal;
    
    private Staff newStaff;
    private List <Staff> staffEntities;
    private List <Staff> filteredStaffEntities;
    
    public StaffManagementManagedBean() {
        newStaff = new Staff();
    }
    
    @PostConstruct
    public void postConstruct(){
        staffEntities = staffControllerLocal.retrieveAllStaffs();
    }
    
    public void viewStaffDetails(ActionEvent event) throws IOException{
        Long staffIdToView = (Long) event.getComponent().getAttributes().get("staffId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("staffIdToView", staffIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllStaffs.xhtml");
    }
    
    public Staff getStaff() {
        return newStaff;
    }

    public void setStaff(Staff staff) {
        this.newStaff = staff;
    }

    public List<Staff> getStaffEntities() {
        return staffEntities;
    }

    public void setStaffEntities(List<Staff> staffEntities) {
        this.staffEntities = staffEntities;
    }


    public List <Staff> getFilteredStaffEntities() {
        return filteredStaffEntities;
    }


    public void setFilteredStaffEntities(List <Staff> filteredStaffEntities) {
        this.filteredStaffEntities = filteredStaffEntities;
    }
    
}
