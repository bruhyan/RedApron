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
public class SubscriptionPlanNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SubscriptionPlanNotFound</code> without
     * detail message.
     */
    public SubscriptionPlanNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SubscriptionPlanNotFound</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SubscriptionPlanNotFoundException(String msg) {
        super(msg);
    }
}
