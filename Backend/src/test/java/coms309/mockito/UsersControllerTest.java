package coms309.mockito;

import static graphql.Assert.assertNull;
import static graphql.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import coms309.Users.UserController;

import coms309.Users.UserRepository;
import coms309.Users.Users;
import coms309.Users.UsersPublisher;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;


public class UsersControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserRepository userRepository;
	@Mock
	UsersPublisher usersPublisher;

	private Users testUser1, testUser2, testUser3, testUser4;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		testUser1 = new Users("Bill", "Smith", "Bsmith@example.com", "smith3920");
		testUser2 = new Users("John", "1234", "john@gmail.com", "Mar1o&Lu1g1");
		testUser3 = new Users("Alex", "abcd", "alex@yahoo.com", "Cycylones12");
		testUser4 = new Users("Steve", "efgh", "steve@gmail.com", "WhiteFence2390");
	}

	@Test
	public void GetUserById() {
		when(userRepository.findById(0)).thenReturn(testUser1);

		Users user = userController.getUserById(0);

		assertEquals(testUser1.getId(), user.getId());
		assertEquals(testUser1.getFirst_name(), user.getFirst_name());
		assertEquals(testUser1.getLast_name(), user.getLast_name());
		assertEquals(testUser1.getEmail(), user.getEmail());
		assertEquals(testUser1.getPassword(), user.getPassword());
	}

	@Test
	public void GetAllUsers() {
		List<Users> list = new ArrayList<Users>();

		list.add(testUser2);
		list.add(testUser3);
		list.add(testUser4);

		when(userRepository.findAll()).thenReturn(list);

		List<Users> UserList = userController.getAllUsers();
		assertEquals(3, UserList.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void CreateUser() {
		String firstName = "John";
		String lastName = "Doe";
		String email = "john@example.com";
		String password = "password";
		Users user = new Users(firstName, lastName, email, password);

		// Mock the UserRepository to return the user with an ID.
		when(userRepository.save(any(Users.class))).thenReturn(user);
		when(userRepository.findById(anyInt())).thenReturn(user);

		Users result = userController.createUser(firstName, lastName, email, password);

		assertTrue(result.equals(user));

		firstName = "henry";
		lastName = "ford";
		email = "henry@example.com";
		password = "123fog567";
		Users user2 = new Users(firstName, lastName, email, password);

		// Mock the UserRepository to return the user with an ID.
		when(userRepository.save(any(Users.class))).thenReturn(user2);
		when(userRepository.findById(anyInt())).thenReturn(user2);

		Users result2 = userController.createUser(firstName, lastName, email, password);

		// Assert that each attribute of the result matches the expected value.
		assertTrue(result2.equals(user2));
	}

	@Test
	public void UpdateUser() {
		// Mock data
		String firstName = "UpdatedFirstName";
		String lastName = "UpdatedLastName";
		String email = "john@example.com";
		String password = "password";

		Users existingUser = new Users("John", "Doe", email, password);
		Users newUser = new Users(firstName, lastName, email, password);

		int id = existingUser.getId();

		// Mock the behavior of userRepository.findById(id)
		when(userRepository.findById(id)).thenReturn(existingUser);
		when(userRepository.save(any(Users.class))).thenReturn(newUser);

		// Call the updateUser method
		Users updatedUser = userController.updateUser(id, firstName, lastName, email, password);

		// Verify that the user was updated correctly
		assertNotNull(updatedUser);
		// assertNotEquals(existingUser, updatedUser);
		assertEquals(existingUser.getId(), updatedUser.getId());
		// assertNotEquals(existingUser.getFirst_name(), updatedUser.getFirst_name());
		// assertNotEquals(existingUser.getLast_name(), updatedUser.getLast_name());

		// Verify that userRepository.save(user) was called once
		verify(userRepository, times(1)).save(updatedUser);
	}

	@Test
	void UpdateUserTest() {

	}

	@Test
	public void DeleteUser() {
		int id = 1;
		String email = "john@example.com";
		String password = "password";

		Users existingUser = new Users("John", "Doe", email, password);
		existingUser.setId(id);

		// Mock the behavior of userRepository.findById(id)
		when(userRepository.findById(id)).thenReturn(existingUser);

		// Call the deleteUser method
		Users deletedUser = userController.deleteUser(id, email, password);

		// Verify that the user was deleted correctly
		assertNull(deletedUser);

		// Verify that userRepository.deleteById(id) was called once
		verify(userRepository, times(1)).deleteById(id);
	}


}
