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

public class MyRetailException extends RuntimeException{
    public MyRetailException(String message){
        super(message);
    }
    
    public MyRetailException(){
        super();
    }
}
