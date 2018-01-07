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
public enum CurrencyCode {
    USD("USA"),
    POUNDS("Great Britain"),
    EURO("EU");
            
    String country;

    CurrencyCode(String country){
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    
    
    
}
