package com.backend.userservice.services;

import com.backend.userservice.dto.request.UserRequest;
import com.backend.userservice.dto.response.UserResponse;
import com.backend.userservice.entities.Users;
import com.backend.userservice.mapper.UserMapper;
import com.backend.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserResponse createUser(UserRequest request){
        try{
            if(request == null ){
                throw new IllegalArgumentException("User doesn't exist ");
            }
            Users user = Users.builder()
                    .email(request.getEmail())
                    .fullName(request.getFullName())
                    .role(request.getRole())
                    .build();
            return mapper.entityToUserResponse(userRepository.save(user));
        }catch (Exception e){
            throw new IllegalArgumentException("Create user was failed");
        }
    }

    public UserResponse updateUser(UserRequest request){
        try{
            if(request == null ){
                throw new IllegalArgumentException("User doesn't exist ");
            }
            Users entity = userRepository.findByEmail((request.getEmail())).getFirst();

            return mapper.entityToUserResponse(userRepository.save(entity));
        }catch (Exception e){
            throw new IllegalArgumentException("Update User was failed");
        }
    }

    public void deleteUser(String email){
        try{
            if(email == null){
                throw new IllegalArgumentException("User doesn't exist ");
            }
            userRepository.deleteByEmail(email);
        }catch (Exception e){
            throw new IllegalArgumentException("Delete User was failed");
        }
    }

    public UserResponse getUserByEmail(String email){
        try{
            if(email == null){
                throw new IllegalArgumentException("Email is null");
            }
            Users user = userRepository.findByEmail(email).getFirst();

            if(user == null){
                throw new IllegalArgumentException("User doesn't exist");
            }

            return mapper.entityToUserResponse(user);
        }catch (Exception e){
            throw new IllegalArgumentException("Get User was failed");
        }
    }

    public UserResponse getUserById(String id){
        try{
            if(id == null){
                throw new IllegalArgumentException("id is null");
            }
            Users user = userRepository.findByEmail(id).getFirst();
            if(user == null){
                throw new IllegalArgumentException("Get User was failed");
            }
            return mapper.entityToUserResponse(user);
        }catch (Exception e){
            throw new IllegalArgumentException("Get User was failed");
        }
    }

    public List<UserResponse> getAllUsers(){
        try{
            List<Users> entities =  userRepository.findAll();
            if(entities.isEmpty()){
                throw new IllegalArgumentException("Users list is empty");
            }
            List<UserResponse> userResponses = new ArrayList<>();
            for(Users user : entities){
                userResponses.add(mapper.entityToUserResponse(user));
            }
            return userResponses;
        }catch (Exception e){
            throw new IllegalArgumentException("Get User was failed");
        }
    }

}
