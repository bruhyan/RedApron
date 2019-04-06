/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Step;
import exceptions.StepNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MX
 */
@Local
public interface StepControllerLocal {

    public Step createNewStep(Step step);

    public Step retrieveStepById(long id) throws StepNotFoundException;

    public List<Step> retrieveAllSteps();

    public void updateStep(Step step) throws StepNotFoundException;
    
}
