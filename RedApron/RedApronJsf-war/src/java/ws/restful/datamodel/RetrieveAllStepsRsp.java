/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Step;
import java.util.List;

/**
 *
 * @author MX
 */
public class RetrieveAllStepsRsp {
    private List<Step> stepEntities;

    public RetrieveAllStepsRsp() {
    }

    public RetrieveAllStepsRsp(List<Step> stepEntities) {
        this.stepEntities = stepEntities;
    }

    /**
     * @return the stepEntities
     */
    public List<Step> getStepEntities() {
        return stepEntities;
    }

    /**
     * @param stepEntities the stepEntities to set
     */
    public void setStepEntities(List<Step> stepEntities) {
        this.stepEntities = stepEntities;
    }
    
    
}
