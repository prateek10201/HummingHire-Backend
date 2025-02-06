package org.humminghire.backend.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.humminghire.backend.aop.exceptions.FileProcessingException;
import org.humminghire.backend.aop.exceptions.ResourceAlreadyExistsException;
import org.humminghire.backend.aop.exceptions.ResourceNotFoundException;
import org.humminghire.backend.dto.UserUpdateDTO;
import org.humminghire.backend.entity.User;
import org.humminghire.backend.entity.UserProfile;
import org.humminghire.backend.dto.UserProfileUpdateDTO;
import org.humminghire.backend.repository.UserProfileRepository;
import org.humminghire.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private final EntityManager entityManager;

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }

        User savedUser = userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(savedUser);
        userProfileRepository.save(userProfile);

        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(String email, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        user.setName(userUpdateDTO.getName());
        user.setPhone(userUpdateDTO.getPhone());
        user.setLocation(userUpdateDTO.getLocation());
        user.setDob(userUpdateDTO.getDob());

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::excludeBlobData)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    private User excludeBlobData(User user) {
        if (user.getProfile() != null) {
            user.getProfile().setAvatar(null);
            user.getProfile().setResume(null);
        }
        return user;
    }

    @Transactional
    public void deleteUser(String email) {
        User user = getUserByEmail(email);

        for (User connectedUser : user.getConnections()) {
            connectedUser.getConnections().remove(user);
            userRepository.save(connectedUser);
        }

        user.getConnections().clear();

        userRepository.delete(user);
    }

    @Transactional
    public UserProfile updateUserProfile(String email,
                                         UserProfileUpdateDTO profileDTO,
                                         MultipartFile avatar,
                                         MultipartFile resume) {
        User user = getUserByEmail(email);
        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user: " + email));

        try {
            System.out.println("Avatar: " + avatar.getOriginalFilename());
            if (avatar != null) {
                profile.setAvatar(avatar.getBytes());
                profile.setAvatarType(avatar.getContentType());
                profile.setAvatarName(avatar.getOriginalFilename());
            }
            if (resume != null) {
                profile.setResume(resume.getBytes());
                profile.setResumeType(resume.getContentType());
                profile.setResumeName(resume.getOriginalFilename());
            }

            // Update fields from DTO
            profile.setCurrentWork(profileDTO.getCurrentWork());
            profile.setPreviousWorks(profileDTO.getPreviousWorks());
            profile.setCertifications(profileDTO.getCertifications());
            profile.setEducation(profileDTO.getEducation());
            profile.setProjects(profileDTO.getProjects());
            profile.setSocialLinks(profileDTO.getSocialLinks());
            profile.setLanguages(profileDTO.getLanguages());
            profile.setSkills(profileDTO.getSkills());


            return userProfileRepository.save(profile);
        } catch (IOException e) {
            throw new FileProcessingException("Error processing file upload", e);
        }
    }

    @Transactional
    public UserProfile getUserProfile(String email) {
        User user = getUserByEmail(email);
        return userProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user: " + email));
    }

    @Transactional
    public User addConnection(String email, String connectionEmail) {
        User user = getUserByEmail(email);
        User connection = getUserByEmail(connectionEmail);

        if (!user.getConnections().contains(connection)) {
            user.getConnections().add(connection);
            userRepository.save(user);
        }

        return user;
    }

    @Transactional
    public User removeConnection(String email, String connectionEmail) {
        User user = getUserByEmail(email);
        User connection = getUserByEmail(connectionEmail);

        user.getConnections().remove(connection);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getUserConnections(String email) {
        User user = getUserByEmail(email);
        // Initialize the connections collection
        List<User> connections = new ArrayList<>(user.getConnections());
        return connections;
    }

}
