package coms309.SavedGamePost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import coms309.GamePost.GamePost;
import coms309.GamePost.GamePostRepository;
import coms309.Users.UserRepository;
import coms309.Users.Users;
import feign.QueryMap;

@Controller
public class SavedGamePostController {
    @Autowired
    private SavedGamePostRepository savedGamePostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GamePostRepository gamePostRepository;

    @QueryMapping
    public List<SavedGamePost> getAllSavedGamePosts() {
        return savedGamePostRepository.findAll();
    }

    @QueryMapping
    public SavedGamePost getSavedGamePostById(@Argument int id) {
        return savedGamePostRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public SavedGamePost getSavedGamePostByUserId(@Argument int userId) {
        return savedGamePostRepository.findByUserId(userId);
    }

    @MutationMapping
    private SavedGamePost createSavedGamePost(@Argument int userID, @Argument int gamePostId) {
        GamePost game = gamePostRepository.findById(gamePostId);

        SavedGamePost savedGamePost =
                new SavedGamePost(userRepository.findById(userID), gamePostId);
        savedGamePostRepository.save(savedGamePost);

        return savedGamePost;
    }

    @MutationMapping
    public SavedGamePost updateSavedGamePost(@Argument int savedPostId, @Argument int userID,
            @Argument int gamePostId) {
        SavedGamePost gamePost = savedGamePostRepository.findById(savedPostId).orElse(null);
        if (gamePost == null) {
            gamePost = new SavedGamePost();
        }
        gamePost.setUser(userRepository.findById(userID));
        gamePost.setGamePost(gamePostId);

        return savedGamePostRepository.save(gamePost);
    }

    @MutationMapping
    public String deleteSavedGamePost(@Argument int id) {
        savedGamePostRepository.deleteById(id);
        return "Delete complete";
    }

}
