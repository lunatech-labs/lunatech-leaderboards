package com.lunatech.leaderboards.controller.provider.exceptionmapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException e) {
        return Response.status(e.getResponse().getStatus())
                .entity(new WebApplicationError(e.getMessage()))
                .build();
    }

    record WebApplicationError(String message) {}
}
