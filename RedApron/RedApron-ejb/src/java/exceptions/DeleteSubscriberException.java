/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Bryan
 */
public class DeleteSubscriberException extends Exception {

    /**
     * Creates a new instance of <code>DeleteSubscriberException</code> without
     * detail message.
     */
    public DeleteSubscriberException() {
    }

    /**
     * Constructs an instance of <code>DeleteSubscriberException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteSubscriberException(String msg) {
        super(msg);
    }
}
