/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Staff;

/**
 *
 * @author MX
 */
public class RetrieveStaffRsp {
    
    private Staff staff;

    public RetrieveStaffRsp() {
    }

    public RetrieveStaffRsp(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    
}
