package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.dto.UserExhibitionDto;
import br.com.fiap.trafficManagement.dto.UserInsertDto;
import br.com.fiap.trafficManagement.exception.TrafficLightNotFoundException;
import br.com.fiap.trafficManagement.model.User;
import br.com.fiap.trafficManagement.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

        return new UserExhibitionDto(userRepository.save(user));
    }

    public UserExhibitionDto queryById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return new UserExhibitionDto(userOptional.get());
        } else {
            throw new TrafficLightNotFoundException("User ID not found!");
        }
    }
}
