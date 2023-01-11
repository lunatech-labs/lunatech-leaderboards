package com.lunatech.leaderboards.controller.providers;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Inject WebApplicationErrorProvider webApplicationErrorProvider;


    @Override
    public Response toResponse(NotFoundException e) {
        return webApplicationErrorProvider.toResponse(e);
    }
}
