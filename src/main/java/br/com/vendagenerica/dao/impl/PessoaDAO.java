package br.com.vendagenerica.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.model.Pessoa;
import br.com.vendagenerica.dao.DAO;
import br.com.vendagenerica.dao.Transactional;

public class PessoaDAO implements DAO<Pessoa> {

	private EntityManager manager;

	@Inject
	public PessoaDAO(EntityManager manager) {
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> findAll() throws ErroSistema {
		try {
			return manager.createQuery("Select u from Pessoa u").getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pessoas", ex);
		}
	}

	public Pessoa findById(UUID id) throws ErroSistema {
		try {
			Query query = manager.createQuery("Select u from Pessoa u where u.id = :pid");
			query.setParameter("pid", id);
			return (Pessoa) query.getSingleResult();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pessoa por ID", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> findByName(String name) throws ErroSistema {
		try {
			Query query = manager.createQuery("Select u from Pessoa u where upper(u.nome) like :pnome");
			query.setParameter("pnome", "%" + name.toUpperCase() + "%");
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pessoa por nome", ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> findByBirthDate(Date birth) throws ErroSistema {
		try {
			Query query = manager.createQuery("Select u from Pessoa u where u.datanascimento = :pdata");
			query.setParameter("pdata", birth);
			return query.getResultList();
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar buscar pessoa por data de nascimento", ex);
		}
	}

	@Transactional
	public boolean save(Pessoa t) throws ErroSistema {
		try {
			if (t.getId() != null) {
				manager.merge(t);
			} else {
				manager.persist(t);
			}
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar salvar o pessoa", ex);
		}
		return true;
	}

	@Transactional
	public boolean delete(Pessoa t) throws ErroSistema {
		try {
			manager.remove(t);
		} catch (HibernateException ex) {
			throw new ErroSistema("Erro ao tentar remover o pessoa", ex);
		}
		return true;
	}

}
