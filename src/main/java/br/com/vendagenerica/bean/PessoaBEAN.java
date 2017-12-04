package br.com.vendagenerica.bean;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.impl.PessoaDAO;
import br.com.vendagenerica.model.Pessoa;

@RequestScoped
@ManagedBean(name = "pessoaBean")
@Named
public class PessoaBEAN extends BEAN<Pessoa, PessoaDAO>{
	
	private EntityManager manager;
	private PessoaDAO dao;
	
	@Inject
	public PessoaBEAN (PessoaDAO dao) {
		this.dao = dao;
	}
	
	@PostConstruct
	public void init() {
		novo();
	}

	@Override
	public PessoaDAO getDAO() {
		if(dao == null) {
			dao = new PessoaDAO(manager);
		} 
		return dao;
	}

	@Override
	public Pessoa criaNovaEntidade() {
		return new Pessoa();
	}

	@Override
	public void salvar(Pessoa entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Pessoa entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void carregar(Pessoa entidade) {
		// TODO Auto-generated method stub
		
	}
	
	public void buscarDataNasc(Date data) {
		try {
			setEntidades(getDAO().findByBirthDate(data));
		} catch (ErroSistema ex) {
			Logger.getLogger(PessoaBEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
}
