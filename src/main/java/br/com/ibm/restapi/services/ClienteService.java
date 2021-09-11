package br.com.ibm.restapi.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ibm.restapi.enums.ErrorCode;
import br.com.ibm.restapi.exceptions.HandleException;
import br.com.ibm.restapi.model.dao.ClienteDAO;
import br.com.ibm.restapi.model.dto.ClienteDTO;
import br.com.ibm.restapi.model.dto.Parametros;
import br.com.ibm.restapi.model.entity.Cliente;
import br.com.ibm.restapi.model.entity.Endereco;
import br.com.ibm.restapi.util.Util;

@Stateless
public class ClienteService {

	@Inject
	private ClienteDAO dao;
	
	public Cliente incluir(Cliente cliente) throws HandleException{
		return dao.incluir(cliente);
	}
	
	public Cliente localizar(Parametros parametros) {
		return dao.localizar(parametros);
	}
	
	public Cliente alterar(ClienteDTO clienteDTO) throws HandleException{
		
		List<String> listaCamposRequeridosSemValor = new ArrayList<String>();
		Cliente cliente = dao.localizarPorId(clienteDTO.getIdCliente());
		
		if(!Util.isObjetoTemValor(clienteDTO.getCpf())) {
			listaCamposRequeridosSemValor.add("cpf");
		}
		if(!Util.isObjetoTemValor(clienteDTO.getNome())) {
			listaCamposRequeridosSemValor.add("nome");
		}
		if(!Util.isObjetoTemValor(clienteDTO.getEndereco().getEndereco())) {
			listaCamposRequeridosSemValor.add("endereco");
		}
		if(!Util.isObjetoTemValor(clienteDTO.getEndereco().getCidade())) {
			listaCamposRequeridosSemValor.add("cidade");
		}
		if(!Util.isObjetoTemValor(clienteDTO.getEndereco().getEstado())) {
			listaCamposRequeridosSemValor.add("estado");
		}
		
		if(listaCamposRequeridosSemValor.isEmpty()) {
			cliente.setCpf(clienteDTO.getCpf());
			cliente.setId(clienteDTO.getId());
			cliente.setNome(clienteDTO.getNome());
			
			Endereco endereco = cliente.getEndereco();
			endereco.setBairro(clienteDTO.getEndereco().getBairro());
			endereco.setCidade(clienteDTO.getEndereco().getCidade());
			endereco.setEndereco(clienteDTO.getEndereco().getEndereco());
			endereco.setEstado(clienteDTO.getEndereco().getEstado());
			endereco.setNumero(clienteDTO.getEndereco().getNumero());
			cliente.setEndereco(endereco);
			
			return dao.alterar(cliente);
		}else {
			throw new HandleException("Campo(s) não pode(m) ser nullo: "+ listaCamposRequeridosSemValor.toString(), ErrorCode.BAD_REQUEST.getCode());
		}
		
		
	}
	
	public void excluir(int idCliente) {
		dao.excluir(idCliente);
	}
	
}
