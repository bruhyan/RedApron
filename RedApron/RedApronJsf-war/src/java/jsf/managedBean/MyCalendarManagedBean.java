package jsf.managedBean;

import entity.Event;
import entity.Staff;
import exceptions.EventNotFoundException;
import exceptions.StaffNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import stateless.EventControllerLocal;
import stateless.StaffControllerLocal;



@Named(value = "myCalendarManagedBean")
@ViewScoped

public class MyCalendarManagedBean implements Serializable
{

    @EJB
    private StaffControllerLocal staffController;

    @EJB
    private EventControllerLocal eventController;
    
    private ScheduleModel scheduleModel; 
    private ScheduleEvent selectedScheduleEvent; //selected event
    private ScheduleEvent newScheduleEvent; //new event

    private Staff staff;

    public MyCalendarManagedBean() 
    {
        scheduleModel = new DefaultScheduleModel();
        selectedScheduleEvent = new DefaultScheduleEvent();
        newScheduleEvent = new DefaultScheduleEvent();

    }

    @PostConstruct
    public void postConstruct()
    {
         Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        staff = (Staff) sessionMap.get("currentStaff"); //Need to pass in the current staff that logged in
            System.out.println("LOOK HERE");
            for (Event event : staff.getEvents()) {
                System.out.println(event.getEventId());
                System.out.println(event.getDescription());
                System.out.println(event.getStartDate());
                ScheduleEvent existing = new DefaultScheduleEvent(event.getDescription(), event.getStartDate(), event.getEndDate(), event.getEventId());
                scheduleModel.addEvent(existing);
            }
    }

    public ScheduleEvent getNewScheduleEvent() {
        return newScheduleEvent;
    }

    public void setNewScheduleEvent(ScheduleEvent newScheduleEvent) {
        this.newScheduleEvent = newScheduleEvent;
    }
 

    public ScheduleModel getScheduleModel() {
        return scheduleModel;
    }

    public void setScheduleModel(ScheduleModel scheduleModel) {
        this.scheduleModel = scheduleModel;
    }

    public ScheduleEvent getSelectedScheduleEvent() {
        return selectedScheduleEvent;
    }

    public void setSelectedScheduleEvent(ScheduleEvent selectedScheduleEvent) {
        this.selectedScheduleEvent = selectedScheduleEvent;
    }

    public void addNewEvent() throws StaffNotFoundException 
    {
            scheduleModel.addEvent(newScheduleEvent);
            Event newEvent = new Event(newScheduleEvent.getTitle(),newScheduleEvent.getStartDate(),newScheduleEvent.getEndDate());
           newEvent = eventController.createNewEvent(newEvent);
            staff.addEvent(newEvent);
            staffController.updateStaff(staff);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event created successfully", null));
        newScheduleEvent = new DefaultScheduleEvent();
    }
    
    public void updateEvent() throws StaffNotFoundException, EventNotFoundException 
    {
  
        System.err.println("********** HERE 1");
        
        scheduleModel.updateEvent(selectedScheduleEvent);
        
        Long eventIdToUpdate = (Long) selectedScheduleEvent.getData();
        Event eventToUpdate = eventController.retrieveEventById(eventIdToUpdate);
        eventToUpdate.setDescription(selectedScheduleEvent.getTitle());
        eventToUpdate.setStartDate(selectedScheduleEvent.getStartDate());
        eventToUpdate.setEndDate(selectedScheduleEvent.getEndDate());
        
        System.err.println("********** title" + eventToUpdate.getDescription());
        
        eventController.updateEvent(eventToUpdate);
        selectedScheduleEvent = new DefaultScheduleEvent();  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event updated successfully", null));

    }
    
     public void deleteEvent() throws StaffNotFoundException, EventNotFoundException {
            Long eventIdToDelete = (Long) selectedScheduleEvent.getData();
            Event eventToDelete = eventController.retrieveEventById(eventIdToDelete);
            staff.getEvents().remove(eventToDelete);
            staffController.updateStaff(staff);

            eventController.deleteEvent(eventIdToDelete);
            scheduleModel.deleteEvent(selectedScheduleEvent);
            selectedScheduleEvent = new DefaultScheduleEvent();  
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Event deleted successfully", null));

    }

    public void onEventSelect(SelectEvent selectEvent) 
    {
        selectedScheduleEvent = (ScheduleEvent) selectEvent.getObject();
        System.err.println("********* select: " + selectedScheduleEvent.getData().toString());
    }
    

    public void onEventMove(ScheduleEntryMoveEvent scheduleEvent) 
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + scheduleEvent.getDayDelta() + ", Hour delta:" + scheduleEvent.getMinuteDelta());
         
        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent scheduleEvent) 
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + scheduleEvent.getDayDelta() + ", Minute delta:" + scheduleEvent.getMinuteDelta());
         
        addMessage(message);
    }

    private void addMessage(FacesMessage message) 
    {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private Calendar today() 
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }

    
}
