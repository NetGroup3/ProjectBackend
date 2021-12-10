package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.models.dto.UserDto;
import com.example.NetProjectBackend.models.dto.UserListRequest;
import com.example.NetProjectBackend.models.Verify;
import com.example.NetProjectBackend.models.dto.PasswordChangeRequestDto;
//import com.example.NetProjectBackend.models.dto.UserDto;
import com.example.NetProjectBackend.models.dto.UserImageDto;
import com.example.NetProjectBackend.models.dto.*;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.models.enums.EStatus;
import com.example.NetProjectBackend.service.Mail;
import com.example.NetProjectBackend.service.Paginator;
import com.example.NetProjectBackend.service.password.HashPassword;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final Mail mail;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private Paginator paginator;

    /**
     * Sign Up
     * @return
     */
    public int create(User user, String role) {

        if (userDao.readByEmail(user.getEmail()) != null) {
            return 0;
        }
        user.setTimestamp(OffsetDateTime.now());
        user.setStatus(EStatus.NOT_VERIFY.name());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int id = userDao.create(user);
        if (id > 0) {
            mail.confirmationCode(user.getEmail());
            return id;
        }
        return 0;
    }

    /**
     * Recovery Password
     * @return
     */
    public boolean recovery(String email) {
        log.info(email);
        if (userDao.readByEmail(email) == null) { //проверка на ниличие в бд
            return false;
        } else {
            if (!mail.recoveryCode(email))
                return false;
        }
        return true;
    }

    /**
     * Code processing
     * @return
     */
    public boolean code(String param) {
        Verify verify = mail.readByCode(param);
        if (verify == null)
            return false;
        User user = userDao.readById(verify.getUserId());

        if (Objects.equals(user.getStatus(), EStatus.ACTIVE.getAuthority())) {
            String newPassword = randomPassword();
            if (mail.sendNewPassword(newPassword, user, verify)) {
                PasswordChangeRequestDto change = new PasswordChangeRequestDto(user.getId(), newPassword, newPassword);
                changePassword(change);
            } else {
                mail.confirmationCode(user.getEmail());
            }
        } else {
            if (mail.checkData(verify)) {
                changeStatus(EStatus.ACTIVE, user.getId());
            } else {
                mail.confirmationCode(user.getEmail());
            }

        }
        mail.deleteCode(verify.getUserId());
        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = userDao.readByEmail(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), true, true, true, true, new HashSet<>());
    }

    private String randomPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public void checkOldPassword(PasswordChangeRequestDto passwordCR) throws Exception {
        User user = userDao.readById(passwordCR.getUserId());
        if (!passwordEncoder.matches(passwordCR.getOldPassword(), user.getPassword())) {
            throw new Exception("Incorrect password");
        }
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void updatePassword(PasswordChangeRequestDto passwordCR) throws Exception {
        checkOldPassword(passwordCR);
        String hashedPassword = hashPassword(passwordCR.getPassword());
        userDao.updatePassword(hashedPassword, passwordCR.getUserId());
    }

    public void changeStatus(EStatus status, int id) {
        userDao.changeStatus(status, id);
    }

    public void changePassword(PasswordChangeRequestDto passwordCR) {
        String hashedPassword = hashPassword(passwordCR.getPassword());
        userDao.updatePassword(hashedPassword, passwordCR.getUserId());
    }

    /*
    public List<User> getAll() {
        return userDao.getAll();
    }
    */

    public Paginator.PaginatedResponse getAllSuitable(UserListRequest req) {
        List<User> list = userDao.getAllSuitable(req);
        Paginator.PaginatedResponse res = paginator.paginate(list, req.getPageNo(), req.getPerPage());
        res.setList(UserDto.transformList(res.getList()));
        return res;
    }

    public UserDto update(User user) {
        if (userDao.readById(user.getId()) == null) {
            return null;
        }
        userDao.update(user);     //updates first and last names
        return UserDto.transform(userDao.readById(user.getId()));
    }

    public UserDto delete(int id) {
        UserDto user = UserDto.transform(userDao.readById(id));
        if (user == null) {
            return null;
        }
        userDao.delete(id);
        return user;
    }

    public UserDto readById(int id) {
        return UserDto.transform(userDao.readById(id));
    }

    public UserDto readByEmail(String email) {
        return UserDto.transform(userDao.readByEmail(email));
    }

    /*
    public User readByName(String name) {
            return userDao.readByName(name);
    }
    */

    public void updateUserImage(UserImageDto obj) {
        User user = userDao.readById(obj.getId());
        if (user != null) {
            userDao.updateImageId(obj.getId(), obj.getImageId());
        }
    }

    public boolean createModerator(User user) {
        if (userDao.readByEmail(user.getEmail()) != null) {
            return false;
        }

        user.setRole(ERole.MODERATOR.getAuthority());
        user.setTimestamp(OffsetDateTime.now());
        user.setStatus(EStatus.ACTIVE.getAuthority());
        String password = randomPassword();
        user.setPassword(passwordEncoder.encode(password));

        if (userDao.create(user) > 0) {
            mail.sendModeratorPassword(password, user.getEmail());
            return true;
        }
        return false;
    }

    /*
    public List<UserDto> readPage(int limit, int offset, String role) {
        if (limit > 100) limit = 100;
        return userDao.readPage(limit, offset, role);
    }
    */

    public List<UserSearchDto> searchUsers(String name){
        return userDao.readUsers(name);
    }

    public UserProfileDto searchUser(int id){
        return userDao.readUser(id);
    }
}