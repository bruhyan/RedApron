/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Enquiry;
import java.util.List;

/**
 *
 * @author MX
 */
public class RetrieveAllEnquiriesRsp {
    private List <Enquiry> enquiryEntities;

    public RetrieveAllEnquiriesRsp() {
    }

    public RetrieveAllEnquiriesRsp(List<Enquiry> enquiryEntities) {
        this.enquiryEntities = enquiryEntities;
    }

    /**
     * @return the enquiryEntities
     */
    public List <Enquiry> getEnquiryEntities() {
        return enquiryEntities;
    }

    /**
     * @param enquiryEntities the enquiryEntities to set
     */
    public void setEnquiryEntities(List <Enquiry> enquiryEntities) {
        this.enquiryEntities = enquiryEntities;
    }
    
    
}
