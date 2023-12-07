package coms309;

import coms309.models.Person;
import org.springframework.web.bind.annotation.*;

@RestController
class PersonController {
    
    /** 
     * @return String
     */
    @GetMapping("/")
    public String welcome() {
        return "Hello, I am in COMS 309";
    }
    @GetMapping("/{name}")
    public String greeting(@PathVariable  String name){
        return "Hello and welcome " + name;
    }
    @PostMapping("/postExample")
    public String postExample(@RequestParam(value = "username", defaultValue = "World") String message){
        return "Hello " + message + ". You have created a post request";
    }
    @PostMapping("/postTest2")
    public String postTest2(@RequestBody Person testData) {
        return String.format("Hello, %s! You sent a post request with a requestbody!\n Also, you are %d years old", testData.getMessage(), testData.getAge());
    }
    @DeleteMapping("/deleteExample")
    public void deleteExample(){
        System.out.println("Delete Successfull");
    }


}