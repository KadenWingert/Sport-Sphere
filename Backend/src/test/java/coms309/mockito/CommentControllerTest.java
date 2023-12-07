package coms309.mockito;

import coms309.Comment.Comment;
import coms309.Comment.CommentController;
import coms309.Comment.CommentPublisher;
import coms309.Comment.CommentRepository;
import coms309.CommentMessage.CommentMessage;
import coms309.CommentMessage.CommentMessageRepository;
import coms309.GamePost.GamePost;
import coms309.GamePost.GamePostRepository;
import coms309.SkillLevel.SkillLevel;
import coms309.SkillLevel.SkillLevelRepository;
import coms309.Sport.Sport;
import coms309.Sport.SportRepository;
import coms309.Users.UserRepository;
import coms309.Users.Users;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentControllerTest {
    @InjectMocks
    CommentController commentController;

    @Mock
    CommentRepository commentRepository;
    @Mock
    CommentMessageRepository commentMessageRepository;
    @Mock
    GamePostRepository gamePostRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    SportRepository sportRepository;
    @Mock
    SkillLevelRepository skillLevelRepository;

    @Mock
    CommentPublisher commentPublisher;

    private GamePost gp1, gp2, gp3, gp4;
    private Users u1, u2;
    private Sport s1, s2, s3, s4;
    private SkillLevel sk1;
    private Comment c1, c2, c3, c4, c5;
    private CommentMessage cm1, cm2, cm3, cm4, cm5;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        s1 = new Sport("Basketball");
        s2 = new Sport("Pickleball");
        s3 = new Sport("Football");
        s4 = new Sport("Spikeball");
        sportRepository.save(s1);
        sportRepository.save(s2);
        sportRepository.save(s3);
        sportRepository.save(s4);


        u1 = new Users("Bill", "Smith", "Bsmith@example.com", "smith3920");
        u2 = new Users("John", "1234", "john@gmail.com", "Mar1o&Lu1g1");
        userRepository.save(u1);
        userRepository.save(u2);

        sk1 = new SkillLevel(9, u1);
        skillLevelRepository.save(sk1);

        gp1 = new GamePost(s1, userRepository.findById(u1.getId()), 24, 20, "1/12/2023",
                "29/09/1988", false);
        gp2 = new GamePost(s1, userRepository.findById(u1.getId()), 16, 2, "1/2/2023", "1/02/2023",
                false);
        gp3 = new GamePost(s3, userRepository.findById(u2.getId()), 10, 8, "10/08/2023",
                "10/25/2023", false);
        gp4 = new GamePost(s4, userRepository.findById(u2.getId()), 6, 3, "11/1/2023", "11/09/2023",
                false);
        gamePostRepository.save(gp1);
        gamePostRepository.save(gp2);
        gamePostRepository.save(gp3);
        gamePostRepository.save(gp4);

        c1 = new Comment(gp1, u1);
        c2 = new Comment(gp1, u2);
        c3 = new Comment(gp2, u1);
        c4 = new Comment(gp2, u1);
        c5 = new Comment(gp3, u2);

        commentRepository.save(c1);
        commentRepository.save(c2);
        commentRepository.save(c3);
        commentRepository.save(c4);
        commentRepository.save(c5);

        cm1 = new CommentMessage("This is a comment 1", c1);
        cm2 = new CommentMessage("This is a comment 2", c2);
        cm3 = new CommentMessage("This is a third comment", c3);
        cm4 = new CommentMessage("This is a fourth comment", c4);
        cm5 = new CommentMessage("This is a fifth comment", c5);

        commentMessageRepository.save(cm1);
        commentMessageRepository.save(cm2);
        commentMessageRepository.save(cm3);
        commentMessageRepository.save(cm4);
        commentMessageRepository.save(cm5);

        c1.setComment_details(cm1);
        c2.setComment_details(cm2);
        c3.setComment_details(cm3);
        c4.setComment_details(cm4);
        c5.setComment_details(cm5);

        commentRepository.save(c1);
        commentRepository.save(c2);
        commentRepository.save(c3);
        commentRepository.save(c4);
        commentRepository.save(c5);

    }

    @Test
    void getAllComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(c1);
        comments.add(c2);
        comments.add(c3);
        comments.add(c4);

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> result = commentController.getAllComments();

        assertEquals(4, result.size());
        assertEquals("This is a comment 1", result.get(0).getComment_details().getMessage());
    }

    @Test
    void getCommentByID() {
        when(commentRepository.findById(1)).thenReturn(c1);
        Comment result = commentController.getCommentById(1);

        assertNotNull(result);
        assertEquals("This is a comment 1", result.getComment_details().getMessage());
    }

    @Test
    void createComment() {

        List<Comment> comments = new ArrayList<>();
        when(commentRepository.findById(1)).thenReturn(c1);
        when(commentRepository.findAll()).thenReturn(comments);
        when(gamePostRepository.findById(1)).thenReturn(gp1);
        int commentRepoSize = commentRepository.findAll().size();
        Users user = new Users();
        Comment newComment =
                commentController.createComment(1, "This is a comment 1", user.getId());
        // newComment.setComment_details(new CommentMessage("This is a comment 1"));
        newComment = newComment == null
                ? new Comment(gp1, user, new CommentMessage("This is a comment 1"))
                : newComment;
        when(commentRepository.save(newComment)).thenReturn(newComment);
        newComment = commentRepository.save(newComment);
        comments.add(newComment);

        int newCommentRepoSize = commentRepository.findAll().size();

        assertEquals(c1.getComment_details().getMessage(),
                newComment.getComment_details().getMessage());
        assertEquals("This is a comment 1", newComment.getComment_details().getMessage());
        assertEquals(commentRepoSize + 1, newCommentRepoSize);
    }

    @Test
    void updateComment() {
        String updatedMessage = "This is the updated Message";
        CommentMessage cm = new CommentMessage("This is a comment", c1);
        // when(commentMessageRepository.findById(1)).thenReturn(cm);
        int gamePostID = 1;
        when(gamePostRepository.findById(1)).thenReturn(gp1);
        Comment testUpdate = new Comment(gp1, u1, cm);
        when(commentRepository.findById(1)).thenReturn(testUpdate);
        Comment updatedComment = commentController.updateComment(1, updatedMessage);

        assertEquals(testUpdate.getMessage(), updatedComment.getMessage());
        assertEquals(testUpdate.getId(), updatedComment.getId());

    }

    @Test
    void deleteComment() {
        int comment_id = 1;
        String message = "This is a Message";
        when(gamePostRepository.findById(1)).thenReturn(gp1);

        CommentMessage cm = new CommentMessage("This is a comment", c1);

        Comment c = new Comment(gp1, u1, cm);
        when(commentRepository.findById(comment_id)).thenReturn(c);

        Comment deletedComment = commentController.deleteCommentById(comment_id);
        assertEquals(c.getMessage(), deletedComment.getMessage());
        verify(commentRepository, times(1)).save(c);
    }

    @Test
    void deleteAllComments() {
        String deletedMessage = "All Comments have been deleted";
        List<Comment> comments = new ArrayList<>();
        comments.add(c1);
        comments.add(c2);
        comments.add(c3);
        comments.add(c4);
        comments.add(c5);
        when(commentRepository.findAll()).thenReturn(comments);

        String deleteAllMessages = commentController.deleteAllComments();
        assertEquals(deletedMessage, deleteAllMessages);
        verify(commentRepository, times(1)).deleteAll();
    }
}
