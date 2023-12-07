package coms309.Users;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureGraphQlTester
public class UserControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetFirstUser() {
        this.graphQlTester.documentName("userDetails").variable("id", "1").execute()
                .path("getUserById").matchesJson("""
                        {
                        "id": "1",
                        "first_name": "Mark",
                        "last_name": "Heath",
                        "email": "Mheath@gmail.com",
                        "password": "SportsSphere1!"
                        }
                        """);
    }

    @Test
    void querydslRepositorySingle() {
        this.graphQlTester.documentName("userDetails").variable("id", "1").execute()
                .path("getUserById.first_name").entity(String.class).isEqualTo("Mark");
    }

    @Test
    void querydslRepositoryMany() {
        this.graphQlTester.documentName("allUsers").execute().path("getAllUsers[*].id")
                .entityList(String.class).contains("1", "2", "3", "4", "5", "6", "7", "8");
    }

    @Test
    void shouldGetUserByEmail() {
        this.graphQlTester.documentName("getUserByEmail").variable("email", "Mheath@gmail.com")
                .execute().path("getUserByEmail").matchesJson("""
                        {
                        "id": "1",
                        "first_name": "Mark",
                        "last_name": "Heath",
                        "email": "Mheath@gmail.com",
                        "password": "SportsSphere1!"
                        }
                        """);
    }

    // static Users user = new Users();

    // static String TEST_USERNAME = "test";
    // static String TEST_LASTNAME = "test";
    // static String TEST_EMAIL = "test";
    // static String TEST_PASSWORD = "test";

    // @BeforeAll
    // static void setUp() {
    // user.setFirst_name(TEST_USERNAME);
    // user.setLast_name(TEST_LASTNAME);
    // user.setEmail(TEST_EMAIL);
    // user.setPassword(TEST_PASSWORD);
    // }

    @Test
    void createTest() {
        this.graphQlTester.documentName("createUser").variable("first_name", "test")
                .variable("last_name", "user").variable("email", "testuser@sportsphere.com")
                .variable("password", "password").execute().path("createUser");

    }

    @Test
    void deleteUserTest() {
        this.graphQlTester.documentName("deleteUserByEmail")
                .variable("email", "testuser@sportsphere.com").variable("password", "password")
                .execute().path("deleteUserByEmail");

    }
}
