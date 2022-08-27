package user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import utilities.UserUtilities;
import validators.ChangePasswordValidator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.Locale;

@Controller
public class ProfilControler {

    @Autowired
    private UserService userService;
    private MessageSource messageSource;

    @GET
    @RequestMapping(value = "/profil")
    public String showUserProfilePage(Model model) {
        String username = UserUtilities.getLoggedUser();


        User user = userService.findUserByEmail(username);

        int nrRoli = user.getRoles().iterator().next().getId();
        user.setNrRoli(nrRoli);
        model.addAttribute("user", user);

        return "profil";
    }

    @GET
    @RequestMapping(value = "/updatepassword")
    public String editUserPassword(Model model) {
        String username = UserUtilities.getLoggedUser();
        User user = userService.findUserByEmail(username);
        model.addAttribute("user", user);
        return "editpassword";
    }

    @POST
    @RequestMapping(value = "/updatepass")
    public String changeUserPassword(User user, BindingResult result, Model model, Locale locale) {
        String returnPage = null;
        new ChangePasswordValidator().validate(user, result);
        new ChangePasswordValidator().checkPasswords(user.getNewPassword(), result);

        if (result.hasErrors()) {
            returnPage = "editpassword";
        } else {
            userService.updateUserPassword(user.getNewPassword(), user.getEmail());
            returnPage = "editpassword";
            model.addAttribute("message", messageSource.getMessage("passwordChange.success", null, locale));
        }
        return returnPage;
    }
}




























