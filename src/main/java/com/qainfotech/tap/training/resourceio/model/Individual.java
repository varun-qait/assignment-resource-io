package com.qainfotech.tap.training.resourceio.model;

import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class Individual {
    
    private final String name;
    private final Integer id;
    private final Boolean active;
    
    public Individual(Map<String, Object> individualMap){
     
        JSONObject individual = (JSONObject) individualMap.get("id");
        this.id = Integer.parseInt(individual.get("id").toString());
        //System.out.println(id);
        this.name = individual.get("name").toString(); 
        //System.out.println(name);
        this.active = Boolean.parseBoolean(individual.get("active").toString());
        //System.out.println(active);
        //throw new UnsupportedOperationException("Not implemented.");
    }
    
    /**
     * get the name of individual
     * 
     * @return individual name
     */
    public String getName(){
        return name;
    }
    
    /**
     * get the employee of of individual
     * @return id
     */
    public Integer getId(){
        return id;
    }
    
    /**
     * get the active status of individual
     * 
     * @return 
     */
    public Boolean isActive(){
        return active;
    }
}
