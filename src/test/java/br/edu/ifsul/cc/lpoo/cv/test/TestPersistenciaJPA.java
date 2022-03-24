package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static br.edu.ifsul.cc.lpoo.cv.model.Cargo.ADESTRADOR;

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

            Funcionario f = persistencia.doLogin("00000000000", "1234");
            System.out.println("o que tem no F: "+ f);

            if(f == null){
                f = new Funcionario();
                f.setNome("Vitoria");
                f.setSenha("1234");
                f.setCpf("00000000000");
                f.setCep("00000000");
                f.setComplemento("avenida");
                f.setEmail("vitoria@gmail.com");
                f.setEndereco("Rua capitao");
                f.setNumero_celular("000000000");
                f.setRg("0000000000");
                f.setNumero_ctps("00000000");
                f.setNumero_pis("0000000");
                f.setCargo(ADESTRADOR);

                Calendar data_nasc = Calendar.getInstance();
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                data_nasc.setTime(formato.parse("30/09/2001"));
                f.setData_nascimento(data_nasc);
                Calendar data_cadast = Calendar.getInstance();
                data_cadast.setTime(formato.parse("22/03/2022"));
                f.setData_cadastro(data_cadast);
                System.out.println("f:" + f);
                persistencia.persist(f);
                System.out.println("Cadastrou novo Funcionario!");
            }else{
                System.out.println("Encontrou funcionario cadastrado!");
            }

            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }

    }

}