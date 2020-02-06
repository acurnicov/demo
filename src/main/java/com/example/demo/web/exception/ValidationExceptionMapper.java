package com.example.demo.web.exception;

import org.springframework.context.annotation.Profile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Profile("dev")
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException ex) {
        if (ex instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) ex);
        }

        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity("Error: " + unwrapException(ex)).build();
    }

    private Response handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
            String lastName = "";
            for (Path.Node n : v.getPropertyPath()) {
                lastName = n.getName();
            }
            sb.append("Error: '" + lastName + "' " + v.getMessage() + "\n");
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON).entity(sb.toString()).build();
    }

    protected String unwrapException(Throwable t) {
        StringBuffer sb = new StringBuffer();
        unwrapException(sb, t);
        return sb.toString();
    }

    private void unwrapException(StringBuffer sb, Throwable t) {
        if (t == null) {
            return;
        }
        sb.append(t.getMessage());
        if (t.getCause() != null && t != t.getCause()) {
            sb.append('[');
            unwrapException(sb, t.getCause());
            sb.append(']');
        }
    }
}