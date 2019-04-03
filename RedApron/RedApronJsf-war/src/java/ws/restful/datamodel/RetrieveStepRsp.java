/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Step;

/**
 *
 * @author MX
 */
public class RetrieveStepRsp {
    private Step step;

    public RetrieveStepRsp() {
    }

    public RetrieveStepRsp(Step step) {
        this.step = step;
    }

    /**
     * @return the step
     */
    public Step getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(Step step) {
        this.step = step;
    }
    
    
}
