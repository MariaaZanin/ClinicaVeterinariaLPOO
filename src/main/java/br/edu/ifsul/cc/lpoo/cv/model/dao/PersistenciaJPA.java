package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PersistenciaJPA implements InterfacePersistencia {

    //QUESTAO 3
    public EntityManagerFactory factory;
    public EntityManager entity;

    public PersistenciaJPA() {
        factory = Persistence.createEntityManagerFactory("MariaEduarda_cv_lpoo_2022");
        entity = factory.createEntityManager();
    }

    @Override
    public Boolean conexaoAberta() {
        return entity.isOpen();
    }

    @Override
    public void fecharConexao() {
        entity.close();
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {

    }

    @Override
    public void remover(Object o) throws Exception {

    }

    public List mostraTudo(Class c) throws Exception{
        return null;
    }

    @Override
    public List<Produto> listProdutos() throws Exception {
        throw new UnsupportedOperationException("Funcionalidade indisponivel no momento.");
    }

    @Override
    public List<Receita> listReceitas() throws Exception {
        throw new UnsupportedOperationException("Funcionalidade indisponivel no momento.");
    }

    @Override
    public List<Fornecedor> listFornecedor() throws Exception {
        throw new UnsupportedOperationException("Funcionalidade indisponivel no momento.");
    }

    @Override
    public List<Funcionario> listFuncionario() throws Exception {
        throw new UnsupportedOperationException("Funcionalidade indisponivel no momento.");
    }

    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {

        List<Fornecedor> list = entity.createNamedQuery("Pessoa.login").setParameter("paramN", cpf).setParameter("paramS", senha).getResultList();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }

    }
}