package br.com.ibm.restapi.resources;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ibm.restapi.exceptions.HandleException;
import br.com.ibm.restapi.exceptions.HandleExceptionMapper;
import br.com.ibm.restapi.model.dto.ClienteDTO;
import br.com.ibm.restapi.model.dto.Parametros;
import br.com.ibm.restapi.model.entity.Cliente;
import br.com.ibm.restapi.services.ClienteService;

@Path("clientes")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class ClienteResource {

	@Inject
	private ClienteService clienteService;
	
	@GET
	public Response localizar(Parametros parametros) {
		Cliente cliente = clienteService.localizar(parametros);
		return Response.ok(cliente).build();
	}
	
	@POST
	public Response incluir(Cliente cliente) {
		try {
			clienteService.incluir(cliente);
			return Response.status(201).entity(cliente).build();	
		}catch(HandleException e) {
			return new HandleExceptionMapper().toResponse(e);
		}
	}
	
	@PUT
	public Response alterar(ClienteDTO clienteDTO) {
		try {
			Cliente cliente = clienteService.alterar(clienteDTO);
			return  Response.ok(cliente).build();
		}catch (HandleException e) {
			return new HandleExceptionMapper().toResponse(e);
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response excluir(@PathParam("id") int id) {
		clienteService.excluir(id);
		return Response.status(201).build();
	}
	
}
