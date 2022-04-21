/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Map;
import java.util.TreeMap;

public class DengueCase implements Cloneable {
    private String districtName;
    private Map<Integer, Integer> dengueCasePerYear;
    
    public DengueCase(String districtName, TreeMap<Integer, Integer> dengueCasePerYear) {
        this.districtName = districtName;
        this.dengueCasePerYear = dengueCasePerYear;
    }
    
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    
    public String getDistrictName() {
        return districtName;
    }
    
    public void setDengueCasePerYear(Map<Integer, Integer> dengueCasePerYear) {
        this.dengueCasePerYear = dengueCasePerYear;
    }
    
    public Map<Integer, Integer> getDengueCasePerYear() {
        return dengueCasePerYear;
    }
    
    //An function from the Cloneable interface overwritten to clone to object,
    //so as to prevent side effect.
    @Override
    public Object clone() {
        try {
            return (DengueCase) super.clone();
        } catch (CloneNotSupportedException ex) {
            return new DengueCase(
                    this.districtName, (TreeMap<Integer, Integer>) this.dengueCasePerYear
            );
        }
    }
}
