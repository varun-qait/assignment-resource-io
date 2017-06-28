package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsJsonReader{
    
    /**
     * get a list of individual objects from db json file
     * 
     * @return 
     */
    
    List<Individual> inactive;
    List<Individual> active;

    JSONObject obj;
    void jsonParser() throws IOException {
        try {
            JSONParser jp = new JSONParser();
            FileReader reader = new FileReader(new File("C:\\Users\\varunaggarwal\\Documents\\NetBeansProjects\\assignment-resource-io-master\\src\\main\\resources\\db.json"));
            obj = (JSONObject) jp.parse(reader);
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(TeamsJsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    List<Individual> ind;
    List<Team> t;
    public List<Individual> getListOfIndividuals(){
        ind = new ArrayList<>();
        try {
            jsonParser();
        } catch(Exception e) {
        
        }
        JSONArray arr = (JSONArray) obj.get("individuals"); // here you get an array of individuals
        for(int i = 0; i < arr.size(); i++) {
            JSONObject iobj = (JSONObject) arr.get(i); // each entry in jsonArray is itself considered as a jsonobject
            //System.out.println(iobj.toString());
            Map<String, Object> map = new HashMap<>();
            //System.out.println(iobj.toString()); // {"name":"John Doe","active":true,"id":1201}
            //map.put(iobj.toString(), iobj); // Here i am puting the toString() representation of whole object as key -- WRONG APPROACH
            //map.put(String.valueOf(iobj.get("id")), iobj); // This is wrong as here i am using value of id as key
            map.put("id", iobj);
            Individual individual = new Individual(map);
            //System.out.println(i + " is added\n\n");
            
            
            ind.add(individual);
        }
        return ind;
    }
    
                                                    // Debug Code
                                                    void printArrayListSize() {
                                                        System.out.println(ind.size());

                                                    }
    
    
    /**
     * get individual object by id
     * 
     * @param id individual id
     * @return 
     * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException 
     */
    public Individual getIndividualById(Integer id) throws ObjectNotFoundException{
        ind = getListOfIndividuals();
        //System.out.println("size of ind " + ind.size());
        for(int i = 0; i < ind.size(); i++) {
            if(ind.get(i).getId() == id.intValue()) {
                return ind.get(i);
            }
        }
        throw new ObjectNotFoundException("Object not found", "id", id.toString());
    }
    
    /**
     * get individual object by name
     * 
     * @param name
     * @return 
     * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException 
     */
    public Individual getIndividualByName(String name) throws ObjectNotFoundException{
        ind = getListOfIndividuals();
        
        for(int i = 0; i < ind.size(); i++) {
            if((ind.get(i).getName()).equals(name)) {
                return ind.get(i);
            }
        }
        throw new ObjectNotFoundException("Individual not found", "name", name);
        //throw new UnsupportedOperationException("Not implemented.");
    }
    
    
    /**
     * get a list of individual objects who are not active
     * 
     * @return List of inactive individuals object
     */
    public List<Individual> getListOfInactiveIndividuals(){
        inactive = new ArrayList<>();
        ind = getListOfIndividuals();
        for(int i = 0; i < ind.size(); i++) {
            if(!(ind.get(i).isActive())) {
                inactive.add(ind.get(i));
            }
        }
        return inactive;
        //throw new UnsupportedOperationException("Not implemented.");
    }
    
    /**
     * get a list of individual objects who are active
     * 
     * @return List of active individuals object
     */
    public List<Individual> getListOfActiveIndividuals(){
        active = new ArrayList<>();
        ind = getListOfIndividuals();
        for(int i = 0; i < ind.size(); i++) {
            if(ind.get(i).isActive()) {
                active.add(ind.get(i));
            }
        }
        return active;
        //throw new UnsupportedOperationException("Not implemented.");
    }
    
    /**
     * get a list of team objects from db json
     * 
     * @return 
     */
    public List<Team> getListOfTeams(){
        try {
            jsonParser();
        } catch (IOException ex) {
            Logger.getLogger(TeamsJsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        t = new ArrayList<>();
        JSONArray team = (JSONArray) obj.get("teams");
        for(int i = 0; i < team.size(); i++) {
            JSONObject tobj = (JSONObject) team.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", tobj);
            Team teamObj = new Team(map);
            t.add(teamObj);
        }
        return t;
        //throw new UnsupportedOperationException("Not implemented.");
    }
    
    
//    public static void main(String[] args) {
//        TeamsJsonReader tjr = new TeamsJsonReader();
//        //tjr.getListOfIndividuals();
//        //tjr.printArrayListSize();
//        tjr.getListOfTeams();
//    }
    
}
