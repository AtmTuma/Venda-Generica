package br.com.vendagenerica.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.EntityManagerProducer;
import br.com.vendagenerica.dao.impl.PessoaDAO;
import br.com.vendagenerica.model.Pessoa;

@Path("/pessoa")
public class PessoaService extends Service<Pessoa, PessoaDAO>{

	private EntityManagerProducer emp;
	private PessoaDAO dao;

	@Inject
	public PessoaService(PessoaDAO dao) {
		this.dao = dao;
	}

	@Override
	public PessoaDAO getDAO() {
		if (dao == null) {
			dao = new PessoaDAO(emp.createEntityManager());
		}
		return dao;
	}
	
	@Override
	public void salvar(Pessoa entidade) {
		
	}
	
	@Override
	public void deletar(Pessoa entidade) {
		
	}

	@Override
	public void carregar(Pessoa entidade) {
		
	}
	
	@GET
	@Path("/buscar/nasc/{nasc_pessoa}")
	@Produces("application/json")
	public List<Pessoa> buscar(@PathParam("nasc_pessoa") Date data) {
		try {
			return getDAO().findByBirthDate(data);
		} catch (ErroSistema ex) {
			Logger.getLogger(PessoaService.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

}
