package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.exeptions.IncorrectPasswordException;
import com.example.NetProjectBackend.models.Verify;
import com.example.NetProjectBackend.models.dto.*;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.enums.ERole;
import com.example.NetProjectBackend.models.enums.EStatus;
import com.example.NetProjectBackend.service.Mail;
import com.example.NetProjectBackend.service.UserService;
import com.example.NetProjectBackend.service.UserSessionService;
import com.example.NetProjectBackend.service.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserServiceImpl implements UserDetailsService, UserService {

    private final Mail mail;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private UserSessionService userSessionService;


    @Override
    public int create(User user, String role) {
        user.setTimestamp(OffsetDateTime.now());
        user.setStatus(EStatus.NOT_VERIFY.name());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        int id = userDao.create(user);
        if (id > 0) {
            mail.confirmationCode(user.getEmail(), false);
            return id;
        }
        return 0;
    }
    

    @Override
    public boolean recovery(String email) {
        log.info(email);
        if (userDao.readByEmail(email) == null) {
            return false;
        } else {
            if (!mail.recoveryCode(email))
                return false;
        }
        return true;
    }


    @Override
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
                mail.confirmationCode(user.getEmail(), false);
            }
        } else {
            if (mail.checkData(verify)) {
                changeStatus(EStatus.ACTIVE, user.getId());
            } else {
                mail.confirmationCode(user.getEmail(), false);
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
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void checkOldPassword(PasswordChangeRequestDto passwordCR) {
        User user = userDao.readById(passwordCR.getUserId());
        if (!passwordEncoder.matches(passwordCR.getOldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void updatePassword(PasswordChangeRequestDto passwordCR) {
        passwordCR.setUserId(userSessionService.getUserIdFromSession());
        checkOldPassword(passwordCR);
        String hashedPassword = hashPassword(passwordCR.getPassword());
        userDao.updatePassword(hashedPassword, passwordCR.getUserId());
    }

    @Override
    public void changeStatus(EStatus status, int id) {
        userDao.changeStatus(status, id);
    }

    private void changePassword(PasswordChangeRequestDto passwordCR) {
        String hashedPassword = hashPassword(passwordCR.getPassword());
        userDao.updatePassword(hashedPassword, passwordCR.getUserId());
    }
    
    public List<UserPaginatedDto> getAllSuitable(UserListRequest req) {
        List<UserPaginated> list = userDao.getAllSuitable(req);
        for (UserPaginated user : list) {
            user.setPagesTotal((int)Math.ceil((float)user.getPagesTotal() / req.getPerPage()));
        }
        List<UserPaginatedDto> res = UserPaginatedDto.transformList(list);
        return res;
    }

    @Override
    public UserDto update(User user) {
        userDao.update(user);
        return UserDto.transform(userDao.readById(user.getId()));
    }

    @Override
    public void updateFull(User user) {
        userDao.updateFull(user);
    }

    @Override
    public UserDto delete(int id) {
        UserDto user = UserDto.transform(userDao.readById(id));
        if (user == null) {
            return null;
        }
        userDao.delete(id);
        return user;
    }

    @Override
    public UserDto readById(int id) {
        return UserDto.transform(userDao.readById(id));
    }

    @Override
    public UserDto readByEmail(String email) {
        return UserDto.transform(userDao.readByEmail(email));
    }

    @Override
    public void updateUserImage(UserImageDto obj) {
        User user = userDao.readById(obj.getId());
        if (user != null) {
            userDao.updateImageId(obj.getId(), obj.getImageId());
        }
    }

    @Override
    public void createModerator(User user) {
        user.setRole(ERole.MODERATOR.getAuthority());
        user.setTimestamp(OffsetDateTime.now());
        user.setStatus(EStatus.ACTIVE.getAuthority());
        String password = randomPassword();
        user.setPassword(passwordEncoder.encode(password));

        if (userDao.create(user) > 0) {
            mail.sendModeratorPassword(password, user.getEmail());
            return;
        }
        return;
    }

    @Override
    public JwtResponseDto authentication(LoginRequestDto loginRequestDto){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtResponseDto(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstname(),
                userDetails.getLastname(),
                userDetails.getTimestamp(),
                userDetails.getImageId(),
                userDetails.getStatus(),
                userDetails.getRole()
        );
    }

    @Override
    public List<UserSearchDto> searchUsers(String name) {
        return userDao.readUsers(name);
    }

    @Override
    public UserProfileDto searchUser(int id) {
        boolean checkUser = true;
        if (id == userSessionService.getUserIdFromSession()){
        checkUser = false;
        }
        return userDao.readUser(id, checkUser);
    }
}