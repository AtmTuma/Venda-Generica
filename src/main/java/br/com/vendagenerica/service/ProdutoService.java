package br.com.vendagenerica.service;

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
import br.com.vendagenerica.dao.impl.ProdutoDAO;
import br.com.vendagenerica.model.Produto;

@Path("/produto")
public class ProdutoService extends Service<Produto, ProdutoDAO> {

	private EntityManagerProducer emp;
	private ProdutoDAO dao;

	@Inject
	public ProdutoService(ProdutoDAO dao) {
		this.dao = dao;
	}

	@Override
	public ProdutoDAO getDAO() {
		if (dao == null) {
			dao = new ProdutoDAO(emp.createEntityManager());
		}
		return dao;
	}
	
	@Override
	public void salvar(Produto entidade) {
		
	}
	
	@Override
	public void deletar(Produto entidade) {
		
	}

	@Override
	public void carregar(Produto entidade) {
		
	}

	@GET
	@Path("/buscar/codigo/{cod_produto}")
	@Produces("application/jboss")
	public Produto buscarCod(@PathParam("cod_produto") String cod) {
		try {
			return getDAO().findByCode(cod);
		} catch (ErroSistema ex) {
			Logger.getLogger(ProdutoService.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/buscar/preco/{preco_produto}")
	@Produces("application/jboss")
	public List<Produto> buscarPrecoUnico(@PathParam("preco_produto") Double preco) {
		try {
			return getDAO().findByPriceRange(preco, preco);
		} catch (ErroSistema ex) {
			Logger.getLogger(ProdutoService.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/buscar/preco/{preco1_produto, preco2_produto}")
	@Produces("application/jboss")
	public List<Produto> buscarPrecoFaixa(@PathParam("preco1_produto") Double preco1,
			@PathParam("preco2_produto") Double preco2) {
		try {
			return getDAO().findByPriceRange(preco1, preco2);
		} catch (ErroSistema ex) {
			Logger.getLogger(ProdutoService.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

}
