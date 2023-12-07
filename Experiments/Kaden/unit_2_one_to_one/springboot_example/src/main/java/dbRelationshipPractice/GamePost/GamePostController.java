package dbRelationshipPractice.GamePost;

import dbRelationshipPractice.Exceptions.NotFoundException;
import dbRelationshipPractice.Users.User;
import dbRelationshipPractice.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
class GamePostController {

    @Autowired
    GamePostRepository gamePostRepository;
    @Autowired
    UserRepository userRepository;

    
    /** 
     * @return String
     */
    @GetMapping("/gamePost/createFakeData")
    public String createDummyData() {
        GamePost gp1 = new GamePost("Basketball", 1, 10, 1, "1/12/2023", "29/09/1988", false);
        GamePost gp2 = new GamePost("Tennis", 4, 12, 4, "29/09/1988", "29/09/1988", true);
        GamePost gp3 = new GamePost("Rock Climbing", 4, 10, 8, "31/3/2023","29/09/1988", false);
        GamePost gp4 = new GamePost("Volleyball", 2, 6,3, "12/4/1988","29/09/1988",false);
        gamePostRepository.save(gp1);
        gamePostRepository.save(gp2);
        gamePostRepository.save(gp3);
        gamePostRepository.save(gp4);
        return "Successfully created dummy data";
    }


    @GetMapping("/gamePost")
    public List<GamePost> allGamePosts() {
        List<GamePost> results = gamePostRepository.findAll();
        return results;
    }

    @GetMapping("/gamePosts/{id}")
    public Optional<GamePost> greeting(@PathVariable  int id){
        Optional<GamePost> gamePost = gamePostRepository.findById((id));
        if(gamePost.isPresent()){
            return gamePost;
        }
        else{
            throw new NotFoundException("GamePost with ID " + id + " not found");
        }

    }
    @PostMapping("/gamePost/addNewGamePost")
    public String postTest2(@RequestBody GamePost gamePost) {
        gamePostRepository.save(gamePost);
        return String.format("New Game Post with id " + gamePost.getId() + " successfully added");
    }

    @PutMapping("/gamePost/{id}")
    Optional<GamePost> updateGamePost(@PathVariable int id, @RequestBody GamePost request){
        Optional<GamePost> gamePost = gamePostRepository.findById(id);
        if(gamePost.isPresent()) {
            gamePostRepository.save(request);
            return gamePostRepository.findById(id);
        }
        else{
            throw new NotFoundException("Game Post with ID " + id + " not found");
        }
    }
//    @PutMapping("/gamePost/{gamePostID}/{userID}")
//    Optional<GamePost> updateUsersPlaying(@PathVariable int gamePostID,@PathVariable int userID){
//        Optional<User> user = userRepository.findById(userID);
//        Optional<GamePost> gamePost = gamePostRepository.findById(gamePostID);
//        if(user.isPresent() && gamePost.isPresent()){
//            gamePostRepository.save(gamePost);
//                    return gamePost;
//        }
//        else{
//            throw new NotFoundException("User or Game Post id not found");
//        }
//    }

    @DeleteMapping("/gamePost/{id}")
    public String deleteExample(@PathVariable int id) {
        Optional<GamePost> gamePost = gamePostRepository.findById(id);
        if (gamePost.isPresent()) {
            gamePostRepository.deleteById(id);
            return "Succesfully deleted Game Post with id " + id;
        } else {
            throw new NotFoundException("Game Post with ID" + id + "not found.");
        }
    }


}