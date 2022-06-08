package com.tms.common.security.service.impl;

import com.tms.common.annotation.Approver;
import com.tms.common.annotation.ChangeRequest;
import com.tms.common.mapper.OrikaBeanMapper;
import com.tms.common.security.service.UserService;
import com.tms.common.changeRequestDomain.enumTypes.OperationType;
import com.tms.common.domain.UserEntity;
import com.tms.common.domain.dto.UserDTO;
import com.tms.common.repository.UserRepository;
import lombok.NonNull;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Approver(repository = UserRepository.class, domainClass = UserEntity.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrikaBeanMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDTO getUserById(@NonNull Long id) {
        return mapper.map(userRepository.getById(id), UserDTO.class);
    }

    @Override
    @Transactional
    @ChangeRequest(operationType = OperationType.CREATE)
    public void registerUser(@NonNull UserDTO user) {
        userRepository.save(mapper.map(user, UserEntity.class));
    }

    @Override
    @ChangeRequest(operationType = OperationType.DELETE)
    public void deleteById(Long id) {
        UserEntity userEntity = userRepository.getById(id);
        userRepository.delete(userEntity);
    }

    @Override
    @ChangeRequest(operationType = OperationType.READ)
    public UserDTO findByEmail(String email) {
        return mapper.map(userRepository.findByEmail(email), UserDTO.class);
    }

    @Override
    @ChangeRequest(operationType = OperationType.READ)
    public List<UserDTO> findAll(boolean isActive) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("activeFilter").setParameter("isActive", isActive);
        List<UserEntity> userEntityList = userRepository.findAll();
        session.disableFilter("activeFilter");
        return mapper.mapAsList(userEntityList, UserDTO.class);
    }


    @Override
    public void update(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(mapper.map(userDTO, UserEntity.class));
    }
}
