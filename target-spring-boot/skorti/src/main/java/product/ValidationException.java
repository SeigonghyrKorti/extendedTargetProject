/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

//import lombok.Data;

/**
 *
 * @author sei
 */
//@Data
public class ValidationException extends MyRetailException{
    private String tag;
    private String message;
    ValidationException(String tag, String message){
        this.message = message;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
