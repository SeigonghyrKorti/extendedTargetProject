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
public class EntityNotFoundException extends MyRetailException{
    public EntityNotFoundException(String message){
        super(message);
    }
    
    public EntityNotFoundException(){
        super();
    }
}
