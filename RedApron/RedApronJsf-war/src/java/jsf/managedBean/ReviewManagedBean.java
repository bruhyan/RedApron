/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Review;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import stateless.ReviewControllerLocal;

/**
 *
 * @author Bryan
 */
@Named(value = "reviewManagedBean")
@Dependent
public class ReviewManagedBean {

    @EJB(name = "ReviewControllerLocal")
    private ReviewControllerLocal reviewControllerLocal;

    private List<Review> reviewList;
    
    public ReviewManagedBean() {
    }
    
    @PostConstruct()
    public void postConstruct() {
        setReviewList(reviewControllerLocal.retrieveAllReviews());
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
