package coms309.Profile;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import coms309.Users.UserRepository;
import coms309.Users.Users;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public Profile getProfileById(@Argument int id) {
        return profileRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Profile getProfileByUsername(@Argument String username) {
        return profileRepository.findByUsername(username);
    }

    @QueryMapping
    public Profile getProfileByUserId(@Argument int userId) {
        return profileRepository.findById(userId).orElse(null);
    }

    @QueryMapping
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @MutationMapping
    public Profile createProfile(@Argument String username, @Argument String bio,
            @Argument String profilePicture, @Argument String profileBanner,
            @Argument String profileColor, @Argument String profileTextColor, @Argument int user) {

        Users usr = userRepository.findById(user);
        Profile profile = userRepository.findById(user).getProfile();
        if (profile == null) {
            profile = new Profile();
        }

        profile.setUsername(username);
        profile.setBio(bio);
        profile.setProfilePicture(profilePicture);
        profile.setProfileBanner(profileBanner);
        profile.setProfileColor(profileColor);
        profile.setProfileTextColor(profileTextColor);
        profile.setUser(usr);
        usr.setProfile(profile);

        return userRepository.save(usr).getProfile();
    }
}
