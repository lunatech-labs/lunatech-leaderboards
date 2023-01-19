package com.lunatech.leaderboard.controller.provider.exceptionmapper;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Inject
    WebApplicationExceptionMapper webApplicationExceptionMapper;


    @Override
    public Response toResponse(NotFoundException e) {
        return webApplicationExceptionMapper.toResponse(e);
    }
}
