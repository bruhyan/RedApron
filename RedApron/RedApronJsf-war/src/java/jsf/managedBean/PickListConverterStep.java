/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Step;
import exceptions.StepNotFoundException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import stateless.StepControllerLocal;


@FacesConverter(value = "pickListConverterStep")

public class PickListConverterStep implements Converter, Serializable {

    StepControllerLocal stepController = lookupStepControllerLocal();

    /**
     * Creates a new instance of PickListConverter
     */

    public PickListConverterStep() {
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            System.out.println("HEYYOOOO");
            long stepId = Long.parseLong(value);
            System.out.println(stepId);

            Step step = lookupStepControllerLocal().retrieveStepById(stepId);
            

            return step;
        } catch(StepNotFoundException ex){
            System.out.println("No Such step found!");
        }
        return null;
    }

    private StepControllerLocal lookupStepControllerLocal() {
        try {
            Context c = new InitialContext();
            return (StepControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/StepController!stateless.StepControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
