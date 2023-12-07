package coms309.Profile;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findByUsername(String username);

    String findProfilePictureByUserId(int userId);

}
