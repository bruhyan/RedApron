/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Review;

/**
 *
 * @author MX
 */
public class CreateReviewReq {
    private Review review;

    public CreateReviewReq() {
    }

    public CreateReviewReq(Review review) {
        this.review = review;
    }

    /**
     * @return the review
     */
    public Review getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(Review review) {
        this.review = review;
    }
    
    
}
