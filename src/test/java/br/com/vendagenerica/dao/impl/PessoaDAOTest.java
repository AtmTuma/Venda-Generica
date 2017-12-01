package br.com.vendagenerica.dao.impl;

import java.time.LocalDate;

import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.util.exception.ErroSistema;
import br.com.vendagenerica.AbstractTestCase;
import br.com.vendagenerica.model.Pessoa;

public class PessoaDAOTest extends AbstractTestCase{
	private PessoaDAO dao;
	
	@Before
	public void init() throws ErroSistema {
		try {
			dao = new PessoaDAO(manager);
		} catch (Exception ex) {
			throw new ErroSistema("Erro no persistencer ", ex);
		}
        
	}
	
	@Test
    public void findAllTest() throws ErroSistema {
		
		/*Query queryPessoa = manager.createNativeQuery("DELETE FROM PESSOA");
        queryPessoa.executeUpdate();
        Assert.assertTrue(dao.findAll().size() == 0);
        
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Pessoa");
		pessoa.setDataNascimento(LocalDate.of(1991, 8, 27));
		manager.persist(pessoa);
		Assert.assertTrue(dao.findAll().size() == 0);*/
	}
}
