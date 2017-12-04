package br.com.vendagenerica.dao.impl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.DAO;
import br.com.vendagenerica.dao.Transactional;
import br.com.vendagenerica.model.ItemPedido;
import br.com.vendagenerica.model.Pedido;

public class ItemPedidoDAO implements DAO<ItemPedido>{

	private EntityManager manager;

	@Inject
	public ItemPedidoDAO (EntityManager manager) {
		this.manager = manager;
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemPedido> findAll() throws ErroSistema {
		try {
			return manager.createQuery("Select u from ItemPedido u").getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pedidos", ex);
		}
	}

	public ItemPedido findById(UUID id) throws ErroSistema {
		try {
			Query query = manager.createQuery("Select u from ItemPedido u where u.id = :pid");
			query.setParameter("pid", id);
			return (ItemPedido) query.getSingleResult();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar item do pedido por ID", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ItemPedido> findByName(String name) throws ErroSistema {
		try {
			Query query = manager
					.createQuery("Select u from ItemPedido u inner join u.produto l where upper(l.nome) like :pnome");
			query.setParameter("pnome", "%" + name.toUpperCase() + "%");
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar item do pedido por nome do produto", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> findByAmountRange(Double amount1, Double amount2) throws ErroSistema {
		if(amount1 > amount2) {
			Double aux = amount1;
			amount1 = amount2;
			amount2 = aux;
		}
		try {
			Query query = manager
					.createQuery("Select u from ItemPedido u where u.quantidade >= :pamount1 and u.quantidade <= :pamount2");
			query.setParameter("pamount1", amount1);
			query.setParameter("pamount2", amount2);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar item do pedido pelo intervalo de quantidades", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> findByPriceRange(Double price1, Double price2) throws ErroSistema {
		if(price1 > price2) {
			Double aux = price1;
			price1 = price2;
			price2 = aux;
		}
		try {
			Query query = manager
					.createQuery("Select u from ItemPedido u where u.precounitario >= :pprice1 and u.precounitario <= :pprice2");
			query.setParameter("pprice1", price1);
			query.setParameter("pprice2", price2);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar item do pedido pelo intervalo de preços", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> findByDiscountRange(Double discount1, Double discount2) throws ErroSistema {
		if(discount1 > discount2) {
			Double aux = discount1;
			discount1 = discount2;
			discount2 = aux;
		}
		try {
			Query query = manager
					.createQuery("Select u from ItemPedido u where u.desconto >= :pdiscount1 and u.desconto <= :pdiscount2");
			query.setParameter("pdisocunt1", discount1);
			query.setParameter("pdiscount2", discount2);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar item do pedido pelo intervalo de descontos", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> findByTotalRange(Double total1, Double total2) throws ErroSistema {
		if(total1 > total2) {
			Double aux = total1;
			total1 = total2;
			total2 = aux;
		}
		try {
			Query query = manager
					.createQuery("Select u from ItemPedido u where u.total >= :ptotal1 and u.total <= :ptotal2");
			query.setParameter("ptotal1", total1);
			query.setParameter("ptotal2", total2);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar item do pedido pelo intervalo de totais", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemPedido> findByPedido(UUID pedido) throws ErroSistema {
		try {
			Query query = manager
					.createQuery("Select u from ItemPedido u inner join u.pedido l where l.id = :pID");
			query.setParameter("pID", pedido);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar item do pedido por nome do produto", ex);
		}
	}

	@Transactional
	public boolean save(ItemPedido t) throws ErroSistema {
		try {
			if (t.getId() != null) {
				manager.merge(t);
			} else {
				manager.persist(t);
			}
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar salvar o item do pedido", ex);
		}
		return true;
	}

	@Transactional
	public boolean delete(ItemPedido t) throws ErroSistema {
		try {
			manager.remove(t);
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar remover o item do pedido", ex);
		}
		return true;
	}

}
