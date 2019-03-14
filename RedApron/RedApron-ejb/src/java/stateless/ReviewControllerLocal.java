/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Review;
import exceptions.ReviewNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
public interface ReviewControllerLocal {
    
     public Review createNewReview(Review review);
    public Review retrieveReviewById(Long reviewId) throws ReviewNotFoundException;

    
}
