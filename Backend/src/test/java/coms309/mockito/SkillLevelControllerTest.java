// package coms309.mockito;

// import coms309.Comment.Comment;
// import coms309.Comment.CommentController;
// import coms309.Comment.CommentRepository;
// import coms309.Exceptions.NotFoundException;
// import coms309.GamePost.GamePost;
// import coms309.GamePost.GamePostRepository;
// import coms309.SkillLevel.SkillLevel;
// import coms309.SkillLevel.SkillLevelController;
// import coms309.SkillLevel.SkillLevelRepository;
// import coms309.Sport.Sport;
// import coms309.Sport.SportRepository;
// import coms309.Users.UserRepository;
// import coms309.Users.Users;
// import org.checkerframework.checker.units.qual.g;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.*;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// class SkillLevelControllerTest {
// @InjectMocks
// SkillLevelController skillLevelController;
// @Mock
// CommentRepository commentRepository;
// @Mock
// GamePostRepository gamePostRepository;
// @Mock
// UserRepository userRepository;
// @Mock
// SportRepository sportRepository;
// @Mock
// SkillLevelRepository skillLevelRepository;

// private GamePost gp1, gp2, gp3, gp4;
// private Users u1, u2;
// private Sport s1, s2, s3, s4;
// private SkillLevel sk1,sk2,sk3,sk4;
// private Comment c1,c2,c3,c4,c5;

// @BeforeEach
// public void init() {
// MockitoAnnotations.initMocks(this);

// s1 = new Sport("Basketball");
// s2 = new Sport("Pickleball");
// s3 = new Sport("Football");
// s4 = new Sport("Spikeball");
// sportRepository.save(s1);
// sportRepository.save(s2);
// sportRepository.save(s3);
// sportRepository.save(s4);


// u1 = new Users("Bill", "Smith", "Bsmith@example.com", "smith3920");
// u2 = new Users("John", "1234", "john@gmail.com", "Mar1o&Lu1g1");
// userRepository.save(u1);
// userRepository.save(u2);

// sk1 = new SkillLevel(9, u1);
// sk1 = new SkillLevel(9, u1);
// sk1 = new SkillLevel(9, u1);
// sk1 = new SkillLevel(9, u2);
// skillLevelRepository.save(sk1);
// skillLevelRepository.save(sk2);
// skillLevelRepository.save(sk3);
// skillLevelRepository.save(sk4);


// gp1 = new GamePost(s1, userRepository.findById(u1.getId()), 24, 20, "1/12/2023", "29/09/1988",
// false);
// gp2 = new GamePost(s1, userRepository.findById(u1.getId()), 16, 2, "1/2/2023", "1/02/2023",
// false);
// gp3 = new GamePost(s3, userRepository.findById(u2.getId()), 10, 8, "10/08/2023", "10/25/2023",
// false);
// gp4 = new GamePost(s4, userRepository.findById(u2.getId()), 6, 3, "11/1/2023", "11/09/2023",
// false);
// gamePostRepository.save(gp1);
// gamePostRepository.save(gp2);
// gamePostRepository.save(gp3);
// gamePostRepository.save(gp4);
// c1 = new Comment("This is a comment 1", gp1, gp1.getCreated_by().getId());
// c2 = new Comment("This is a comment 2", gp1, gp1.getCreated_by().getId());
// c3 = new Comment("This is a comment 3", gp2, gp2.getCreated_by().getId());
// c4 = new Comment("This is a comment 4", gp2, gp2.getCreated_by().getId());
// c5 = new Comment("This is a comment 5", gp3, gp3.getCreated_by().getId());

// commentRepository.save(c1);
// commentRepository.save(c2);
// commentRepository.save(c3);
// commentRepository.save(c4);
// commentRepository.save(c5);
// }

// @Test
// void getAllSkillLevels() {
// List<SkillLevel> skillLevels = new ArrayList<>();
// skillLevels.add(sk1);
// skillLevels.add(sk2);
// skillLevels.add(sk3);
// skillLevels.add(sk4);

// when(skillLevelRepository.findAll()).thenReturn(skillLevels);

// List<SkillLevel> result = skillLevelController.getAllSkillLevels();

// assertEquals(4, result.size());
// assertEquals(sk1.getSkill_level(), result.get(0).getSkill_level());
// }
// @Test
// void getSkillLevelBuId() {
// int skillLevel_id = 1;
// when(skillLevelRepository.findById(skillLevel_id)).thenReturn(sk1);
// SkillLevel result = skillLevelController.getSkillLevelById(skillLevel_id);

// assertNotNull(result);
// assertEquals(sk1.getSkill_level(), result.getSkill_level());
// }
// @Test
// void createSkillLevel() {
// int user_id = 1;
// int sport_id = 1;
// int skill_level = 9;
// when(userRepository.findById(user_id)).thenReturn(u1);
// when(sportRepository.findById(sport_id)).thenReturn(s1);
// SkillLevel newSkillLevel = skillLevelController.createSkillLevel(user_id,skill_level,sport_id);
// assertEquals(sk1.getSkill_level(), newSkillLevel.getSkill_level());
// }
// @Test
// void updateSkillLevel() {
// int user_id = 1;
// int sport_id = 1;
// int skill_level_id = 1;
// int skill_level = 10;
// when(userRepository.findById(user_id)).thenReturn(u1);
// when(skillLevelRepository.findById(skill_level_id)).thenReturn(sk1);
// when(sportRepository.findById(sport_id)).thenReturn(s1);
// SkillLevel updatedSkillLevel =
// skillLevelController.updateSkillLevel(skill_level_id,skill_level,sport_id);
// assertEquals(sk1.getSkill_level(), updatedSkillLevel.getSkill_level());
// }
// @Test
// void deleteSkillLevelById() {
// int skill_level_id = 1;
// when(skillLevelRepository.findById(skill_level_id)).thenReturn(sk1);
// String deletedSkillLevel = skillLevelController.deleteSkillLevelById(skill_level_id);
// assertEquals("SkillLevel with id " + skill_level_id + " has been deleted", deletedSkillLevel);
// }
// @Test
// void deleteAllSkillLevels() {
// String deletedMessage = "All Skill Levels have been deleted";
// // Create and initialize SkillLevel objects
// SkillLevel sk1 = new SkillLevel(1, u1, s1);
// SkillLevel sk2 = new SkillLevel(2, u2, s2);
// SkillLevel sk3 = new SkillLevel(3, u2, s3);
// SkillLevel sk4 = new SkillLevel(4, u1, s4);
// List<SkillLevel> skillLevels = new ArrayList<>();
// skillLevels.add(sk1);
// skillLevels.add(sk2);
// skillLevels.add(sk3);
// skillLevels.add(sk4);
// // Mock the skillLevelRepository behavior
// when(skillLevelRepository.findAll()).thenReturn(skillLevels);
// String deleteAllMessages = skillLevelController.deleteAllSkillLevels();
// assertEquals(deletedMessage, deleteAllMessages);
// }
// }
