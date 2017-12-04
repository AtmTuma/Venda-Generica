package br.com.vendagenerica.service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.DAO;

public abstract class Service<T, D extends DAO<T>> {
	
	public abstract D getDAO();
	public abstract void salvar(T entidade);
	public abstract void deletar(T entidade);
	public abstract void carregar(T entidade);
	
	@GET
	@Path("/listar")
	@Produces("application/jboss")
	public List<T> listar() {
		try {
			List<T> lObjT = getDAO().findAll(); 
			if(!lObjT.isEmpty()) {
				for(int i = 0; i < lObjT.size(); i++) {
					carregar(lObjT.get(i));
				}
			}
			return lObjT;
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}
	
	@GET
	@Path("/buscar/{id}")
	@Produces("application/json")
	public T buscar(@PathParam("id") UUID id) {
		try {
			T objT = getDAO().findById(id);
			carregar(objT);
			return objT;
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/buscar/nome/{nome}")
	@Produces("application/json")
	public List<T> buscar(@PathParam("nome") String nome) {
		try {
			List<T> lObjT = getDAO().findByName(nome); 
			if(!lObjT.isEmpty()) {
				for(int i = 0; i < lObjT.size(); i++) {
					carregar(lObjT.get(i));
				}
			}
			return lObjT;
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes("application/json")
	public Response cadastrar(T objT) {
		try {
			getDAO().save(objT);
			salvar(objT);
			adicionarMensagem("Cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
			return Response.status(200).entity("cadastro realizado.").build();
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/json")
	public Response alterar(T objT) {
		try {
			getDAO().save(objT);
			adicionarMensagem("Alterado com sucesso!", FacesMessage.SEVERITY_INFO);
			return Response.status(200).entity("cadastro alterado.").build();
		} catch (ErroSistema ex) {
			Logger.getLogger(PessoaService.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	@DELETE
	@Path("/excluir/{id}")
	public Response excluir(@PathParam("id") UUID id) {
		try {
			T objT = getDAO().findById(id);
			deletar(objT);
			getDAO().delete(objT);
			adicionarMensagem("Excluido com sucesso!", FacesMessage.SEVERITY_INFO);
			return Response.status(200).entity("cadastro excluido.").build();
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
		FacesMessage ms = new FacesMessage(tipoErro, mensagem, null);
		FacesContext.getCurrentInstance().addMessage(null, ms);
	}
}
