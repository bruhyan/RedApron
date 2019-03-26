/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Staff;
import exceptions.StaffNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
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
        
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        staff = (Staff) sessionMap.get("currentStaff"); //Need to pass in the current staff that logged in
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
    
      public void saveStaff(Staff staff)
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
    
    public void handleFileUpload(FileUploadEvent event)
    {
        try
        {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true)
            {
                a = inputStream.read(buffer);

                if (a < 0)
                {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            staff.setPicURL(newFilePath);
            saveStaff(staff);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "File uploaded successfully", ""));
        }
        catch(IOException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "File upload error: " + ex.getMessage(), ""));
        }
    }

    
}
