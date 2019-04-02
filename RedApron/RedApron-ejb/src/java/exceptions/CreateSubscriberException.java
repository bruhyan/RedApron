/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author MX
 */
public class CreateSubscriberException extends Exception {

    /**
     * Creates a new instance of <code>CreateSubscriberException</code> without
     * detail message.
     */
    public CreateSubscriberException() {
    }

    /**
     * Constructs an instance of <code>CreateSubscriberException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateSubscriberException(String msg) {
        super(msg);
    }
}
