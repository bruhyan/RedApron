/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Review;
import exceptions.ReviewNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
public interface ReviewControllerLocal {
    
    public Review createNewReview(Review review);
    
    public Review retrieveReviewById(Long reviewId) throws ReviewNotFoundException;
    
    public List<Review> retrieveAllReviews();

    public void updateReview(Review review) throws ReviewNotFoundException;

    public void deleteReview(Long reviewId) throws ReviewNotFoundException;

    public List<Review> retrieveReviewsByRecipeId(Long subscriberId);

}
