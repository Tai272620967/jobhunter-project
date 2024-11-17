package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
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

    public boolean isEmailExist(String email) {
        return this.userRepositoty.existsByEmail(email);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setKataFirstName(user.getKataFirstName());
        res.setKataLastName(user.getKataLastName());
        res.setAddress1(user.getAddress1());
        res.setAddress2(user.getAddress2());
        res.setAddress3(user.getAddress3());
        res.setAddress4(user.getAddress4());
        res.setPhone(user.getPhone());
        res.setBirthday(user.getBirthday());
        res.setGender(user.getGender());
        res.setCreatedAt(user.getCreatedAt());
        return res;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setKataFirstName(user.getKataFirstName());
        res.setKataLastName(user.getKataLastName());
        res.setAddress1(user.getAddress1());
        res.setAddress2(user.getAddress2());
        res.setAddress3(user.getAddress3());
        res.setAddress4(user.getAddress4());
        res.setPhone(user.getPhone());
        res.setBirthday(user.getBirthday());
        res.setGender(user.getGender());
        res.setCreatedAt(user.getCreatedAt());
        return res;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setKataFirstName(user.getKataFirstName());
        res.setKataLastName(user.getKataLastName());
        res.setAddress1(user.getAddress1());
        res.setAddress2(user.getAddress2());
        res.setAddress3(user.getAddress3());
        res.setAddress4(user.getAddress4());
        res.setPhone(user.getPhone());
        res.setBirthday(user.getBirthday());
        res.setGender(user.getGender());
        res.setCreatedAt(user.getCreatedAt());
        return res;
    }

    public ResultPaginationDTO handleGetAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepositoty.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageUser.getNumber() + 1);
        mt.setPageSize(pageUser.getSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);

        // remove sensitive data
        List<ResUserDTO> listUser = pageUser.getContent()
                .stream().map(item -> new ResUserDTO())
                .collect(Collectors.toList());

        rs.setResult(listUser);

        return rs;
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.handleGetUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setAddress1(reqUser.getAddress1());
            currentUser.setAddress2(reqUser.getAddress2());
            currentUser.setAddress3(reqUser.getAddress3());
            currentUser.setAddress4(reqUser.getAddress4());
            currentUser.setGender(reqUser.getGender());
            currentUser.setBirthday(reqUser.getBirthday());
            currentUser.setFirstName(reqUser.getFirstName());
            currentUser.setLastName(reqUser.getLastName());
            currentUser.setKataFirstName(reqUser.getKataFirstName());
            currentUser.setKataLastName(reqUser.getKataLastName());

            // update
            currentUser = this.userRepositoty.save(currentUser);
        }
        return currentUser;
    }

    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepositoty.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepositoty.findByRefreshTokenAndEmail(token, email);
    }
}
