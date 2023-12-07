package dbRelationshipPractice.Users;

import dbRelationshipPractice.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
class UserController {

    @Autowired
    UserRepository userRepository;

    
    /** 
     * @return String
     */
    @GetMapping("/users/createFakeData")
    public String createDummyData() {
        User u1 = new User("John", "Doe", "jDoe@gmail.com", "Redfence10");
        User u2 = new User("Jane", "Doe", "Jane1243@yahoo.com", "Cyclones12");
        User u3 = new User("Some", "Pleb", "Pleb.test@fakeEmail,com", "WhiteFence45");
        User u4 = new User("Chad", "Champion", "ChadChamp@iastate.edu", "password12345");
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        return "Successfully created dummy data";
    }


    @GetMapping("/users")
    public List<User> allUsers() {
        List<User> results = userRepository.findAll();
        return results;
    }

    @GetMapping("/users/{id}")
    public Optional<User> greeting(@PathVariable  int id){
        Optional<User> user = userRepository.findById((id));
        if(user.isPresent()){
            return user;
        }
        else{
            throw new NotFoundException("User with ID " + id + " not found");
        }

    }
    @PostMapping("/users/addNewUser")
    public String postTest2(@RequestBody User user) {
        userRepository.save(user);
        return String.format("New user with id " + user.getId() + " successfully added");
    }

    @PutMapping("/users/{id}")
    Optional<User> updateUser(@PathVariable int id, @RequestBody User request){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.save(request);
            return userRepository.findById(id);
        }
        else{
            throw new NotFoundException("User with ID " + id + " not found");
        }
    }

    @DeleteMapping("/users/{id}")
    public String deleteExample(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return "Succesfully deleted user with id " + id;
        } else {
            throw new NotFoundException("User with ID" + id + "not found.");
        }
    }


}