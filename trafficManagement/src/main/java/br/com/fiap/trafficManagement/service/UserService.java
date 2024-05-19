package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.dto.UserExhibitionDto;
import br.com.fiap.trafficManagement.dto.UserInsertDto;
import br.com.fiap.trafficManagement.exception.NotFoundException;
import br.com.fiap.trafficManagement.model.User;
import br.com.fiap.trafficManagement.model.UserRole;
import br.com.fiap.trafficManagement.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserExhibitionDto insertUser(UserInsertDto userInsertDto) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(userInsertDto.password());
        User user = new User();
        BeanUtils.copyProperties(userInsertDto, user);
        user.setPassword(encryptedPassword);
        user.setUserRole(userInsertDto.userRole() != null ? userInsertDto.userRole() : UserRole.USER);
        return new UserExhibitionDto(userRepository.save(user));
    }

    public UserExhibitionDto queryById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new UserExhibitionDto(userOptional.get());
        } else {
            throw new NotFoundException("User ID not found!");
        }
    }

    public Page<UserExhibitionDto> queryAllUsers(Pageable page) {
        return userRepository
                .findAll(page)
                .map(UserExhibitionDto::new);
    }

    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new RuntimeException("User ID not found!");
        }
    }

}
