package br.com.vendagenerica.dao;

import java.util.List;
import java.util.UUID;

import br.com.util.exception.ErroSistema;

public interface DAO<T> {
	
	public List<T> findAll() throws ErroSistema;

	public T findById(UUID id) throws ErroSistema;

	public List<T> findByName(String name) throws ErroSistema;

	public boolean save(T t) throws ErroSistema;

	public boolean delete(T t) throws ErroSistema;

}
