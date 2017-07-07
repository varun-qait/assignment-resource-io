package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsYamlReader {

    /**
     * get a list of individual objects from db yaml file
     *
     * @return
     */
    Yaml yaml;
    InputStream is = null;
    List<Individual> yamlInd;
    List<Team> yamlTeam;
    List<Individual> inactive; 
    List<Individual> active;

    void loadParser() throws FileNotFoundException {
        String filePath = "C:\\Users\\varunaggarwal\\Documents\\NetBeansProjects\\assignment-resource-io-master\\src\\main\\resources\\db.yaml";
        File file = new File(filePath);
        is = new FileInputStream(file);
        yaml = new Yaml();
    }
    
    public List<Individual> getListOfIndividuals() throws FileNotFoundException {
        loadParser();

        // SnakeYAML allows you to construct a Java object of any type.
        Map<String, Object> result = (Map<String, Object>) yaml.load(is); // Map of both individuals and teams present in YAML file.
        
        //System.out.println("Printing keysets of full Map - " + result.keySet()); // Printing keysets [individuals, teams]
        
        ArrayList temp = (ArrayList) result.get("individuals"); 
        Map<String, Object> mapConstructor;
        yamlInd = new ArrayList<>();
        for(int i = 0; i < temp.size(); i++) {
            mapConstructor = (Map<String, Object>) temp.get(i); // Creating the map of individual
            
            //System.out.println("Printing keyset of each map in arrayList - " + mapConstructor.keySet()); // Printing keyset of each map in arrayList - [id, name, active]

            Individual individual = new Individual(mapConstructor);
            yamlInd.add(individual);
        }
        return yamlInd;
    }

    /**
     * get individual object by id
     *
     * @param id individual id
     * @return
     * @throws
     * com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
     * @throws java.io.FileNotFoundException
     */
    
    public Individual getIndividualById(Integer id) throws ObjectNotFoundException, FileNotFoundException {
        
        yamlInd = getListOfIndividuals();
        //System.out.println("size of ind " + ind.size());
        for(int i = 0; i < yamlInd.size(); i++) {
            if(yamlInd.get(i).getId() == id.intValue()) {
                return yamlInd.get(i);
            }
        }
        throw new ObjectNotFoundException("Individual", "id", id.toString());
    }

    /**
     * get individual object by name
     *
     * @param name
     * @return
     * @throws
     * com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
     * @throws java.io.FileNotFoundException
     */
    public Individual getIndividualByName(String name) throws ObjectNotFoundException, FileNotFoundException {
        yamlInd = getListOfIndividuals();
        //System.out.println("size of ind " + ind.size());
        for(int i = 0; i < yamlInd.size(); i++) {
            if(yamlInd.get(i).getName().equals(name)) {
                return yamlInd.get(i);
            }
        }
        throw new ObjectNotFoundException("Individual", "Name", name);
    }

    /**
     * get a list of individual objects who are not active
     *
     * @return List of inactive individuals object
     * @throws java.io.FileNotFoundException
     */
    public List<Individual> getListOfInactiveIndividuals() throws FileNotFoundException {
        inactive = new ArrayList<>();
        yamlInd = getListOfIndividuals();
        for(int i = 0; i < yamlInd.size(); i++) {
            if(!(yamlInd.get(i).isActive())) {
                inactive.add(yamlInd.get(i));
            }
        }
        return inactive;
    }

    /**
     * get a list of individual objects who are active
     *
     * @return List of active individuals object
     * @throws java.io.FileNotFoundException
     */
    public List<Individual> getListOfActiveIndividuals() throws FileNotFoundException {
        active = new ArrayList<>();
        yamlInd = getListOfIndividuals();
        for(int i = 0; i < yamlInd.size(); i++) {
            if(yamlInd.get(i).isActive()) {
                active.add(yamlInd.get(i));
            }
        }
        return active;
        // throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * get a list of team objects from db yaml
     *
     * @return
     */
    public List<Team> getListOfTeams() throws FileNotFoundException {
        loadParser();
        // SnakeYAML allows you to construct a Java object of any type.
        Map<String, Object> result = (Map<String, Object>) yaml.load(is); // Map of both individuals and teams present in YAML file.
        ArrayList temp = (ArrayList) result.get("teams"); 
        Map<String, Object> mapConstructor;
        yamlTeam = new ArrayList<>();
        for(int i = 0; i < temp.size(); i++) {
            mapConstructor = (Map<String, Object>) temp.get(i); // Creating the map of individual
            System.out.println("Keyset ************************" + mapConstructor.keySet());
            //System.out.println("**********************************" + mapConstructor.toString());
            Team teamMember = new Team(mapConstructor);
            yamlTeam.add(teamMember);
        }
        
        return yamlTeam;
        // throw new UnsupportedOperationException("Not implemented.");
    }

    public static void main(String[] args) throws FileNotFoundException {
        TeamsYamlReader tyr = new TeamsYamlReader();
        //tyr.getListOfIndividuals();
        System.out.println(tyr.getListOfTeams().toString());
    }
    
}
