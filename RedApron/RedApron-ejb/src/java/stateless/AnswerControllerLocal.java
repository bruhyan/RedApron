/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Answer;
import exceptions.AnswerNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
public interface AnswerControllerLocal {
        public Answer createNewAnswer(Answer answer);
        public Answer retrieveAnswerById(Long answerId) throws AnswerNotFoundException;
    
}
