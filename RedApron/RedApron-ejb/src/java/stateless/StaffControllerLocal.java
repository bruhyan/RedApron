/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Answer;
import entity.Staff;
import exceptions.EnquiryNotFoundException;
import exceptions.InvalidLoginCredentialException;
import exceptions.StaffNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
@Local
public interface StaffControllerLocal {

    public List<Answer> retrieveStaffAnswers(Long staffId) throws StaffNotFoundException;

    public void deleteStaff(Long staffId) throws StaffNotFoundException;

    public void answerEnquiry(String answerContent, Long enquiryId, Long staffId) throws StaffNotFoundException, EnquiryNotFoundException;

    public Staff retrieveStaffById(long id) throws StaffNotFoundException;

    public List<Staff> retrieveAllStaffs();

    public Staff retrieveStaffByEmail(String email) throws StaffNotFoundException;

    public Staff staffLogin(String email, String password) throws InvalidLoginCredentialException;

    public Staff createNewStaff(Staff staff);

    public void updateStaff(Staff staff) throws StaffNotFoundException;
    
}
