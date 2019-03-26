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
    private Map<Object,Object> calendarMap;
    private Long databaseEventId;

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
        calendarMap = new HashMap<>();
            System.out.println("LOOK HERE");
            for (Event event : staff.getEvents()) {
                System.out.println(event.getEventId());
                System.out.println(event.getDescription());
                System.out.println(event.getStartDate());
                ScheduleEvent existing = new DefaultScheduleEvent(event.getDescription(), event.getStartDate(), event.getEndDate());
                scheduleModel.addEvent(existing);
                calendarMap.put(existing,event); 
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

    public Long getDatabaseEventId() {
        return databaseEventId;
    }

    public void setDatabaseEventId(Long databaseEventId) {
        Event databaseEvent = (Event) calendarMap.get(selectedScheduleEvent);
       databaseEventId = databaseEvent.getEventId();
        this.databaseEventId = databaseEventId;
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
            calendarMap.put(newScheduleEvent,newEvent); 
        newScheduleEvent = new DefaultScheduleEvent();
    }
    
    public void updateEvent() throws StaffNotFoundException, EventNotFoundException 
    {
  
        scheduleModel.updateEvent(selectedScheduleEvent);
        System.out.println(databaseEventId);
        Event eventToUpdate = eventController.retrieveEventById(databaseEventId);
        eventToUpdate.setDescription(selectedScheduleEvent.getTitle());
        eventToUpdate.setStartDate(selectedScheduleEvent.getStartDate());
        eventToUpdate.setEndDate(selectedScheduleEvent.getEndDate());
        eventController.updateEvent(eventToUpdate);
        calendarMap.put(selectedScheduleEvent, eventToUpdate);
        selectedScheduleEvent = new DefaultScheduleEvent();  
    }
    
     public void deleteEvent() throws StaffNotFoundException, EventNotFoundException {
           
            Event eventToDelete = (Event) calendarMap.get(selectedScheduleEvent);
            eventController.deleteEvent(eventToDelete.getEventId());
            staff.getEvents().remove(eventToDelete);
            staffController.updateStaff(staff);
            calendarMap.remove(selectedScheduleEvent);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category deleted successfully", null));
       
        
    }
    
    
    
    public void onEventSelect(SelectEvent selectEvent) 
    {
        selectedScheduleEvent = (ScheduleEvent) selectEvent.getObject();
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
