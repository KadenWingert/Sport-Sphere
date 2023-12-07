package main.java.coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre, Arie Kraayenbrink
 */

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Component
@PropertySource("classpath:application.properties")
@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, Person> peopleList = new  HashMap<>();

    @Value("${server.port}")
    private String serverPort;

    
    /** 
     * @return HashMap<String, Person>
     */
    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public @ResponseBody HashMap<String,Person> getAllPersons() {
        return peopleList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public @ResponseBody String createPerson(@RequestBody Person person) {
        System.out.println(person);
        
        String resultMsg = "Person already exists, ignoring command...";

        // check for existing match before creating new person
        if (!personExists(person))
        {
            peopleList.put(person.getFirstName(), person);
            resultMsg = "New person "+ person.getFirstName() + " Saved";
        }

        return resultMsg;
    }

    private boolean personExists(Person person)
    {
        boolean exists = peopleList.containsKey(person.getFirstName());
        return exists;
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{firstName}")
    public @ResponseBody String getPerson(@PathVariable String firstName) {
        Person p = peopleList.get(firstName);

        String msg = String.format("<h3>Current records for %s are as follows:</h3>\n<i>%s</i>", firstName, p);
        return msg;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{firstName}")
    public @ResponseBody Person updatePerson(@PathVariable String firstName, @RequestBody Person p) {
        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/people/{firstName}")
    public @ResponseBody HashMap<String, Person> deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return peopleList;
    }

    // TODO: make this return a html formated page
    @GetMapping("/search")
    public @ResponseBody HashMap<String,Person> searchAllPersons() {

        HashMap<String, Person> searchList = new  HashMap<>();

        try {
            peopleList.forEach((key, value) -> {
    
                Person newPerson = new Person(String.format("<a href=\'http://localhost:%s/people/%s\'>%s</a>", 
                this.serverPort, value.getFirstName(), value.getFirstName()), value.getLastName(), value.getAddress(), value.getTelephone());
    
                searchList.put(key, newPerson);
            });            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }

        return searchList;
    }
}

