package coms309.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

import coms309.Users.UserController;

import coms309.Users.UserRepository;
import coms309.Users.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


public class TestUserController {

	@InjectMocks
	UserController userController;

	@Mock
	UserRepository repo;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUserByIdTest() {
		when(repo.findById(1)).thenReturn(new Users("John", "Doe", "jDoe@gmail.com", "Redfence10"));

		Users user = userController.getUserById(1);

		assertEquals("John", user.getFirst_name());
		assertEquals("Doe", user.getLast_name());
		assertEquals("jDoe@gmail.com", user.getEmail());
		assertEquals("Redfence10", user.getPassword());
	}

	@Test
	public void getAllUsers() {
		List<Users> list = new ArrayList<Users>();
		Users u1 = new Users("John", "1234", "john@gmail.com", "Mar1o&Lu1g1");
		Users u2 = new Users("Alex", "abcd", "alex@yahoo.com", "Cycylones12");
		Users u3 = new Users("Steve", "efgh", "steve@gmail.com", "WhiteFence2390");

		list.add(u1);
		list.add(u2);
		list.add(u3);

		when(repo.findAll()).thenReturn(list);

		List<Users> usersList = userController.getAllUsers();

		assertEquals(3, usersList.size());
		verify(repo, times(1)).findAll();
	}

	@Test
	public void updateUser() {
		Users u1 = new Users("John", "1234", "john@gmail.com", "Mar1o&Lu1g1");
		Users u2 = new Users("Alex", "abcd", "alex@yahoo.com", "Cycylones12");

		when(repo.findById(1)).thenReturn(u1);
		when(repo.save(notNull())).thenReturn(u2);
		Users old = repo.findById(1);

		Users user =
				userController.updateUser(1, "Jim", "Martin", old.getEmail(), old.getPassword());

		assertNotEquals(old, user);
		assertEquals(old.getId(), user.getId());
	}

}
