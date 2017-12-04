package br.com.vendagenerica.bean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.DAO;

public abstract class BEAN<E, D extends DAO<E>>{
	
	private E entidade;
	private List<E> entidades;
	
	public abstract D getDAO();
	public abstract E criaNovaEntidade();
	public abstract void salvar(E entidade);
	public abstract void deletar(E entidade);
	public abstract void carregar(E entidade);
	
	public void novo() {
		this.entidade = criaNovaEntidade();
	}
	
	public void listar() {
		try {
			setEntidades(getDAO().findAll()); 
			if(getEntidades() != null && !getEntidades().isEmpty()) {
				for(int i = 0; i < getEntidades().size(); i++) {
					carregar(getEntidades().get(i));
				}
			} else {
				adicionarMensagem("Não tem nada cadastrado!", FacesMessage.SEVERITY_WARN);
			}
		} catch (ErroSistema ex) {
			Logger.getLogger(BEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void buscarNome(String nome) {
		try {
			setEntidades(getDAO().findByName(nome)); 
			if(!getEntidades().isEmpty()) {
				for(int i = 0; i < getEntidades().size(); i++) {
					carregar(getEntidades().get(i));
				}
			}
		} catch (ErroSistema ex) {
			Logger.getLogger(BEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	
	
	public void cadastrar() {
		try {
			getDAO().save(entidade);
			adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
		} catch (ErroSistema ex) {
			Logger.getLogger(BEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void editar(E entidade) {
		try {
			getDAO().save(entidade);
			adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
		} catch (ErroSistema ex) {
			Logger.getLogger(BEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void excluir(E entidade){
        try {
            getDAO().delete(entidade);
            entidades.remove(entidade);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(BEAN.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
	
	public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
		FacesMessage ms = new FacesMessage(tipoErro, mensagem, null);
		FacesContext.getCurrentInstance().addMessage(null, ms);
	}
	
	public E getEntidade() {
		return entidade;
	}
	public void setEntidade(E entidade) {
		this.entidade = entidade;
	}
	public List<E> getEntidades() {
		return entidades;
	}
	public void setEntidades(List<E> entidades) {
		this.entidades = entidades;
	}
	
	
}
