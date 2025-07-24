package com.tratra.tratra_app.service;

import com.tratra.tratra_app.entity.User;
import com.tratra.tratra_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String AVATAR_UPLOAD_DIR = "uploads/avatars/";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(String username, String rawPassword, String fullName) {
        System.out.println(">>> [DEBUG] Intentando registrar usuario: " + username);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setFullName(fullName);

        User savedUser = userRepository.save(user);

        System.out.println(">>> [DEBUG] Usuario guardado con ID: " + savedUser.getId());

        return savedUser;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void updatePassword(User user, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPasswordHash(encodedPassword);
        userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Guarda la imagen de avatar recibida como bytes en disco y devuelve el nombre del archivo guardado.
     *
     * @param user       El usuario al que pertenece el avatar.
     * @param imageBytes Array de bytes con la imagen (ya decodificada).
     * @return Nombre del archivo guardado o null si hubo error.
     */
    public String saveUserAvatar(User user, byte[] imageBytes) {
        try {
            Path uploadPath = Paths.get(AVATAR_UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = "avatar_" + user.getId() + "_" + UUID.randomUUID() + ".png";
            Path filePath = uploadPath.resolve(filename);

            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(imageBytes);
            }

            return filename;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
