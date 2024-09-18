package ua.bondoleks.loyaltySystem.service.impl;

import org.springframework.stereotype.Service;
import ua.bondoleks.loyaltySystem.entity.LSUser;
import ua.bondoleks.loyaltySystem.exception.LSUserNotFoundException;
import ua.bondoleks.loyaltySystem.repository.UserRepository;
import ua.bondoleks.loyaltySystem.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<LSUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public LSUser getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new LSUserNotFoundException("User with phone number " + phoneNumber + " not found"));
    }

    @Override
    public void createUser(LSUser user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(String phoneNumber, LSUser userDetails) {
        LSUser user = getUserByPhoneNumber(phoneNumber);
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setBalance(userDetails.getBalance());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String phoneNumber) {
        if (!userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new LSUserNotFoundException("User with phone number " + phoneNumber + " not found");
        }
        userRepository.deleteByPhoneNumber(phoneNumber);
    }
}