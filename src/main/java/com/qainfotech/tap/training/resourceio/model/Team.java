package com.qainfotech.tap.training.resourceio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.qainfotech.tap.training.resourceio.TeamsJsonReader;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class Team {
    
    private final String name;
    private final Integer id;
    private final List<Individual> members;
    TeamsJsonReader tjr = new TeamsJsonReader();
    List<Individual> activeTeam;
    List<Individual> inActiveTeam;
    
    public Team(Map<String, Object> teamMap){
        JSONObject team = (JSONObject) teamMap.get("id"); // In db.json there are 2 objects. Returns a team object.
        this.id = Integer.parseInt(team.get("id").toString());
        this.name = team.get("name").toString(); 
        this.members = new ArrayList<>();
        JSONArray memberJsonArray = (JSONArray) team.get("members"); // Getting the reference to 'members' field of a team object and as it is a list i am casting it to a jsonarray 
        List<Individual> ind = tjr.getListOfIndividuals(); // Creating a local copy of individual list.
        
        for(int i = 0; i < memberJsonArray.size(); i++) {
            //the reason why the below statement will not run is that in our json we don't always have id first attribute.
            //int employeeId = (Integer) memberJsonArray.get(i);
            Long value = (Long) memberJsonArray.get(i);
            int employeeId = (int) value.longValue();
            for(int j = 0; j < ind.size(); j++) {
                if(ind.get(j).getId() == employeeId) {
                    this.members.add(ind.get(j));
                }
            }
        }
        //throw new UnsupportedOperationException("Not implemented.");
    }
    
    /**
     * get team name
     * 
     * @return 
     */
    public String getName(){
        return name;
    }
    
    /**
     * get team id
     * 
     * @return 
     */
    public Integer getId(){
        return id;
    }
    
    /** 
     * get list of individuals that are members of this team
     * 
     * @return 
     */
    public List<Individual> getMembers(){
        return members;
    }
    
    /**
     * get a list of individuals that are members of this team and are also active
     * 
     * @return 
     */
    public List<Individual> getActiveMembers(){
        activeTeam = new ArrayList<>();
        for(int i = 0; i < members.size(); i++) {
            if(members.get(i).isActive()) {
                activeTeam.add(members.get(i));
            }
        }
        
        return activeTeam;     
        //throw new UnsupportedOperationException("Not implemented.");
    }
        
    /**
     * get a list of individuals that are members of this team but are inactive
     * 
     * @return 
     */
    public List<Individual> getInactiveMembers(){
        inActiveTeam = new ArrayList<>();
        for(int i = 0; i < members.size(); i++) {
            if(!(members.get(i).isActive())) {
                inActiveTeam.add(members.get(i));
            }
        }
        
        return inActiveTeam; 
        // throw new UnsupportedOperationException("Not implemented.");
    }
}
