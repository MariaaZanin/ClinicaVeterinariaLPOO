package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaJDBC implements InterfacePersistencia{

    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5433/MariaEduarda_cv_lpoo_2022";
    private Connection con = null;

    public PersistenciaJDBC () throws Exception{
        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");

        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);

    }

    @Override
    public Boolean conexaoAberta(){
        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    @Override
    public void fecharConexao(){
        try{
            this.con.close();//fecha a conexao.
            System.out.println("\nFechou conexao JDBC");
        }catch(SQLException e){
            e.printStackTrace();//gera uma pilha de erro na saida.
        }

    }

    @Override
    public Object find(Class c, Object id) throws Exception{
        if(c == Produto.class){

            //tb_produto
            PreparedStatement ps = this.con.prepareStatement("select id, nome, valor, quantidade,tipoproduto, fornecedor_id from tb_produto where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getFloat("valor"));
                p.setQuantidade(rs.getFloat("quantidade"));
                p.setTipoProduto(TipoProduto.getTipoProduto(rs.getString("tipoproduto")));
                Fornecedor f = new Fornecedor();
                f.setCpf(rs.getString("fornecedor_id"));
                p.setFornecedor(f);

                ps.close();

                return p;
            }

        }else if(c == Receita.class){
            //tb_receita
            PreparedStatement ps = this.con.prepareStatement("select id, orientacao, consulta_id from tb_receita where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Receita r = new Receita();
                r.setId(rs.getInt("id"));
                r.setOrientacao(rs.getString("orientacao"));
                Consulta consulta = new Consulta();
                consulta.setId(rs.getString("consulta_id"));
                r.setConsulta(consulta);

                PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.nome, p.quantidade, p.tipoproduto, p.valor, p.fornecedor_id " +
                        "FROM tb_receita_produto rp, tb_produto p WHERE p.id = rp.produto_id AND rp.receita_id = ?");
                ps2.setInt(1, Integer.parseInt(id.toString()));

                List<Produto> lista = new ArrayList<Produto>();
                ResultSet rs2 = ps2.executeQuery();

                while(rs2.next()){

                    Produto p = new Produto();
                    p.setId(rs2.getInt("id"));
                    p.setNome(rs2.getString("nome"));
                    p.setValor(rs2.getFloat("valor"));
                    p.setQuantidade(rs2.getFloat("quantidade"));
                    TipoProduto tp = TipoProduto.getTipoProduto(rs2.getString("tipoproduto"));
                    p.setTipoProduto(tp);
                    Fornecedor f = new Fornecedor();
                    f.setCpf(rs2.getString("fornecedor_id"));
                    p.setFornecedor(f);

                    lista.add(p);
                }

                r.setProdutos(lista);
                ps2.close();
                ps.close();

                return r;
            }

        }
        return null;
    }

    @Override
    public void persist(Object o) throws Exception{
        // throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates
        // Descobrir a instancia do Object o

        if(o instanceof Produto) {
            Produto p = (Produto) o; // Converter o "o" para Produto

            // Descobrir qual operacao deve realizar (insert ou update)
            if(p.getId() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_produto (id, nome, valor, quantidade, tipoproduto, fornecedor_id) values (nextval('seq_produto'), ?, ?, ?, ?, ?);",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getValor());
                ps.setFloat(3, p.getQuantidade());
                ps.setString(4, p.getTipoProduto().toString());
                ps.setString(5, p.getFornecedor().getCpf());

                ps.execute();

                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next()) {
                    p.setId(rs.getInt(1));
                }
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_produto set nome = ?, valor = ?, quantidade = ?, tipoproduto = ?, fornecedor_id = ? where id = ?;");
                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getValor());
                ps.setFloat(3, p.getQuantidade());
                ps.setString(4, p.getTipoProduto().toString());
                ps.setString(5, p.getFornecedor().getCpf());
                ps.setInt(6, p.getId());

                ps.execute();
            }

        } else if (o instanceof Receita) {
            Receita r = (Receita) o; // Converter o "o" para Receita

            // Descobrir qual operacao deve realizar (insert ou update)
            if(r.getId() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_receita (id, orientacao, consulta_id) values (nextval('seq_receita'), ?, ?);",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, r.getOrientacao());
                ps.setString(2, r.getConsulta().getId());

                ps.execute();

                ResultSet rs = ps.getGeneratedKeys();

                //LOOP PERCORRER LISTA DE PRODUTOS E INSERIR NO TB_RECEITA_PRODUTO (2 IDS)
                PreparedStatement psProd_Receita = this.con.prepareStatement("insert into tb_receita_produto (receita_id, produto_id) values ( ?, ?);",
                        Statement.RETURN_GENERATED_KEYS);
                List<Produto> lista = r.getProdutos();
                rs.next();
                int id_receita = rs.getInt("id");
                for(Produto p : lista){
                    psProd_Receita.setInt(1, id_receita);
                    psProd_Receita.setInt(2, p.getId());
                    psProd_Receita.execute();
                }

                if(rs.next()) {
                    r.setId(rs.getInt(1));
                }
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_receita set orientacao = ?, consulta_id = ? where id = ?;");
                ps.setString(1, r.getOrientacao());
                ps.setString(2, r.getConsulta().getId());
                ps.setInt(3, r.getId());

                ps.execute();
            }
        }

    }

    @Override
    public void remover(Object o) throws Exception{
        if(o instanceof Produto){

            Produto p = (Produto) o; //converter o para o e que é do tipo Produto

            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_receita_produto where produto_id = ? ");// deleta a informação da tabela receita_produto
            ps2.setInt(1, p.getId());
            ps2.execute();

            PreparedStatement ps = this.con.prepareStatement("delete from tb_produto where id = ? ");
            ps.setInt(1, p.getId());
            ps.execute();

        }else if(o instanceof Receita){
            Receita r = (Receita) o; //converter o para o e que é do tipo Receita

            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_receita_produto where receita_id = ? ");// deleta a informação da tabela receita_produto
            ps2.setInt(1, r.getId());
            ps2.execute();

            PreparedStatement ps = this.con.prepareStatement("delete from tb_receita where id = ?");
            ps.setInt(1, r.getId());
            ps.execute();
        }
    }

    @Override
    public List<Produto> listProdutos() throws Exception {

        List<Produto> listaProduto = null;

        PreparedStatement ps = this.con.prepareStatement("select id, nome, valor, quantidade from tb_produto order by id asc;");

        ResultSet rs = ps.executeQuery();

        listaProduto = new ArrayList();
        while(rs.next()) {
            Produto end = new Produto();

            end.setId(rs.getInt("id"));
            end.setNome(rs.getString("nome"));
            end.setValor(rs.getFloat("valor"));
            end.setQuantidade(rs.getFloat("quantidade"));

            listaProduto.add(end); // Adiciona na lista o objeto que contém as informações obtidas do banco pelo ResultSet
        }

        return listaProduto;
    }

    @Override
    public List<Receita> listReceitas() throws Exception {
        List<Receita> listaReceita = null;

        PreparedStatement ps = this.con.prepareStatement("select id, orientacao from tb_receita order by id asc;");

        ResultSet rs = ps.executeQuery();

        listaReceita = new ArrayList();
        while(rs.next()) {
            Consulta con = new Consulta();
            con.setId("123");
            Receita end = new Receita();
            end.setId(rs.getInt("id"));
            end.setOrientacao(rs.getString("orientacao"));
            end.setConsulta(con);

            listaReceita.add(end); // Adiciona na lista o objeto que contém as informações obtidas do banco pelo ResultSet
        }

        return listaReceita;
    }

    @Override
    public List mostraTudo(Class c) throws Exception {
        if (c == Receita.class) {
            List<Receita> lista = new ArrayList<>();

            PreparedStatement ps = this.con.prepareStatement("select id, orientacao, consulta_id from tb_receita order by id asc");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //POPULANDO COM AS INFORMAÇÕES QUE RECEBE
                Receita r = new Receita();
                r.setId(rs.getInt("id"));
                r.setOrientacao(rs.getString("orientacao"));
                Consulta cons = new Consulta();
                cons.setId(rs.getString("consulta_id"));
                r.setConsulta(cons);
                //COMPARANDO OS ID produto E receita. E BUSCA OS PRODUTOS DESSA RECEITA
                PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.nome, p.quantidade, p.tipoproduto, p.valor, p.fornecedor_id " +
                        "from tb_receita_produto rp, tb_produto p where p.id = rp.produto_id and rp.receita_id = ?");
                ps2.setInt(1, Integer.parseInt(r.getId().toString()));
                ResultSet rs2 = ps2.executeQuery();
                List listaProduto = new ArrayList<Produto>();
                while (rs2.next()) {
                    Produto p = new Produto();
                    p.setId(rs2.getInt("id"));
                    p.setNome(rs2.getString("nome"));
                    p.setQuantidade(rs2.getFloat("quantidade"));
                    TipoProduto tipoProduto = TipoProduto.getTipoProduto(rs2.getString("tipoproduto"));
                    p.setTipoProduto(tipoProduto);
                    p.setValor(rs2.getFloat("valor"));
                    Fornecedor f = new Fornecedor();
                    f.setCpf(rs2.getString("fornecedor_id"));
                    p.setFornecedor(f);

                    listaProduto.add(p);

                }
                r.setProdutos(listaProduto);

                lista.add(r);

            }
            return lista;

        }else{
            if(c == Produto.class){
                List<Produto> listaProduto = new ArrayList<>();
                PreparedStatement ps = this.con.prepareStatement("select id, nome, quantidade, tipoproduto, valor, fornecedor_id from tb_produto order by id asc");

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    //POPULANDO COM AS INFORMAÇÕES QUE RECEBE
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setQuantidade(rs.getFloat("quantidade"));
                    p.setNome(rs.getString("nome"));
                    p.setValor(rs.getFloat("valor"));
                    TipoProduto tipoProduto = TipoProduto.getTipoProduto(rs.getString("tipoproduto"));
                    p.setTipoProduto(tipoProduto);
                    Fornecedor f = new Fornecedor();
                    f.setCpf(rs.getString("fornecedor_id"));
                    p.setFornecedor(f);

                    listaProduto.add(p);
                }
                return listaProduto;
            }
        }
        return null;
    }

}
