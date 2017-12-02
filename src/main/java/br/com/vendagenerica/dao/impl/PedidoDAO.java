package br.com.vendagenerica.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.dao.DAO;
import br.com.vendagenerica.dao.Transactional;
import br.com.vendagenerica.model.Pedido;

public class PedidoDAO implements DAO<Pedido>{
	
	private EntityManager manager;

	@Inject
	public PedidoDAO (EntityManager manager) {
		this.manager = manager;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> findAll() throws ErroSistema {
		try {
			return manager.createQuery("Select u from Pedido u").getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pedidos", ex);
		}
	}

	public Pedido findById(UUID id) throws ErroSistema {
		try {
			Query query = manager.createQuery("Select u from Pedido u where u.id = :pid");
			query.setParameter("pid", id);
			return (Pedido) query.getSingleResult();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pedido por ID", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> findByName(String name) throws ErroSistema {
		try {
			Query query = manager
					.createQuery("Select u from Pedido u inner join u.pessoa l where upper(l.nome) like :pnome");
			query.setParameter("pnome", "%" + name.toUpperCase() + "%");
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pedido por nome do cliente", ex);
		}
	}
	
	public Pedido findByNumber(Integer number) throws ErroSistema {
		try {
			Query query = manager.createQuery("Select u from Pedido u where u.numero = :pnumber");
			query.setParameter("pnumber", number);
			return (Pedido) query.getSingleResult();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pedido por numero", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> findByDateTimeRange(LocalDateTime ldtime1, LocalDateTime ldtime2) throws ErroSistema {
		if(ldtime1.getSecond() > ldtime2.getSecond()) {
			LocalDateTime aux = ldtime1;
			ldtime1 = ldtime2;
			ldtime2 = aux;
		}
		try {
			Query query = manager
					.createQuery("Select u from Pedido u where u.emissao >= :pldtime1 and u.emissao <= :pldtime2");
			query.setParameter("pldtime1", ldtime1);
			query.setParameter("pldtime2", ldtime2);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pedido pelo intervalo de datas", ex);
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
					.createQuery("Select u from Pedido u where u.total >= :ptotal1 and u.total <= :ptotal2");
			query.setParameter("ptotal1", total1);
			query.setParameter("ptotal2", total2);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pedido pelo intervalo de totais", ex);
		}
	}

	@Transactional
	public boolean save(Pedido t) throws ErroSistema {
		try {
			if (t.getId() != null) {
				manager.merge(t);
			} else {
				manager.persist(t);
			}
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar salvar o pedido", ex);
		}
		return true;
	}

	@Transactional
	public boolean delete(Pedido t) throws ErroSistema {
		try {
			manager.remove(t);
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar remover o pedido", ex);
		}
		return true;
	}

}
