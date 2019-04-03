/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Review;
import java.util.List;

/**
 *
 * @author MX
 */
public class RetrieveAllReviewsRsp {
    private List <Review> reviewEntities;

    public RetrieveAllReviewsRsp() {
    }

    public RetrieveAllReviewsRsp(List<Review> reviewEntities) {
        this.reviewEntities = reviewEntities;
    }

    public List <Review> getReviewEntities() {
        return reviewEntities;
    }

    public void setReviewEntities(List <Review> reviewEntities) {
        this.reviewEntities = reviewEntities;
    }
    
    
}
