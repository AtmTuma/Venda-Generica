package br.com.vendagenerica.service;

import java.time.LocalDateTime;
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
import br.com.vendagenerica.dao.impl.ItemPedidoDAO;
import br.com.vendagenerica.dao.impl.PedidoDAO;
import br.com.vendagenerica.model.Pedido;

@Path("/pedido")
public class PedidoService extends Service<Pedido, PedidoDAO> {

	private EntityManagerProducer emp;
	private PedidoDAO dao;

	@Inject
	public PedidoService(PedidoDAO dao) {
		this.dao = dao;
	}

	@Override
	public PedidoDAO getDAO() {
		if (dao == null) {
			dao = new PedidoDAO(emp.createEntityManager());
		}
		return dao;
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
				throw new WebApplicationException(500);
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
			pedido.setItens(itemDAO.findByPedido(pedido.getId()));
		} catch (ErroSistema ex) {
			Logger.getLogger(PedidoService.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/buscar/numero/{num}")
	@Produces("application/json")
	public Pedido buscar(@PathParam("num") Integer num) {
		try {
			Pedido objT = getDAO().findByNumber(num);
			carregar(objT);
			return objT;
		} catch (ErroSistema ex) {
			Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/buscar/data/{data}")
	@Produces("application/json")
	public List<Pedido> buscarData1(@PathParam("data") LocalDateTime data) {
		try {
			List<Pedido> lObjT = getDAO().findByDateTimeRange(data, data);
			if (!lObjT.isEmpty()) {
				for (int i = 0; i < lObjT.size(); i++) {
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
	@Path("/buscar/data/{data1, data2}")
	@Produces("application/json")
	public List<Pedido> buscarData2(@PathParam("data1") LocalDateTime data1, @PathParam("data2") LocalDateTime data2) {
		try {
			List<Pedido> lObjT = getDAO().findByDateTimeRange(data1, data2);
			if (!lObjT.isEmpty()) {
				for (int i = 0; i < lObjT.size(); i++) {
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
	@Path("/buscar/total/{total}")
	@Produces("application/json")
	public List<Pedido> buscarTotal1(@PathParam("total") Double total) {
		try {
			List<Pedido> lObjT = getDAO().findByTotalRange(total, total);
			if (!lObjT.isEmpty()) {
				for (int i = 0; i < lObjT.size(); i++) {
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
	@Path("/buscar/total/{total1, total2}")
	@Produces("application/json")
	public List<Pedido> buscarTotal2(@PathParam("total1") Double total1, @PathParam("total2") Double total2) {
		try {
			List<Pedido> lObjT = getDAO().findByTotalRange(total1, total2);
			if (!lObjT.isEmpty()) {
				for (int i = 0; i < lObjT.size(); i++) {
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
}
