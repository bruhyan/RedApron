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
public class RecipeNotFoundException extends Exception {


    public RecipeNotFoundException() {
    }

    public RecipeNotFoundException(String msg) {
        super(msg);
    }
}
