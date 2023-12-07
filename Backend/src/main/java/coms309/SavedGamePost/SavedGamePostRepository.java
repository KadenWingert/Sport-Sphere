package coms309.SavedGamePost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedGamePostRepository extends JpaRepository<SavedGamePost, Integer> {

    SavedGamePost findByUserId(int userId);


}
