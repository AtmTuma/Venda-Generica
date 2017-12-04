package br.com.vendagenerica.service;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.ws.rs.Path;

import br.com.vendagenerica.dao.EntityManagerProducer;
import br.com.vendagenerica.dao.impl.ItemPedidoDAO;
import br.com.vendagenerica.dao.impl.PedidoDAO;
import br.com.vendagenerica.dao.impl.ProdutoDAO;
import br.com.vendagenerica.model.ItemPedido;

@Path("/pedido/{num_pedido}/item")
public class ItemPedidoService extends Service<ItemPedido, ItemPedidoDAO>{

	private EntityManagerProducer emp;
	private ItemPedidoDAO dao;

	@Inject
	public ItemPedidoService(ItemPedidoDAO dao) {
		this.dao = dao;
	}

	@Override
	public ItemPedidoDAO getDAO() {
		if (dao == null) {
			dao = new ItemPedidoDAO(emp.createEntityManager());
		}
		return dao;
	}

	@Override
	public void salvar(ItemPedido entidade) {
		
	}

	@Override
	public void deletar(ItemPedido entidade) {
		
	}

	@Override
	public void carregar(ItemPedido entidade) {
		
	}

	
}
