package coms309.Profile;

import coms309.Users.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "profile")
public class Profile {

    public Profile(Users user) {
        this.user = user;
    }

    public Profile(int userId) {
        this.id = userId;
    }

    @Id
    @Column(name = "user_id")
    private int id;

    private String username;
    private String bio;
    private String profilePicture;
    private String profileBanner;
    private String profileColor;
    private String profileTextColor;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Users user;
}
