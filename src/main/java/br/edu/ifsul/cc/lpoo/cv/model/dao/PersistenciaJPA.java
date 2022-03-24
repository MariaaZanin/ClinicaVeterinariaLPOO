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
        entity.getTransaction().begin();// abrir a transacao.
        entity.persist(o); //realiza o insert ou update.
        entity.getTransaction().commit(); //comita a transacao (comando sql)
    }

    @Override
    public void remover(Object o) throws Exception {
//        entity.getTransaction().begin();// abrir a transacao.
//        entity.remove(o); //realiza o delete
//        entity.getTransaction().commit(); //comita a transacao (comando sql)
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
    public Funcionario doLogin(String cpf, String senha) throws Exception {
        try {
            List<Funcionario> list = entity.createNamedQuery("Pessoa.login").setParameter("paramN", cpf).setParameter("paramS", senha).getResultList();
            System.out.println("list = " + list);
            if(list.isEmpty()){
                System.out.println("triste");
                return null;
            }else{
                System.out.println("feliz");
                return list.get(0);
            }
        }catch(Exception error){
            System.out.println(error);
            return null;
        }

        //System.out.println(entity.createNamedQuery("Pessoa.login").getResultList());
        //List<Funcionario> list = entity.createNamedQuery("Pessoa.login").getResultList();

    }
}
