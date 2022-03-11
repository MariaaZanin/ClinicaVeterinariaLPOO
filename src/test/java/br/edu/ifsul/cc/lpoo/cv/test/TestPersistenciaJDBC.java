package br.edu.ifsul.cc.lpoo.cv.test;


import br.edu.ifsul.cc.lpoo.cv.model.Consulta;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import static br.edu.ifsul.cc.lpoo.cv.model.TipoProduto.CONSULTA;
import static br.edu.ifsul.cc.lpoo.cv.model.TipoProduto.MEDICAMENTO;

import java.util.List;
import org.junit.Test;


public class TestPersistenciaJDBC{
    //@Test
    public void testConexaoGeracaoTabelas() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }

    }

    //@Test
    public void testPersistencia() throws Exception{
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()) {
            //VERIFICA SE TEM INFORMAÇÕES DENTRO DAS TABELAS
            if(!persistencia.mostraTudo(Receita.class).isEmpty() || !persistencia.mostraTudo(Produto.class).isEmpty()){
                Fornecedor f = new Fornecedor();
                if(!persistencia.mostraTudo(Receita.class).isEmpty()) { // VERIFICA SE É RECEITA QUE TEM INFORMAÇÃO
                    //IMRPIME OS DADOS DA RECEITA
                    System.out.println("Receitas encontradas:");

                    for (Receita r : (List<Receita>) persistencia.mostraTudo(Receita.class)) {
                        System.out.println("\tId: " + r.getId() + " | Orientacao: " + r.getOrientacao() +
                                "| Consulta ID: " + r.getConsulta().getId());

                        System.out.println("Produtos da Receita: ");

                        for (Produto p : r.getProdutos()) {
                            System.out.println("\tId: " + p.getId() + " | Nome: " + p.getNome() + " | Valor: " + p.getValor()
                                    + " | Tipo Produto: " + p.getTipoProduto() + " | Fornecedor ID: " + p.getFornecedor().getCpf());
                        }
                        persistencia.remover(r);
                    }
                    }
                    if(!persistencia.mostraTudo(Produto.class).isEmpty()){ // VERIFICA SE É PRODUTO QUE TEM INFORMAÇÃO
                    //IMPRIME OS DADOS DO PRODUTO
                    System.out.println("\n Produtos encontrados: ");
                    for(Produto p : (List<Produto>)persistencia.mostraTudo(Produto.class)){
                        System.out.println("\tId: " + p.getId() + " | Nome: " + p.getNome() + " | Quantidade: " + p.getQuantidade()
                         + " | Tipo Produto: " + p .getTipoProduto() + " | Valor: " + p.getValor() + " | Fornecedor CPF: " + p.getFornecedor().getCpf());
                        f.setCpf(p.getFornecedor().getCpf());

                        persistencia.remover(p);
                    }
                    }
            } else {
                System.out.println("Não encontrou o produto");
                //ADICIONA INFORMAÇÕES DO PRODUTO E RECEITA
                Fornecedor f = new Fornecedor();
                f.setCpf("12345678");

                Produto end = new Produto();
                end.setNome("Benalet");
                end.setValor(12f);
                end.setQuantidade(4f);
                end.setTipoProduto(CONSULTA);
                end.setFornecedor(f);
                persistencia.persist(end); // insert na tabela
                System.out.println("Cadastrou o produto: " + end.getId());

                Produto end3 = new Produto();
                end3.setNome("Corticorten");
                end3.setValor(15f);
                end3.setQuantidade(6f);
                end3.setTipoProduto(MEDICAMENTO);
                end3.setFornecedor(f);
                persistencia.persist(end3); // insert na tabela
                System.out.println("Cadastrou o produto: " + end3.getId());

                System.out.println("Não encontrou o receita");
                List<Produto> lista_prod = persistencia.listProdutos();
                Consulta c = new Consulta();
                c.setId("123");
                Receita end2 = new Receita();
                end2.setOrientacao("orientacao");
                end2.setConsulta(c);
                end2.setProdutos(lista_prod);
                persistencia.persist(end2); // insert na tabela
                System.out.println("Cadastrou o receita: " + end2.getId());
            }
            persistencia.fecharConexao();
        }else {
            System.out.println("Nao abriu a conexao com BD via JDBC");
        }
    }


    //@Test
    public void testListPersistenciaProduto() throws Exception { //INSERE DADOS
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            List<Produto> lista = persistencia.listProdutos();

            if(!lista.isEmpty()) {
                for(Produto p: lista) {
                    System.out.println("Produto ID: "+p.getId()+" Nome: "+p.getNome());
                    persistencia.remover(p);
                }
            } else {
                System.out.println("Não encontrou o produto");

                Fornecedor f = new Fornecedor();
                f.setCpf("12345678");

                Produto end = new Produto();
                end.setNome("Benalet");
                end.setValor(12f);
                end.setQuantidade(4f);
                end.setTipoProduto(CONSULTA);
                end.setFornecedor(f);
                persistencia.persist(end); // insert na tabela
                System.out.println("Cadastrou o produto "+end.getId());

                Produto end3 = new Produto();
                end3.setNome("Corticorten");
                end3.setValor(15f);
                end3.setQuantidade(6f);
                end3.setTipoProduto(MEDICAMENTO);
                end3.setFornecedor(f);
                persistencia.persist(end3); // insert na tabela
                System.out.println("Cadastrou o produto: " + end3.getId());

            }

            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    //@Test
    public void testListPersistenciaReceita() throws Exception { //INSERE DADOS DA RECEITA

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            List<Receita> lista = persistencia.listReceitas();

            if(!lista.isEmpty()) {
                for(Receita r: lista) {
                    System.out.println("Receita ID: "+r.getId()+ " Orientacao: "+r.getOrientacao());
                    persistencia.remover(r);
                }
            } else {
                System.out.println("Não encontrou o receita");
                //Produto p = (Produto) persistencia.find(Produto.class, 1);
                List<Produto> lista_prod = persistencia.listProdutos();
                Consulta c = new Consulta();
                c.setId("123");
                Receita end = new Receita();
                end.setOrientacao("orientacao");
                end.setConsulta(c);
                end.setProdutos(lista_prod);
                persistencia.persist(end); // insert na tabela
                System.out.println("Cadastrou o receita " + end.getId());

            }

            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    //@Test
    public void testFindProduto() throws Exception { //BUSCA PRODUTO POR ID
        PersistenciaJDBC persistencia = new PersistenciaJDBC();

        if(persistencia.conexaoAberta()) {
            Produto p = (Produto) persistencia.find(Produto.class, 3);

            if(p == null) {
                System.out.println("Elemento nao encontrado");
            } else {
                System.out.println("Produto encontrada\nId: " + p.getId() + " | Nome: " + p.getNome() + " | Valor: " + p.getValor()
                + " | Tipo Produto: " + p.getTipoProduto() + " | Fornecedor CPF: " + p.getFornecedor().getCpf());

            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com BD via JDBC");
        }
    }


    @Test
    public void testFindReceita() throws Exception { // BUSCA RECEITA POR ID
        PersistenciaJDBC persistencia = new PersistenciaJDBC();

        if(persistencia.conexaoAberta()) {
            Receita r = (Receita) persistencia.find(Receita.class, 2);

            if(r == null) {
                System.out.println("Elemento nao encontrado");
            } else {
                System.out.println("Receita encontrada\n\tId: " + r.getId() + " | Orientacao: " + r.getOrientacao() +
                        "| Consulta ID: " + r.getConsulta().getId());

                System.out.println("Produtos da receita: ");
                for(Produto p : r.getProdutos()){
                    System.out.println("\tId: " + p.getId() + " | Nome: " + p.getNome() + " | Valor: " + p.getValor()
                            + " | Tipo Produto: " + p.getTipoProduto() + " | Fornecedor CPF: " + p.getFornecedor().getCpf());
                }
            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com BD via JDBC");
        }
    }

    //@Test
    public void testListPersistenciaPessoa() throws Exception {

        // recupera a lista de Pessoas

        //imprimir na tela os dados de cada jogador e as suas respectivas patentes

        //alterar o jogador ao algum dado da tabela associativa.

        //remove as patentes do jogador (tb_jogador_patente), uma a uma

        //caso a lista de jogadores esteja vazia, insere um ou mais jogadores , bem como, vincula ao menos uma patente no jogador (tb_jogador_patente)
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            List<Fornecedor> list = persistencia.listFornecedor();

            list.forEach(j->System.out.println("Fornecedor: "+j.getNome()));

            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

}
