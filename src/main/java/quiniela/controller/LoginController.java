package quiniela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiniela.model.Player;
import quiniela.model.Team;
import quiniela.model.form.LoginForm;
import quiniela.model.views.TokenAbleView;
import quiniela.model.views.ViewPlayerInfo;
import quiniela.service.LoginService;
import quiniela.service.PlayerService;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    PlayerService playerService;


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public TokenAbleView login(@RequestBody LoginForm form) {
        return new TokenAbleView().setToken(loginService.login(form.getUsername(),form.getPassword()));
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Boolean logout(@RequestBody LoginForm form) {
        return loginService.logout(form.getToken());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ViewPlayerInfo signup(@RequestBody LoginForm form) {
        Player p = playerService.createPlayer(form.getUsername(),form.getPassword());
        return new ViewPlayerInfo(p).setToken(loginService.login(form.getUsername(), form.getPassword()));
    }

}
