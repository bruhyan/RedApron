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
public class SubscriberNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SubscriberNotFoundException</code>
     * without detail message.
     */
    public SubscriberNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SubscriberNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public SubscriberNotFoundException(String msg) {
        super(msg);
    }
}
