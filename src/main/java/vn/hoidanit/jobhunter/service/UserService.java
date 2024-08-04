package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepositoty;

@Service
public class UserService {
    
    private final UserRepositoty userRepositoty;

    public UserService(UserRepositoty userRepositoty) {
        this.userRepositoty = userRepositoty;
    }

    public User handleCreateUser(User user) {
        return this.userRepositoty.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepositoty.deleteById(id);
    }

    public User handleGetUserById(long id) {
        Optional<User> userOptional = this.userRepositoty.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepositoty.findByEmail(username);
    }

    public List<User> handleGetAllUser() {
        return this.userRepositoty.findAll();
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.handleGetUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setName(reqUser.getName());
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setPassword(reqUser.getPassword());

            currentUser = this.userRepositoty.save(currentUser);
        }
        return currentUser;
    }
}
