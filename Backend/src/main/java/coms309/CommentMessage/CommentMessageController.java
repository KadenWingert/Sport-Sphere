package coms309.CommentMessage;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Component
public class CommentMessageController {
    @Autowired
    private CommentMessageRepository commentMessageRepository;

    @QueryMapping
    public List<CommentMessage> getCommentDetails() {
        return commentMessageRepository.findAll();
    }
}
