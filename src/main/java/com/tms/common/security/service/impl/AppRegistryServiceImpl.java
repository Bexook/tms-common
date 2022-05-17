package com.tms.common.security.service.impl;

import com.tms.common.domain.ApplicationDetailsEntity;
import com.tms.common.domain.dto.ApplicationDetailsDTO;
import com.tms.common.domain.dto.ApplicationLoginDTO;
import com.tms.common.domain.enumTypes.ApplicationState;
import com.tms.common.mapper.OrikaBeanMapper;
import com.tms.common.repository.AppRegistryRepository;
import com.tms.common.security.service.AppRegistryService;
import com.tms.common.util.AuthorizationUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AppRegistryServiceImpl implements AppRegistryService {

    @Autowired
    private AppRegistryRepository appRegistryRepository;
    @Autowired
    private OrikaBeanMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${system.api.app-secret-prefix:}")
    private String appSecretPrefix;
    @Value("${system.api.app-key-header-value:}")
    private String headerApplicationKey;
    @Value("${system.api.app-secret-header-value:}")
    private String headerApplicationSecret;

    @Override
    public boolean validateApplication(final ApplicationLoginDTO login) {
        final ApplicationDetailsDTO appDetails = mapper.map(appRegistryRepository.getByAppKey(login.getAppKey()), ApplicationDetailsDTO.class);
        if (passwordEncoder.matches(login.getAppSecret(), appDetails.getAppSecret())) {
            return true;
        }
        return false;
    }

    @Override
    public ApplicationDetailsDTO registerApplication(String appRoute, String appName) {
        final ApplicationDetailsEntity detailsEntity = new ApplicationDetailsEntity();
        detailsEntity.setActive(false);
        detailsEntity.setName(appName);
        detailsEntity.setRoute(appRoute);
        detailsEntity.setAppKey(generateAppKey(appName));
        detailsEntity.setAppSecret(passwordEncoder.encode(generateAppSecret(appName)));
        detailsEntity.setCreated(LocalDate.now());
        detailsEntity.setCreatedBy(AuthorizationUtils.getCurrentUsername());
        return mapper.map(appRegistryRepository.save(detailsEntity), ApplicationDetailsDTO.class);
    }

    @Override
    public void deleteApplication(String appKey) {
        appRegistryRepository.deleteByAppKey(appKey);

    }

    @Override
    public List<ApplicationDetailsDTO> allApplicationFilteredByActivity(final ApplicationState applicationState) {
        switch (applicationState) {
            case ALL:
                return mapper.mapAsList(appRegistryRepository.findAll(), ApplicationDetailsDTO.class);

            case ACTIVE:
                return mapper.mapAsList(appRegistryRepository.findAll(), ApplicationDetailsDTO.class).stream()
                        .filter(app -> app.isActive() == Boolean.TRUE)
                        .collect(Collectors.toList());

            case DEACTIVATED:
                return mapper.mapAsList(appRegistryRepository.findAll(), ApplicationDetailsDTO.class).stream()
                        .filter(app -> app.isActive() == Boolean.FALSE)
                        .collect(Collectors.toList());

            default:
                throw new IllegalArgumentException("Unknown application state");
        }
    }

    @Override
    public void deactivateApplication(String appKey) {
        final ApplicationDetailsEntity entity = appRegistryRepository.getByAppKey(appKey);
        entity.setActive(false);
        appRegistryRepository.save(entity);
    }

    @Override
    public ApplicationDetailsDTO regenerateApplicationSecret(String appKey) {
        final ApplicationDetailsEntity entity = appRegistryRepository.getByAppKey(appKey);
        entity.setAppSecret(generateAppSecret(appKey));
        appRegistryRepository.save(entity);
        return mapper.map(appRegistryRepository.save(entity), ApplicationDetailsDTO.class);
    }

    @Override
    public void activateApplication(String appKey) {
        final ApplicationDetailsEntity entity = appRegistryRepository.getByAppKey(appKey);
        entity.setActive(true);
        appRegistryRepository.save(entity);
    }

    @Override
    public ApplicationLoginDTO getLoginFromRequest(HttpServletRequest request) {
        final ApplicationLoginDTO applicationLogin = new ApplicationLoginDTO();
        if (isApplicationKeyPresent(request) && isApplicationSecretPresent(request)) {
            return applicationLogin.setAppKey(request.getHeader(headerApplicationKey))
                    .setAppSecret(request.getHeader(headerApplicationSecret));
        }
        throw new IllegalArgumentException("App is not registered");
    }

    private boolean isApplicationKeyPresent(HttpServletRequest request) {
        return Objects.isNull(request.getHeader(headerApplicationKey)) && Strings.isNotBlank(request.getHeader(headerApplicationKey));
    }

    private boolean isApplicationSecretPresent(HttpServletRequest request) {
        return Objects.isNull(request.getHeader(headerApplicationSecret)) && Strings.isNotBlank(request.getHeader(headerApplicationSecret));
    }

    //================================================== PRIVATE METHODS ===============================================================

    private String generateAppKey(final String appName) {
        return Base64.getEncoder().encodeToString(appName.getBytes());
    }


    private String generateAppSecret(final String appName) {
        return Base64.getEncoder().encodeToString(String.format("%s_app_%s", appSecretPrefix, appName).getBytes());
    }

}
