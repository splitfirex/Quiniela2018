package quiniela.controller2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import quiniela.model.Player;
import quiniela.model.form.LoginForm;
import quiniela.model.views.TokenAbleView;
import quiniela.model.views.ViewPlayerInfo;
import quiniela.service2.PlayerService;

@Controller
@RequestMapping("/login")
public class LoginController {


    @Autowired
    PlayerService playerService;

    @Value("${general.key}")
    String generalKey;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public TokenAbleView login(@RequestBody LoginForm form) {
        return new TokenAbleView().setToken(playerService.login(form.getUsername(),form.getPassword()));
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public Boolean logout(@RequestBody LoginForm form) {
        return playerService.logout(form.getToken());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ViewPlayerInfo signup(@RequestBody LoginForm form) {
        Player p = playerService.createPlayer(form.getUsername(),form.getPassword());
        return new ViewPlayerInfo(p).setToken(playerService.login(form.getUsername(), form.getPassword()));
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    @ResponseBody
    public void resetUser(@RequestBody LoginForm form) {
        if (playerService.encode(form.getPassword()).equals(generalKey)) {
            playerService.resetPassword(form.getUsername());
        }
    }

}
