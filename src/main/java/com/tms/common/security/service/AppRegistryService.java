package com.tms.common.security.service;

import com.tms.common.domain.dto.ApplicationDetailsDTO;
import com.tms.common.domain.dto.ApplicationLoginDTO;
import com.tms.common.domain.enumTypes.ApplicationState;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AppRegistryService {

    boolean validateApplication(final ApplicationLoginDTO login);

    ApplicationDetailsDTO registerApplication(final String appRoute, final String appName);

    void deleteApplication(final String appKey);

    List<ApplicationDetailsDTO> allApplicationFilteredByActivity(final ApplicationState applicationState);

    void deactivateApplication(final String appKey);

    void activateApplication(final String appKey);

    ApplicationDetailsDTO regenerateApplicationSecret(final String appKey);

    ApplicationLoginDTO getLoginFromRequest(final HttpServletRequest request);

}
