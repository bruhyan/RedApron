/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Event;
import exceptions.EventNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
@Local
public interface EventControllerLocal {

    public Event createNewEvent(Event event);

    public Event retrieveEventById(Long eventId) throws EventNotFoundException;

    public void deleteEvent(Long eventId) throws EventNotFoundException;

    public void updateEvent(Event event) throws EventNotFoundException;
    
}
