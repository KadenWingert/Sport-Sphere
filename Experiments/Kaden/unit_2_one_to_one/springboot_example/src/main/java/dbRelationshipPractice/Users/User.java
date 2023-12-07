package dbRelationshipPractice.Users;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;
    @Column(name = "first_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String firstName;
    @Column(name = "last_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String lastName;
    @Column(name = "email")
    @NotFound(action = NotFoundAction.IGNORE)
    private String email;
    @Column(name = "password")
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;


    public User(){
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    
    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}