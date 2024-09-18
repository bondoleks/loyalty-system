package ua.bondoleks.loyaltySystem.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bondoleks.loyaltySystem.entity.LSUser;
import ua.bondoleks.loyaltySystem.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<LSUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new LSUser());
        return "create_user";
    }

    @PostMapping
    public String createUser(@ModelAttribute LSUser user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{phoneNumber}")
    public String editUserForm(@PathVariable String phoneNumber, Model model) {
        LSUser user = userService.getUserByPhoneNumber(phoneNumber);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/update/{phoneNumber}")
    public String updateUser(@PathVariable String phoneNumber, @ModelAttribute LSUser userDetails) {
        userService.updateUser(phoneNumber, userDetails);
        return "redirect:/users";
    }

    @GetMapping("/delete/{phoneNumber}")
    public String deleteUser(@PathVariable String phoneNumber) {
        userService.deleteUser(phoneNumber);
        return "redirect:/users";
    }
}
