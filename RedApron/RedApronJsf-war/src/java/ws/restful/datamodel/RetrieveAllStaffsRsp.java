/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Staff;
import java.util.List;

/**
 *
 * @author MX
 */
public class RetrieveAllStaffsRsp {
    
    private List<Staff> staffEntities;

    public RetrieveAllStaffsRsp() {
    }

    public RetrieveAllStaffsRsp(List<Staff> staffEntities) {
        this.staffEntities = staffEntities;
    }

    public List<Staff> getStaffEntities() {
        return staffEntities;
    }

    public void setStaffEntities(List<Staff> staffEntities) {
        this.staffEntities = staffEntities;
    }
    
    
}
