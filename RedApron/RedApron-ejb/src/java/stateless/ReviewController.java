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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

  @Override
    public List<Review> retrieveAllReviews()
    {
        Query query = em.createQuery("SELECT r FROM Review r");
        List<Review> reviewEntities = query.getResultList();
        for(Review rev:reviewEntities) {
            rev.getRecipe();
            rev.getSubscriber();
        }
        return reviewEntities;
    }  
    
    @Override
    public void updateReview(Review review) throws ReviewNotFoundException {
        
        Review reviewToUpdate = retrieveReviewById(review.getReviewId());
        if(reviewToUpdate != null){
            reviewToUpdate.setDate(review.getDate());
            reviewToUpdate.setRating(review.getRating());
            reviewToUpdate.setRecipe(review.getRecipe());
            reviewToUpdate.setSubscriber(review.getSubscriber());
            reviewToUpdate.setText(review.getText());
        }
        em.merge(reviewToUpdate);
    }
    
    @Override
    public void deleteReview(Long reviewId) throws ReviewNotFoundException {

        Review review = retrieveReviewById(reviewId);
        em.remove(review);

    }
}
