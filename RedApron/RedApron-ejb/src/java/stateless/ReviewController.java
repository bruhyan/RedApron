/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Review;
import exceptions.ReviewNotFoundException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matthealoo
 */
@Stateless
@Local(ReviewControllerLocal.class)
public class ReviewController implements ReviewControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    public Review createNewReview(Review review) {
        em.persist(review);
        em.flush();
        
        return review;
    }
    
    @Override
    public Review retrieveReviewById(Long reviewId) throws ReviewNotFoundException {
        Review review = em.find(Review.class, reviewId);

        if (review != null) {
            return review;
        } else {
            throw new ReviewNotFoundException("Answer ID " + reviewId + " does not exist");
        }
    }

    
}
