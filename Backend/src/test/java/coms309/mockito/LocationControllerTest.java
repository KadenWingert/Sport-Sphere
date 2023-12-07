package coms309.mockito;

import coms309.GamePost.GamePost;
import coms309.GamePost.GamePostController;
import coms309.GamePost.GamePostRepository;
import coms309.Location.Location;
import coms309.Location.LocationController;
import coms309.Location.LocationRepository;
import coms309.Sport.Sport;
import coms309.Sport.SportRepository;
import coms309.Users.UserRepository;
import coms309.Users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LocationControllerTest {


    @InjectMocks
    LocationController locationController;

    @Mock
    LocationRepository locationRepository;
    @Mock
    GamePostRepository gamePostRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    SportRepository sportRepository;

    private GamePost gp1, gp2, gp3, gp4;
    private Users u1, u2;
    private Sport s1, s2, s3, s4;
    private Location l1, l2, l3, l4;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        Sport s1 = new Sport("Basketball");
        Sport s2 = new Sport("Pickleball");
        Sport s3 = new Sport("Football");
        Sport s4 = new Sport("Spikeball");

        u1 = new Users("Bill", "Smith", "Bsmith@example.com", "smith3920");
        u2 = new Users("John", "1234", "john@gmail.com", "Mar1o&Lu1g1");

        gp1 = new GamePost(s1, userRepository.findById(u1.getId()), 24, 20, "1/12/2023",
                "29/09/1988", false);
        gp2 = new GamePost(s2, userRepository.findById(u1.getId()), 16, 2, "1/2/2023", "1/02/2023",
                false);
        gp3 = new GamePost(s3, userRepository.findById(u2.getId()), 10, 8, "10/08/2023",
                "10/25/2023", false);
        gp4 = new GamePost(s4, userRepository.findById(u2.getId()), 6, 3, "11/1/2023", "11/09/2023",
                false);

        l1 = new Location("324 South James street", "34,23", gp1);
        l2 = new Location("123 Hyland Ave", "56,23", gp2);
        l3 = new Location("123 Hyland Ave", "52,390", gp3);
        l4 = new Location("324 PGA Drive", "964,234", gp4);
    }

    @Test
    void getAllLocations() {
        // Arrange
        List<Location> expectedLocations = Arrays.asList(l1, l2, l3, l4);
        when(locationRepository.findAll()).thenReturn(expectedLocations);

        // Act
        List<Location> actualLocations = locationController.getAllLocations();

        // Assert
        assertEquals(expectedLocations, actualLocations);
    }


    @Test
    void getLocationById() {
        // Arrange
        int locationId = 1; // Replace with a valid location ID
        when(locationRepository.findById(locationId)).thenReturn(l1);

        Location actualLocation = locationController.getLocationById(locationId);

        assertEquals(l1, actualLocation);
    }


    @Test
    void getGamePostsAtLocation() {
        // Arrange
        String address = "123 Hyland Ave"; // Replace with a valid address

        // Create two GamePost objects with the desired address
        GamePost gpWithAddress1 = new GamePost(s1, userRepository.findById(u1.getId()), 16, 2,
                "1/2/2023", "1/02/2023", false, "123 Hyland Ave", "23.3");
        GamePost gpWithAddress2 = new GamePost(s3, userRepository.findById(u2.getId()), 10, 8,
                "10/08/2023", "10/25/2023", false, "123 Hyland Ave", "23.3");

        // Create a list of GamePost objects that includes the ones with the desired address
        List<GamePost> gamePosts = Arrays.asList(gp1, gpWithAddress1, gpWithAddress2, gp4);
        when(gamePostRepository.findAll()).thenReturn(gamePosts);

        // Act
        Set<GamePost> actualGamePosts = locationController.getGamePostsAtLocation(address);

        // Assert
        Set<GamePost> expectedGamePosts =
                new HashSet<>(Arrays.asList(gpWithAddress1, gpWithAddress2));
        assertEquals(expectedGamePosts.size(), actualGamePosts.size());
        assertTrue(actualGamePosts.containsAll(expectedGamePosts));
    }



    @Test
    void createLocation() {
        // Arrange
        String address = "123 New Street";
        String gps = "45,67";
        int gamePostID = 1;
        GamePost gamePost = new GamePost(s1, userRepository.findById(u1.getId()), 24, 20,
                "1/12/2023", "29/09/1988", false);

        // Mock the gamePostRepository to return the gamePost when findById is called
        when(gamePostRepository.findById(gamePostID)).thenReturn(gamePost);

        // Act
        Location createdLocation = locationController.createLocation(address, gps, gamePostID);

        // Assert
        assertEquals(address, createdLocation.getAddress());
        assertEquals(gps, createdLocation.getGps());
        assertEquals(gamePost.getId(), createdLocation.getGamePost().getId());
    }


    @Test
    void updateLocation() {
        // Arrange
        String newAddress = "456 Updated Street";
        String newGPS = "12,34";
        Location existingLocation = new Location("123 Old Street", "56,78", gp1);

        // Mock the locationRepository to return the existingLocation when findById is called
        when(locationRepository.findById(existingLocation.getId())).thenReturn(existingLocation);

        // Act
        Location updatedLocation =
                locationController.updateLocation(existingLocation.getId(), newAddress, newGPS);

        // Assert
        assertEquals(existingLocation.getId(), updatedLocation.getId());
        assertEquals(newAddress, updatedLocation.getAddress());
        assertEquals(newGPS, updatedLocation.getGps());
        verify(locationRepository, times(1)).save(existingLocation); // Verify that
                                                                     // locationRepository.save is
                                                                     // called once
    }

    @Test
    void deleteLocation() {
        // Arrange
        int locationID = 1;
        Location locationToDelete = new Location("123 Old Street", "56,78", gp1);

        // Mock the locationRepository to return the locationToDelete when findById is called
        when(gamePostRepository.findById(gp1.getId())).thenReturn(gp1);
        when(locationRepository.findById(locationID)).thenReturn(locationToDelete);
        when(locationRepository.findAll()).thenReturn(Arrays.asList(locationToDelete, l2, l3, l4));
        // Act
        Location deletedLocation = locationController.deleteLocation(locationID);
        // Assert
        assertEquals(locationToDelete, deletedLocation);
        verify(locationRepository, times(1)).deleteById(locationID); // Verify that
                                                                     // locationRepository.deleteById
                                                                     // is called once
        assertNull(gp1.getLocation());
    }
}
