package br.com.exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GeneralException implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {

        MessageError messageError = new MessageError();

        if (e instanceof BusinessException) {
            messageError.setMessageError(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(messageError).build();
        } else {
            messageError.setMessageError("Obtivemos uma falha, entre em contato com o suporte!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageError).build();
        }

    }
}
