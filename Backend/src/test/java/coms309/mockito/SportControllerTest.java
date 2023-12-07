package coms309.mockito;

import coms309.GamePost.GamePost;
import coms309.GamePost.GamePostRepository;
import coms309.Location.Location;
import coms309.SkillLevel.SkillLevel;
import coms309.SkillLevel.SkillLevelRepository;
import coms309.Sport.Sport;
import coms309.Sport.SportController;
import coms309.Sport.SportRepository;
import coms309.Users.UserRepository;
import coms309.Users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SportControllerTest {
    @InjectMocks
    SportController sportController;

    @Mock
    GamePostRepository gamePostRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    SportRepository sportRepository;
    @Mock
    SkillLevelRepository skillLevelRepository;

    private GamePost gp1, gp2, gp3, gp4;
    private Users u1, u2;
    private Sport s1, s2, s3, s4;
    private SkillLevel sk1;

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

        sk1 = new SkillLevel(9, u1);
        skillLevelRepository.save(sk1);

        gp1 = new GamePost(s1, userRepository.findById(u1.getId()), 24, 20, "1/12/2023",
                "29/09/1988", false);
        gp2 = new GamePost(s1, userRepository.findById(u1.getId()), 16, 2, "1/2/2023", "1/02/2023",
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
    void getAllSports() {
        List<Sport> sports = new ArrayList<>();
        sports.add(s1);
        sports.add(s2);
        sports.add(s3);
        sports.add(s4);

        when(sportRepository.findAll()).thenReturn(sports);

        List<Sport> result = sportController.getAllSports();

        assertEquals(4, result.size());
        assertEquals("Basketball", result.get(0).getSport_name());
    }

    @Test
    void getSportById() {
        when(sportRepository.findById(1)).thenReturn(s1);
        Sport result = sportController.getSportById(1);

        assertNotNull(result);
        assertEquals("Basketball", result.getSport_name());
    }

    @Test
    void getAllGamePostsPlayingASport() {
        int sport_id = 1;
        when(sportRepository.findById(sport_id)).thenReturn(s1);
        Set<GamePost> expectedGPList = new HashSet<>();
        expectedGPList.add(gp1);
        expectedGPList.add(gp2);
        s1.setGamePosts(expectedGPList);

        Set<GamePost> actualGPList = sportController.getAllGamePostsPlayingASport(sport_id);

        assertEquals(expectedGPList.size(), actualGPList.size());
        assertTrue(actualGPList.contains(gp2));
    }

    @Test
    void createSport() {
        List<Sport> sportList = new ArrayList<>();
        SkillLevel sk = new SkillLevel(1, userRepository.findById(1), sportRepository.findById(1));
        when(skillLevelRepository.findById(1)).thenReturn(sk);
        when(userRepository.findById(1)).thenReturn(u1);
        when(sportRepository.findById(1)).thenReturn(s1);
        String sportName = "Rugby";
        int skillLevelId = 1;
        Sport sport = new Sport(sportName, sk);
        when(sportRepository.findAll()).thenReturn(sportList);

        int sportRepoSize = sportRepository.findAll().size();
        Sport createdSport = sportController.createSport(sportName, skillLevelId);
        sportList.add(sport);
        int newSportRepoSize = sportRepository.findAll().size();

        assertEquals(sportName, createdSport.getSport_name());
        assertEquals(sportRepoSize + 1, newSportRepoSize);

    }

    @Test
    void updateSport() {
        String sportName = "Football";
        int sport_id = 1;
        int skillLevel_id = 1;
        when(skillLevelRepository.findById(skillLevel_id)).thenReturn(sk1);
        when(sportRepository.findById(sport_id)).thenReturn(s1);
        Sport updatedSport = sportController.updateSport("Hockey", sport_id, skillLevel_id);

        assertEquals(s1.getSport_name(), updatedSport.getSport_name());

    }

    @Test
    void deleteSportById() {
        List<Sport> sportList = Arrays.asList(s1, s2, s3, s4);
        int sport_id = 1;

        when(sportRepository.findById(sport_id)).thenReturn(s1);
        when(sportRepository.findAll()).thenReturn(sportList);
        // Act
        String deletedSport = sportController.deleteSportById(sport_id);
        // Assert
        verify(sportRepository, times(1)).deleteById(sport_id); // Verify that
                                                                // locationRepository.deleteById is
                                                                // called once
        assertEquals("Sport with id " + sport_id + " has been deleted", deletedSport);
    }

    @Test
    void deleteAllSports() {
        List<Sport> sportList = Arrays.asList(s1, s2, s3, s4);
        int sport_id = 1;

        when(sportRepository.findById(sport_id)).thenReturn(s1);
        when(sportRepository.findAll()).thenReturn(sportList);
        // Act
        String deletedSports = sportController.deleteAllSports();
        // Assert
        verify(sportRepository, times(1)).deleteAll(); // Verify that locationRepository.deleteById
                                                       // is called once
        assertEquals("All sports have been deleted", deletedSports);
    }
}
