package br.com.ibm.restapi.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import br.com.ibm.restapi.enums.ErrorCode;
import br.com.ibm.restapi.exceptions.HandleException;
import br.com.ibm.restapi.model.dto.Parametros;
import br.com.ibm.restapi.model.entity.Cliente;
import br.com.ibm.restapi.util.Util;

@Stateless
public class ClienteDAO {

	@PersistenceContext(name = "RestApiDS")
	private EntityManager em;
	
	public Cliente incluir(Cliente cliente) throws HandleException {
		try {
			em.persist(cliente);
			return cliente;
		}catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> cv = e.getConstraintViolations();
			List<String> campos = new ArrayList<String>() ;
			for (ConstraintViolation<?> constraintViolation : cv) {
				if(constraintViolation.getMessageTemplate().contains("NotNull")) {
					campos.add(constraintViolation.getPropertyPath().toString());
				}
			}
			throw new HandleException("Campo(s) não pode(m) ser nullo: "+ campos.toString(), ErrorCode.BAD_REQUEST.getCode());
		}
		
	}
	
	public Cliente alterar(Cliente cliente) throws HandleException{
		try {
			return em.merge(cliente);	
		}catch (ConstraintViolationException e) {
			Set<ConstraintViolation<?>> cv = e.getConstraintViolations();
			List<String> campos = new ArrayList<String>() ;
			for (ConstraintViolation<?> constraintViolation : cv) {
				if(constraintViolation.getMessageTemplate().contains("NotNull")) {
					campos.add(constraintViolation.getPropertyPath().toString());
				}
			}
			throw new HandleException("Campo(s) não pode(m) ser nullo: "+ campos.toString(), ErrorCode.BAD_REQUEST.getCode());
		}
		
	}
	
	public void excluir(int idCliente) {
		Cliente cliente = em.find(Cliente.class, idCliente);
		em.remove(cliente);
	}
	
	public Cliente localizar(Parametros parametros) {
	
		String hql = "from Cliente c where 1=1";
		
		if(Util.isObjetoTemValor(parametros.getNome())) {
			hql+=" and c.nome = :nome";
		}
		if(Util.isObjetoTemValor(parametros.getCpf())) {
			hql+=" and c.cpf = :cpf";
		}
		if(Util.isObjetoTemValor(parametros.getEndereco())){
			if(Util.isObjetoTemValor(parametros.getEndereco().getEndereco())) {
				hql+=" and c.endereco.endereco = :endereco";
			}
			if(Util.isObjetoTemValor(parametros.getEndereco().getCidade())) {
				hql+=" and c.endereco.cidade = :cidade";
			}
			if(Util.isObjetoTemValor(parametros.getEndereco().getEstado())) {
				hql+=" and c.endereco.estado = :estado";
			}
		}
		
		
		Query q = em.createQuery(hql);
				
		if(Util.isObjetoTemValor(parametros.getNome())) {
			q.setParameter("nome", parametros.getNome());
		}
		if(Util.isObjetoTemValor(parametros.getCpf())) {
			q.setParameter("cpf", parametros.getCpf());
		}
		if(Util.isObjetoTemValor(parametros.getEndereco())){
			if(Util.isObjetoTemValor(parametros.getEndereco().getEndereco())) {
				q.setParameter("endereco", parametros.getEndereco().getEndereco());
			}
			if(Util.isObjetoTemValor(parametros.getEndereco().getCidade())) {
				q.setParameter("cidade", parametros.getEndereco().getCidade());
			}
			if(Util.isObjetoTemValor(parametros.getEndereco().getEstado())) {
				q.setParameter("estado", parametros.getEndereco().getEstado());
			}
		}
		
		return (Cliente) q.getSingleResult();
	}
	
	public Cliente localizarPorId(int id) throws HandleException{
		try {
			Cliente cliente = em.find(Cliente.class, id);
			return cliente;
		}catch (IllegalArgumentException e) {
			throw new HandleException("Valor invalido para o parametro id", ErrorCode.BAD_REQUEST.getCode());
		}
		
	}
	
}
