package sk.tuke.gamestudio.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.CommentService;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    private User loggedUser;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
        if ("heslo".equals(password)) {
            loggedUser = new User(login);
            return "redirect:/Picture Sliding Puzzle";
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    @RequestMapping("/addComment")
    public String addComment(String text) {

            commentService.addComment(new Comment(loggedUser.getLogin(), "Picture Sliding Puzzle", text, new Date()));
        return "redirect:/Picture Sliding Puzzle";
    }


    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

}
