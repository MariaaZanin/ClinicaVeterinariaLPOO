package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import org.junit.Test;

public class TestPersistenciaJPA {
    //QUESTÃO 4 GERAÇÃO DE TABELAS VIA JPA
    // /@Test
    public void testConexaoGeracaoTabelas(){
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
    }

    @Test
    public void testGeracaoPessoaLogin() throws Exception {

        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");

            Fornecedor f = persistencia.doLogin("teste@", "1234");

            if(f == null){
                f = new Fornecedor();
                f.setNome("maria");
                f.setSenha("1234");

                persistencia.persist(f);
                System.out.println("Cadastrou nova pessoa!");
            }else{
                System.out.println("Encontrou pessoa cadastrado!");
            }

            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }

    }

}