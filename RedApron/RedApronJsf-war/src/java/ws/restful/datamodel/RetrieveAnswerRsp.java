/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Answer;

/**
 *
 * @author MX
 */
public class RetrieveAnswerRsp {
    private Answer answer;

    public RetrieveAnswerRsp() {
    }

    public RetrieveAnswerRsp(Answer answer) {
        this.answer = answer;
    }

    /**
     * @return the answer
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
    
    
}
