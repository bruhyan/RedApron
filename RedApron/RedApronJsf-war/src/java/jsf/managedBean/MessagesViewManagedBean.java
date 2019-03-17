/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author mdk12
 */
@Named(value = "messagesViewManagedBean")
@RequestScoped
public class MessagesViewManagedBean {


    public MessagesViewManagedBean() {
    }
    
    public void done() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Action Completed!", "New entry added."));
    }
    
     public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Encountered.", "Please try again."));
    }
    
}
