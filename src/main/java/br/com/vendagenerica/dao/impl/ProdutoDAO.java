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
import br.com.vendagenerica.model.Produto;

public class ProdutoDAO implements DAO<Produto>{

	private EntityManager manager;

	@Inject
	public ProdutoDAO (EntityManager manager) {
		this.manager = manager;
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findAll() throws ErroSistema {
		try {
			return manager.createQuery("Select u from Produto u").getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar produtos", ex);
		}
	}

	public Produto findById(UUID id) throws ErroSistema {
		try {
			Query query = manager.createQuery("Select u from Produto u where u.id = :pid");
			query.setParameter("pid", id);
			return (Produto) query.getSingleResult();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar produto por ID", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findByName(String name) throws ErroSistema {
		try {
			Query query = manager
					.createQuery("Select u from Produto u where upper(u.nome) like :pnome");
			query.setParameter("pnome", "%" + name.toUpperCase() + "%");
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar produto por nome", ex);
		}
	}
	
	public Produto findByCode(String code) throws ErroSistema {
		try {
			Query query = manager
					.createQuery("Select u from Produto u where upper(u.codigo) like :pcode");
			query.setParameter("pcode", "%" + code.toUpperCase() + "%");
			return (Produto) query.getSingleResult();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar produto por codigo", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findByPriceRange(Double price1, Double price2) throws ErroSistema {
		if(price1 > price2) {
			Double aux = price1;
			price1 = price2;
			price2 = aux;
		}
		try {
			Query query = manager
					.createQuery("Select u from Produto u where u.precounitario >= :pprice1 and u.precounitario <= :pprice2");
			query.setParameter("pprice1", price1);
			query.setParameter("pprice2", price2);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar produto por codigo", ex);
		}
	}

	@Transactional
	public boolean save(Produto t) throws ErroSistema {
		try {
			if (t.getId() != null) {
				manager.merge(t);
			} else {
				manager.persist(t);
			}
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar salvar o produto", ex);
		}
		return true;
	}

	@Transactional
	public boolean delete(Produto t) throws ErroSistema {
		try {
			manager.remove(t);
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar remover o pedido", ex);
		}
		return true;
	}

}
