package com.lunatech.leaderboards.controller.providers;

import org.yaml.snakeyaml.reader.ReaderException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ReaderExceptionMapper implements ExceptionMapper<ReaderException> {
    @Override
    public Response toResponse(ReaderException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
