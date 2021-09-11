package br.com.ibm.restapi.exceptions;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.ibm.restapi.enums.ErrorCode;
import br.com.ibm.restapi.model.dto.ErrorMessage;

public class HandleExceptionMapper {

	
	public Response toResponse(HandleException exception) {
		
		ErrorMessage erro = new ErrorMessage(exception.getMessage(), exception.getCodigo());
		
		if(exception.getCodigo() == ErrorCode.BAD_REQUEST.getCode()) {
			return Response.status(Status.BAD_REQUEST).entity(erro).type(MediaType.APPLICATION_JSON).build() ;
		}
		else if(exception.getCodigo() == ErrorCode.NOT_FOUND.getCode()) {
			return Response.status(Status.NOT_FOUND).entity(erro).type(MediaType.APPLICATION_JSON).build() ;
		}
		else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(erro).type(MediaType.APPLICATION_JSON).build() ;
		}
		
	}

}
