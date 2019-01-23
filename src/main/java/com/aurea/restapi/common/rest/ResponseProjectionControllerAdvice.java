package com.aurea.restapi.common.rest;

import com.aurea.restapi.common.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

@ControllerAdvice
public class ResponseProjectionControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
            MethodParameter returnType,
            ServerHttpRequest req, ServerHttpResponse res) {
        ServletServerHttpRequest request = (ServletServerHttpRequest) req;
        String projection = request.getServletRequest().getParameter("view");
        if (projection != null) {
            switch (projection) {
                case "NAME":
                    bodyContainer.setSerializationView(User.Projections.Name.class);
                    break;
                case "DETAILS":
                    bodyContainer.setSerializationView(User.Projections.Details.class);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}