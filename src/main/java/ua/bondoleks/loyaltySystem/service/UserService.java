package ua.bondoleks.loyaltySystem.service;

import org.springframework.stereotype.Service;
import ua.bondoleks.loyaltySystem.entity.LUser;
import ua.bondoleks.loyaltySystem.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LUser> getAllUsers() {
        return userRepository.findAll();
    }

    public LUser getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void createUser(LUser user) {
        userRepository.save(user);
    }

    public void updateUser(String phoneNumber, LUser userDetails) {
        LUser user = getUserByPhoneNumber(phoneNumber);
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setBalance(userDetails.getBalance());
        userRepository.save(user);
    }

    public void deleteUser(String phoneNumber) {
        userRepository.deleteByPhoneNumber(phoneNumber);
    }
}