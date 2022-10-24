package com.akikun.family.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import com.akikun.family.exception.AbstractHaloException;
import com.akikun.family.model.support.BaseResponse;
import com.akikun.family.utils.ExceptionUtils;
import com.akikun.family.utils.JsonUtils;
import com.akikun.family.utils.ServletUtils;

/**
 * Default AuthenticationFailureHandler.
 *
 * @author johnniang
 * @date 12/12/18
 */
@Slf4j
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private boolean productionEnv = true;

    private ObjectMapper objectMapper = JsonUtils.DEFAULT_JSON_MAPPER;

    public DefaultAuthenticationFailureHandler() {
    }

    @Override
    public void onFailure(HttpServletRequest request, HttpServletResponse response,
        AbstractHaloException exception) throws IOException {
        log.warn("Handle unsuccessful authentication, ip: [{}]", ServletUtils.getClientIP(request));
        log.error("Authentication failure: [{}], status: [{}], data: [{}]", exception.getMessage(),
            exception.getStatus(), exception.getErrorData());

        BaseResponse<Object> errorDetail = new BaseResponse<>();

        errorDetail.setStatus(exception.getStatus().value());
        errorDetail.setMessage(exception.getMessage());
        errorDetail.setData(exception.getErrorData());

        if (!productionEnv) {
            errorDetail.setDevMessage(ExceptionUtils.getStackTrace(exception));
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(exception.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorDetail));
    }

    /**
     * Sets custom object mapper.
     *
     * @param objectMapper object mapper
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "Object mapper must not be null");

        this.objectMapper = objectMapper;
    }

    /**
     * Sets production environment.
     *
     * @param productionEnv production environment
     */
    public void setProductionEnv(boolean productionEnv) {
        this.productionEnv = productionEnv;
    }
}
