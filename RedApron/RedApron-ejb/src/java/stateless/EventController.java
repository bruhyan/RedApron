/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Event;
import exceptions.EventNotFoundException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author matthealoo
 */
@Stateless
@Local(EventControllerLocal.class)
public class EventController implements EventControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
  
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Event createNewEvent(Event event) {
        em.persist(event);
        em.flush();

        return event;
    }

    @Override
    public Event retrieveEventById(Long eventId) throws EventNotFoundException {
        Event event = em.find(Event.class, eventId);

        if (event != null) {
            return event;
        } else {
            throw new EventNotFoundException("Event ID " + eventId + " does not exist");
        }
    }
   
    @Override
    public void deleteEvent(Long eventId) throws EventNotFoundException {
        Event event = retrieveEventById(eventId);
        em.remove(event);
    }

    @Override
    public void updateEvent(Event event) throws EventNotFoundException {
        Event eventToUpdate = retrieveEventById(event.getEventId());
        eventToUpdate.setDescription(event.getDescription());
        eventToUpdate.setStartDate(event.getStartDate());
        eventToUpdate.setEndDate(event.getEndDate());
        em.merge(eventToUpdate);

    }

}
