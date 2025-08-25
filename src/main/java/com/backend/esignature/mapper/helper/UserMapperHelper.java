package com.backend.esignature.mapper.helper;

import com.backend.esignature.entities.Users;
import com.backend.esignature.repositories.user.UserRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapperHelper {

    @Autowired
    private UserRepository userRepository;

    @Named("mapUserById")
    public Users mapUserById(String userId) {
        if (userId == null) return null;
        return userRepository.findById(userId)
                .orElse(null); // bạn có thể throw exception nếu cần
    }
}
