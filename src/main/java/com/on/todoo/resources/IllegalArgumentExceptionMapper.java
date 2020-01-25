package com.on.todoo.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.jetty.http.HttpStatus;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(IllegalArgumentException exception) {

		return Response.status(HttpStatus.UNPROCESSABLE_ENTITY_422).entity(exception.getMessage())
				.type(MediaType.TEXT_PLAIN).build();
	}

}
