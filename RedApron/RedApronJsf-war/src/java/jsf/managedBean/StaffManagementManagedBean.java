/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Staff;
import exceptions.StaffNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import stateless.StaffControllerLocal;

/**
 *
 * @author MX
 */
@Named(value = "staffManagementManagedBean")
@ViewScoped
public class StaffManagementManagedBean implements Serializable{

    @EJB
    private StaffControllerLocal staffControllerLocal;
    
    private Staff newStaff;
    private List <Staff> staffEntities;
    private List <Staff> filteredStaffEntities;
    private Staff selectedStaffEntityToView;
    private Staff selectedStaffEntityToUpdate;
    
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
    
    public void doUpdateStaff(ActionEvent event)
    {
        selectedStaffEntityToUpdate = (Staff)event.getComponent().getAttributes().get("staffEntityToUpdate");
    }
    
    public void updateStaff(ActionEvent event)
    {
        try
        {
            staffControllerLocal.updateStaff(selectedStaffEntityToUpdate);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff updated successfully", null));
        }
        catch(StaffNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating staff: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
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

    public Staff getSelectedStaffEntityToView() {
        return selectedStaffEntityToView;
    }

    public void setSelectedStaffEntityToView(Staff selectedStaffEntityToView) {
        this.selectedStaffEntityToView = selectedStaffEntityToView;
    }

    public Staff getSelectedStaffEntityToUpdate() {
        return selectedStaffEntityToUpdate;
    }

    public void setSelectedStaffEntityToUpdate(Staff selectedStaffEntityToUpdate) {
        this.selectedStaffEntityToUpdate = selectedStaffEntityToUpdate;
    }
    
}
