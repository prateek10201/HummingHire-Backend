package org.humminghire.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.humminghire.backend.aop.exceptions.FileProcessingException;
import org.humminghire.backend.dto.UserUpdateDTO;
import org.humminghire.backend.entity.User;
import org.humminghire.backend.entity.UserProfile;
import org.humminghire.backend.dto.UserProfileUpdateDTO;
import org.humminghire.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;


    // User

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/{email}/profile/avatar")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable String email) {
        UserProfile profile = userService.getUserProfile(email);
        if (profile.getAvatar() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(profile.getAvatarType()))
                    .body(profile.getAvatar());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{email}/profile/resume")
    public ResponseEntity<byte[]> getUserResume(@PathVariable String email) {
        UserProfile profile = userService.getUserProfile(email);
        if (profile.getResume() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(profile.getResumeType()))
                    .body(profile.getResume());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(email, userUpdateDTO));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    // UserProfile

    @GetMapping("/{email}/profile")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserProfile(email));
    }

    @PutMapping("/{email}/profile")
    public ResponseEntity<UserProfile> updateUserProfile(
            @PathVariable String email,
            @RequestPart(value = "profile") String profileJson,
            @RequestPart(required = false) MultipartFile avatar,
            @RequestPart(required = false) MultipartFile resume) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("MM-dd-yyyy"));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            System.out.println("Received Profile JSON: " + profileJson); // Debug print
            System.out.println("Received Profile Avatar: " + avatar);
            System.out.println("Received Profile Resume: " + resume);

            UserProfileUpdateDTO profileDTO = mapper.readValue(profileJson, UserProfileUpdateDTO.class);
            return ResponseEntity.ok(userService.updateUserProfile(email, profileDTO, avatar, resume));
        } catch (JsonProcessingException e) {
            System.err.println("JSON Processing Error: " + e.getMessage()); // Debug print
            e.printStackTrace(); // Print full stack trace
            throw new FileProcessingException("Error processing profile JSON: " + e.getMessage(), e);
        }
    }

    @PostMapping("/{email}/connections/{connectionEmail}")
    public ResponseEntity<User> addConnection(
            @PathVariable String email,
            @PathVariable String connectionEmail) {
        return ResponseEntity.ok(userService.addConnection(email, connectionEmail));
    }

    @DeleteMapping("/{email}/connections/{connectionEmail}")
    public ResponseEntity<User> removeConnection(
            @PathVariable String email,
            @PathVariable String connectionEmail) {
        return ResponseEntity.ok(userService.removeConnection(email, connectionEmail));
    }

    @GetMapping("/{email}/connections")
    public ResponseEntity<List<User>> getUserConnections(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserConnections(email));
    }
}
