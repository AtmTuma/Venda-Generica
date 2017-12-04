package br.com.vendagenerica.bean;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.EntityManagerProducer;
import br.com.vendagenerica.dao.impl.ItemPedidoDAO;
import br.com.vendagenerica.dao.impl.PedidoDAO;
import br.com.vendagenerica.model.Pedido;
import br.com.vendagenerica.service.PedidoService;
import br.com.vendagenerica.service.Service;

public class PedidoBEAN extends BEAN<Pedido, PedidoDAO>{

	private EntityManager manager;
	private EntityManagerProducer emp;
	private PedidoDAO dao;
	
	@Inject
	public PedidoBEAN(PedidoDAO dao) {
		this.dao = dao;
	}
	
	@PostConstruct
	public void init() {
		novo();
	}
	
	@Override
	public PedidoDAO getDAO() {
		if(dao == null) {
			dao = new PedidoDAO(manager);
		}
		return dao;
	}

	@Override
	public Pedido criaNovaEntidade() {
		return new Pedido();
	}

	@Override
	public void salvar(Pedido pedido) {
		ItemPedidoDAO itemDAO = new ItemPedidoDAO(emp.createEntityManager());
		for (int i = 0; pedido.getItens().size() > i; i++) {
			try {
				itemDAO.save(pedido.getItens().get(i));
			} catch (ErroSistema ex) {
				Logger.getLogger(PedidoService.class.getName()).log(Level.SEVERE, null, ex);
				adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			}
		}
	}

	@Override
	public void deletar(Pedido pedido) {
		ItemPedidoDAO itemDAO = new ItemPedidoDAO(emp.createEntityManager());
		carregar(pedido);
		while (!pedido.getItens().isEmpty()) {
			try {
				itemDAO.delete(pedido.getItens().remove(0));
			} catch (ErroSistema ex) {
				Logger.getLogger(PedidoService.class.getName()).log(Level.SEVERE, null, ex);
				adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
				throw new WebApplicationException(500);
			}
		}
	}

	@Override
	public void carregar(Pedido pedido) {
		ItemPedidoDAO itemDAO = new ItemPedidoDAO(emp.createEntityManager());
		try {
			if(pedido != null) {
			pedido.setItens(itemDAO.findByPedido(pedido.getId()));
			}
		} catch (ErroSistema ex) {
			Logger.getLogger(PedidoService.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}
	
	public void buscarNum(Integer num) {
		try {
			getEntidades().add(getDAO().findByNumber(num));
			carregar(getEntidades().get(0));
		} catch (ErroSistema ex) {
			Logger.getLogger(ProdutoBEAN.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void buscarData(LocalDateTime data1, LocalDateTime data2) {
		try {
			setEntidades(getDAO().findByDateTimeRange(data1, data2));
			if (!getEntidades().isEmpty()) {
				for (int i = 0; i < getEntidades().size(); i++) {
					carregar(getEntidades().get(i));
				}
			}
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void buscarTotal(Double total1, Double total2) {
		try {
			setEntidades(getDAO().findByTotalRange(total1, total2));
			if (!getEntidades().isEmpty()) {
				for (int i = 0; i < getEntidades().size(); i++) {
					carregar(getEntidades().get(i));
				}
			}
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

}
