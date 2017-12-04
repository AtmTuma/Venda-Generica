package br.com.vendagenerica.bean;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.impl.ProdutoDAO;
import br.com.vendagenerica.model.Produto;

public class ProdutoBEAN extends BEAN<Produto, ProdutoDAO>{

	private EntityManager manager;
	private ProdutoDAO dao;
	
	@Inject
	public ProdutoBEAN(ProdutoDAO dao) {
		this.dao = dao;
	}
	
	@PostConstruct
	public void init() {
		novo();
	}
	
	@Override
	public ProdutoDAO getDAO() {
		if(dao == null) {
			dao = new ProdutoDAO(manager);
		}
		return dao;
	}

	@Override
	public Produto criaNovaEntidade() {
		return new Produto();
	}

	@Override
	public void salvar(Produto entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Produto entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void carregar(Produto entidade) {
		// TODO Auto-generated method stub
		
	}

	public void buscaCod(String cod) {
		try {
			getEntidades().add(getDAO().findByCode(cod));
		} catch (ErroSistema ex) {
			Logger.getLogger(ProdutoBEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void buscaPreco(Double preco1, Double preco2) {
		try {
			setEntidades(getDAO().findByPriceRange(preco1, preco2));
		} catch (ErroSistema ex) {
			Logger.getLogger(ProdutoBEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
}
