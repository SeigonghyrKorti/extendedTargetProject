/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

/**
 *
 * @author sei
 */
public class Validate {
    
    public static void validateNotNull(Object arg, String tag) throws NullPointerException{
        if (arg == null){
            throw new NullPointerException(String.format("%s cannot be null", tag));
        }
    }
    
    public static void validateNotEmpty(String arg, String tag) throws Exception{
        if(arg.isEmpty()){
            throw new Exception(String.format("%s cannot be empty", tag));
        }
    }
}
