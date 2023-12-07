package coms309.mockito;

import coms309.Auth.Auth;
import coms309.Enums.Permission;
import coms309.GamePost.GamePost;
import coms309.GamePost.GamePostController;
import coms309.GamePost.GamePostPublisher;
import coms309.GamePost.GamePostRepository;
import coms309.Location.Location;
import coms309.Sport.Sport;
import coms309.Sport.SportRepository;
import coms309.Users.UserRepository;
import coms309.Users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import static coms309.Auth.Auth.isAdmin;
import static coms309.Enums.Permission.permission.ADMIN;
import static coms309.Enums.Permission.permission.USER;
import static graphql.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GamePostControllerTest {


    @InjectMocks
    GamePostController gamePostController;

    @Mock
    GamePostRepository gamePostRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    SportRepository sportRepository;
    @Mock
    GamePostPublisher gamePostPublisher;

    private GamePost gp1, gp2, gp3, gp4;
    private Users u1, u2;
    private Sport s1, s2, s3, s4;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        s1 = new Sport("Basketball");
        s2 = new Sport("Pickleball");
        s3 = new Sport("Football");
        s4 = new Sport("Spikeball");
        sportRepository.save(s1);
        sportRepository.save(s2);
        sportRepository.save(s3);
        sportRepository.save(s4);

        u1 = new Users("Bill", "Smith", "Bsmith@example.com", "smith3920");
        u2 = new Users("John", "1234", "john@gmail.com", "Mar1o&Lu1g1");
        userRepository.save(u1);
        userRepository.save(u2);

        gp1 = new GamePost(s1, userRepository.findById(u1.getId()), 24, 20, "1/12/2023",
                "29/09/1988", false);
        gp2 = new GamePost(s2, userRepository.findById(u1.getId()), 16, 2, "1/2/2023", "1/02/2023",
                false);
        gp3 = new GamePost(s3, userRepository.findById(u2.getId()), 10, 8, "10/08/2023",
                "10/25/2023", false);
        gp4 = new GamePost(s4, userRepository.findById(u2.getId()), 6, 3, "11/1/2023", "11/09/2023",
                false);
        gamePostRepository.save(gp1);
        gamePostRepository.save(gp2);
        gamePostRepository.save(gp3);
        gamePostRepository.save(gp4);
    }

    @Test
    void getAllGamePosts() {
        List<GamePost> list = new ArrayList<>();
        list.add(gp1);
        list.add(gp2);
        list.add(gp3);
        list.add(gp4);
        when(gamePostRepository.findAll()).thenReturn(list);

        List<GamePost> gamePostList = gamePostController.getAllGamePosts();
        assertEquals(4, gamePostList.size());
        assertEquals(gp1.getId(), gamePostList.get(1).getId());
        verify(gamePostRepository, times(1)).findAll();

    }

    @Test
    void getGamePostById() {
        when(gamePostRepository.findById(0)).thenReturn(gp1);
        GamePost gamePost = gamePostController.getGamePostById(0);

        assertEquals(gp1.getId(), gamePost.getId());
        assertEquals(gp1.getSport(), gamePost.getSport());
        assertEquals(gp1.getMax_players(), gamePost.getMax_players());
        assertEquals(gp1.getMin_players(), gamePost.getMin_players());
        assertEquals(gp1.getPlayersSignedUp(), gamePost.getPlayersSignedUp());
        assertEquals(gp1.getCreated_by(), gamePost.getCreated_by());
        assertEquals(gp1.getCreated_on(), gamePost.getCreated_on());
        assertEquals(gp1.getPlaying_on(), gamePost.getPlaying_on());
        assertEquals(gp1.getIs_deleted(), gamePost.getIs_deleted());
        assertEquals(gp1.getComment(), gamePost.getComment());
        assertEquals(gp1.getLocation(), gamePost.getLocation());
    }

    @Test
    void getUsersPlaying() {
        assertEquals(1, gp1.getPlayersSignedUp().size());
        Set<Users> verifyCreatorIsSignedUp = new HashSet<>();
        verifyCreatorIsSignedUp.add(u2);
        assertTrue(gp1.getPlayersSignedUp().contains(userRepository.findById(1)));

        int gamePostID = 1;
        Set<Users> players = new HashSet<>();
        players.add(u1);
        players.add(u2);
        players.add(gp1.getCreated_by());
        when(gamePostRepository.findById(gamePostID)).thenReturn(gp1);
        gp1.setPlayersSignedUp(players);

        Set<Users> result = gamePostController.getUsersPlaying(gamePostID);
        assertEquals(players, result);
        assertEquals(3, result.size());
    }

    @Test
    void createGamePost() {
        int sport_id = 1;
        int created_by = 1;
        int max_players = 10;
        int min_players = 5;
        String playing_on = "2/1/2023";
        String created_on = "1/1/2023";
        boolean is_deleted = false;
        String address = "123 Main St";
        String GPS = "12.345,67.890";

        Sport sport = new Sport("Tennis");
        sport.setId(sport_id);
        when(sportRepository.findById(sport_id)).thenReturn(sport);

        Users creator = new Users("New", "User", "new@example.com", "password");
        creator.setId(created_by);
        when(userRepository.findById(created_by)).thenReturn(creator);

        GamePost gamePost = new GamePost(sport, creator, max_players, min_players, playing_on,
                created_on, is_deleted);
        Location location = new Location(address, GPS);
        gamePost.setLocation(location);

        when(gamePostRepository.save(any(GamePost.class))).thenReturn(gamePost);

        GamePost result = gamePostController.createGamePost(sport_id, created_by, max_players,
                min_players, playing_on, created_on, is_deleted, address, GPS, creator.getEmail(),
                creator.getPassword());

        assertEquals(sport_id, result.getSport().getId());
        assertEquals(created_by, result.getCreated_by().getId());
        assertEquals(max_players, result.getMax_players());
        assertEquals(min_players, result.getMin_players());
        assertEquals(playing_on, result.getPlaying_on());
        assertEquals(created_on, result.getCreated_on());
        assertEquals(is_deleted, result.getIs_deleted());
        assertEquals(creator.getEmail(), userRepository.findById(created_by).getEmail());
        assertEquals(creator.getPassword(), userRepository.findById(created_by).getPassword());
        assertEquals(address, result.getLocation().getAddress());
        assertEquals(GPS, result.getLocation().getGps());
    }

    // @Test
    // void updateGamePost() {
    //
    // int max_players = 15;
    // int min_players = 8;
    // String playing_on = "2/15/2023";
    // String created_on = "2/1/2023";
    // Boolean is_deleted = true;
    // String address = "456 Elm St";
    // String GPS = "23.456,45.678";
    // String email = "user@example.com";
    // String password = "password123";
    //
    // Users creator = new Users("User", "Test", email, password);
    // Sport sport = new Sport("Baseball");
    // gp1.setCreated_by(creator);
    // when(userRepository.findById(creator.getId())).thenReturn(creator);
    // when(gamePostRepository.findById(gp1.getId())).thenReturn(gp1);
    //
    // gamePostController.updateGamePost(gp1.getId(), sport.getId(), max_players,
    // min_players, playing_on, created_on, is_deleted, address, GPS, email, password);
    //
    // assertEquals(sport.getId(), gp1.getSport().getId());
    // assertEquals(max_players, gp1.getMax_players());
    // assertEquals(min_players, gp1.getMin_players());
    // assertEquals(playing_on, gp1.getPlaying_on());
    // assertEquals(created_on, gp1.getCreated_on());
    // assertEquals(is_deleted, gp1.getIs_deleted());
    // assertEquals(address, gp1.getLocation().getAddress());
    // assertEquals(GPS, gp1.getLocation().getGps());
    // }
    //

    @Test
    void addNewUserPlaying() {
        int gamePostID = 1;
        String email = "user@example.com";
        String password = "password123";


        when(userRepository.findById(u2.getId())).thenReturn(u2);
        when(gamePostRepository.findById(gamePostID)).thenReturn(gp1);

        Set<Users> playersBeforeAdd = gp1.getPlayersSignedUp();
        int playersCountBeforeAdd = playersBeforeAdd.size();

        Set<Users> playersAfterAdd = gamePostController.addNewUserPlaying(gamePostID, u2.getId(),
                u2.getEmail(), u2.getPassword());

        assertEquals(playersCountBeforeAdd + 1, playersAfterAdd.size());
        assertTrue(playersAfterAdd.contains(u2));
    }

    @Test
    void addGamePostToSport() {
        int sport_id = 1;
        int created_by = 1;
        int max_players = 10;
        int min_players = 5;
        String playing_on = "2/1/2023";
        String created_on = "1/1/2023";
        boolean is_deleted = false;
        String email = "user@example.com";
        String password = "password123";


        Users creator = new Users("New", "User", "new@example.com", "password");
        creator.setId(created_by);
        when(userRepository.findById(created_by)).thenReturn(creator);

        GamePost gamePost = new GamePost(s1, creator, max_players, min_players, playing_on,
                created_on, is_deleted);


        when(userRepository.findById(gamePost.getCreated_by().getId())).thenReturn(creator);
        when(gamePostRepository.findById(gamePost.getId())).thenReturn(gamePost);
        when(sportRepository.findById(s1.getId())).thenReturn(s1);

        GamePost updatedGamePost = gamePostController.addGamePostToSport(gamePost.getId(),
                s1.getId(), email, password);

        assertEquals(s1.getId(), updatedGamePost.getSport().getId());
    }

    // @Test
    // void removeUserPlaying() {
    // String email = "user@example.com";
    // String password = "password123";

    // Users userToRemove = new Users("User", "Remove", email, password);

    // when(userRepository.findById(userToRemove.getId())).thenReturn(userToRemove);
    // when(gamePostRepository.findById(gp1.getId())).thenReturn(gp1);

    // gp1.getPlayersSignedUp().add(userToRemove);
    // Set<Users> playersBeforeRemove = gp1.getPlayersSignedUp();
    // int playersCountBeforeRemove = playersBeforeRemove.size();

    // Set<Users> playersAfterRemove = gamePostController.removeUserPlaying(gp1.getId(),
    // userToRemove.getId(), email, password);

    // assertEquals(playersCountBeforeRemove - 1, playersAfterRemove.size());
    // assertFalse(playersAfterRemove.contains(userToRemove));
    // }

    @Test
    void deleteGamePostById() {
        // Arrange
        String email = "Bsmith@example.com";
        String password = "smith3920";
        Users u = new Users("George", "Smith", email, password);

        // Mock authentication to return true
        when(Auth.isAuthenticated(u, email, password)).thenReturn(true);
        Sport s = new Sport("Tennis");

        GamePost g = new GamePost(s, u, 24, 20, "1/12/2023", "29/09/1988", false);
        when(sportRepository.findById(s.getId())).thenReturn(s);
        when(userRepository.findById(u.getId())).thenReturn(u);
        when(gamePostRepository.findById(g.getId())).thenReturn(g);

        // Act
        String result = gamePostController.deleteGamePostById(g.getId(), email, password);

        // Assert
        verify(gamePostRepository, times(1)).deleteById(anyInt()); // Use anyInt() here
        assertEquals("Successfully deleted GamePosts with id " + g.getId(), result);
        assertEquals(0, gamePostRepository.findAll().size());
    }


    @Test
    void deleteAllGamePosts() {
        // Arrange
        String email = "Bsmith@example.com";
        String password = "smith3920";
        Users u = new Users("George", "Smith", email, password);
        u.setRole(ADMIN);
        // Mock authentication to return true

        when(Auth.isAdmin(u)).thenReturn(true);
        assertTrue(u.isAuthenticated(email, password));
        assertTrue(u.isAdmin(u));
        Sport s = new Sport("Tennis");

        GamePost g = new GamePost(s, u, 24, 20, "1/12/2023", "29/09/1988", false);
        when(sportRepository.findById(s.getId())).thenReturn(s);
        when(userRepository.findById(u.getId())).thenReturn(u);
        when(userRepository.findByEmail(u.getEmail())).thenReturn(u);
        when(gamePostRepository.findById(g.getId())).thenReturn(g);

        // Act
        String result = gamePostController.deleteAllGamePosts(email, password);

        // Assert
        assertEquals("Successfully deleted all GamePosts", result);
        assertEquals(0, gamePostRepository.findAll().size());

    }

}
