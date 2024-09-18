package ua.bondoleks.loyaltySystem.service;

import org.springframework.stereotype.Service;
import ua.bondoleks.loyaltySystem.entity.LSUser;
import ua.bondoleks.loyaltySystem.exception.LSUserNotFoundException;
import ua.bondoleks.loyaltySystem.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LSUser> getAllUsers() {
        return userRepository.findAll();
    }

    public LSUser getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new LSUserNotFoundException("User with phone number " + phoneNumber + " not found"));
    }

    public void createUser(LSUser user) {
        userRepository.save(user);
    }

    public void updateUser(String phoneNumber, LSUser userDetails) {
        LSUser user = getUserByPhoneNumber(phoneNumber);
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setBalance(userDetails.getBalance());
        userRepository.save(user);
    }

    public void deleteUser(String phoneNumber) {
        if (!userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new LSUserNotFoundException("User with phone number " + phoneNumber + " not found");
        }
        userRepository.deleteByPhoneNumber(phoneNumber);
    }
}