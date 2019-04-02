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
public class RetrieveReviewRsp {
    private Review review;

    public RetrieveReviewRsp() {
    }

    public RetrieveReviewRsp(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
    
    
}
