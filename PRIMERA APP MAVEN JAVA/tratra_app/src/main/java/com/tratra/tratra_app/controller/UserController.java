package com.tratra.tratra_app.controller;

import java.util.Base64;
import java.util.List;

import com.tratra.tratra_app.entity.User;
import com.tratra.tratra_app.entity.Activity;
import com.tratra.tratra_app.service.UserService;
import com.tratra.tratra_app.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    // Carpeta donde guardarás los avatares (ajusta la ruta según tu proyecto)
    private static final String AVATAR_UPLOAD_DIR = "uploads/avatars/";

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String fullName,
                               Model model) {
        if (userService.findByUsername(username).isPresent()) {
            model.addAttribute("error", "El usuario ya existe.");
            return "register";
        }

        userService.registerUser(username, password, fullName);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String showHome(Model model, Authentication authentication) {
        String username = authentication.getName();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("avatarPath", user.getAvatarPath());  // <-- Añadido para mostrar avatar

        List<Activity> activities = activityService.findAllByUser(user);
        model.addAttribute("activities", activities);

        return "home";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "edit_profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam(required = false) String password,
                                @RequestParam(required = false) String croppedImage,  // base64 del avatar
                                Authentication authentication,
                                Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        user.setFullName(fullName);

        if (password != null && !password.isBlank()) {
            userService.updatePassword(user, password);
        }

        if (croppedImage != null && !croppedImage.isBlank()) {
            try {
                String base64Image = croppedImage.split(",")[1];
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                String avatarFilename = userService.saveUserAvatar(user, imageBytes);

                if (avatarFilename != null) {
                    user.setAvatarPath("/" + AVATAR_UPLOAD_DIR + avatarFilename);  // <-- importante prefijar con "/"
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "Hubo un problema al guardar el avatar.");
            }
        }

        userService.save(user);

        model.addAttribute("user", user);
        model.addAttribute("success", "Perfil actualizado con éxito.");

        return "edit_profile";
    }

    @GetMapping("/some-path")
    public String showHomePage(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "home";
    }

    // Método auxiliar para obtener extensión del archivo
    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return filename.substring(dotIndex + 1);
    }
}
