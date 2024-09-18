package ua.bondoleks.loyaltySystem.service;

import ua.bondoleks.loyaltySystem.entity.LSUser;

import java.util.List;

public interface UserService {
    List<LSUser> getAllUsers();
    LSUser getUserByPhoneNumber(String phoneNumber);
    void createUser(LSUser user);
    void updateUser(String phoneNumber, LSUser userDetails);
    void deleteUser(String phoneNumber);
}
