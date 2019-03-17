/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Staff;
import exceptions.InvalidLoginCredentialException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import stateless.StaffControllerLocal;

/**
 *
 * @author matthealoo
 */
@Named(value = "staffLoginManagedBean")
@RequestScoped
public class StaffLoginManagedBean {

    @EJB
    private StaffControllerLocal staffController;
    private String email;
    private String password;
    
    /**
     * Creates a new instance of StaffLoginManagedBean
     */
    public StaffLoginManagedBean() {
    }
   
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
//    public void login() {         
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute("swal('Login success!', 'Congratulations you are logged in!','success')");
//      
//    }   
    
    public void login(ActionEvent event) throws IOException
    {
        try
        {
            Staff currentStaff = staffController.staffLogin(email, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentStaff", currentStaff);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/template/homeTemplateClient.xhtml");
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }
}
