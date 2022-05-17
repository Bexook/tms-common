package com.tms.common.security.service;

import com.tms.common.domain.UserEntity;
import com.tms.common.domain.dto.UserDTO;
import lombok.NonNull;

import java.util.List;


public interface UserService {


    UserDTO getUserById(@NonNull Long id);

    UserEntity registerUser(@NonNull UserEntity userEntity);

    void deleteById(Long id);

    UserDTO findByEmail(String email);

    List<UserDTO> findAll(boolean isActive);

    void update(UserDTO userDTO);
}
