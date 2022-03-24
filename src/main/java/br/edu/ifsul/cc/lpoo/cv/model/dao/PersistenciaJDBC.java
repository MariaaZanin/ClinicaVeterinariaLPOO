package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.*;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

        }else if(c == Fornecedor.class){

            PreparedStatement ps = this.con.prepareStatement("select f.cnpj, f.ie, f.cpf, p.cep, p.complemento, p.data_cadastro, " +
                    "p.data_nascimento, p.email, p.endereco, p.nome, p.numero_celular, p.rg, p.senha from tb_fornecedor as f, tb_pessoa as p " +
                    "where p.cpf = f.cpf and f.cpf = ?");
            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Fornecedor f = new Fornecedor();
                f.setCnpj(rs.getString("Cnpj"));
                f.setIe(rs.getString("Ie"));
                f.setCpf(rs.getString("cpf"));

                f.setCep(rs.getString("CEP"));
                f.setComplemento(rs.getString("Complemento"));
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("Data_Cadastro").getTime());
                f.setData_cadastro(dtCad);
                Calendar dtNas = Calendar.getInstance();
                dtNas.setTimeInMillis(rs.getDate("Data_Nascimento").getTime());
                f.setData_nascimento(dtNas);
                f.setEmail(rs.getString("Email"));
                f.setEndereco(rs.getString("Endereco"));
                f.setNome(rs.getString("Nome"));
                f.setNumero_celular(rs.getString("Numero_Celular"));
                f.setRg(rs.getString("RG"));
                f.setSenha(rs.getString("Senha"));
                ps.close();

                return f;
            }
        }else if(c == Funcionario.class){

            PreparedStatement ps = this.con.prepareStatement("select f.cargo, f.numero_ctps, f.numero_pis, f.cpf, p.cep, p.complemento, p.data_cadastro, " +
                    "p.data_nascimento, p.email, p.endereco, p.nome, p.numero_celular, p.rg, p.senha from tb_funcionario as f, tb_pessoa as p " +
                    "where p.cpf = f.cpf and f.cpf = ?");
            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Funcionario f = new Funcionario();
                f.setCargo(Cargo.valueOf(rs.getString("cargo")));
                f.setNumero_ctps(rs.getString("Numero_ctps"));
                f.setNumero_pis(rs.getString("Numero_pis"));
                f.setCpf(rs.getString("cpf"));
                f.setCep(rs.getString("CEP"));
                f.setComplemento(rs.getString("Complemento"));
                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("Data_Cadastro").getTime());
                f.setData_cadastro(dtCad);
                Calendar dtNas = Calendar.getInstance();
                dtNas.setTimeInMillis(rs.getDate("Data_Nascimento").getTime());
                f.setData_nascimento(dtNas);
                f.setEmail(rs.getString("Email"));
                f.setEndereco(rs.getString("Endereco"));
                f.setNome(rs.getString("Nome"));
                f.setNumero_celular(rs.getString("Numero_Celular"));
                f.setRg(rs.getString("RG"));
                f.setSenha(rs.getString("Senha"));
                ps.close();

                return f;
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
        } else if (o instanceof Fornecedor) {
            Fornecedor f = (Fornecedor) o;

            if (f.getData_cadastro() == null) {
                PreparedStatement ps_pessoa = this.con.prepareStatement("insert into tb_pessoa"
                        + " (cpf, rg, nome, senha, numero_celular, email, cep, endereco, complemento, data_cadastro, data_nascimento, tipo)"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, 'fornecedor')");
                ps_pessoa.setString(1, f.getCpf());
                ps_pessoa.setString(2, f.getRg());
                ps_pessoa.setString(3, f.getNome());
                ps_pessoa.setString(4, f.getSenha());
                ps_pessoa.setString(5, f.getNumero_celular());
                ps_pessoa.setString(6, f.getEmail());
                ps_pessoa.setString(7, f.getCep());
                ps_pessoa.setString(8, f.getEndereco());
                ps_pessoa.setString(9, f.getComplemento());
                ps_pessoa.setTimestamp(10, new Timestamp(f.getData_nascimento().getTimeInMillis()));

                ps_pessoa.execute();

                PreparedStatement psf = this.con.prepareStatement("insert into tb_fornecedor "
                        + "(cnpj, ie, cpf) values (?, ?, ?) ");
                psf.setString(1, f.getCnpj());
                psf.setString(2, f.getIe());
                psf.setString(3, f.getCpf());

                psf.execute();
                //System.out.println("O Fornecedor com CPF = " + f.getCpf() + " foi cadastrado com sucesso!\n");
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set rg = ?, nome = ?, senha = ?, numero_celular = ?, "
                        + "email = ?, cep= ?, endereco = ?, complemento = ?, data_nascimento = ?, tipo = 'fornecedor'"
                        + "where cpf = ?");
                ps.setString(1, f.getRg());
                ps.setString(2, f.getNome());
                ps.setString(3, f.getSenha());
                ps.setString(4, f.getNumero_celular());
                ps.setString(5, f.getEmail());
                ps.setString(6, f.getCep());
                ps.setString(7, f.getEndereco());
                ps.setString(8, f.getComplemento());
                ps.setTimestamp(9, new Timestamp(f.getData_nascimento().getTimeInMillis()));
                ps.setString(10, f.getCpf());

                PreparedStatement psf = this.con.prepareStatement("update tb_fornecedor set cnpj = ?, ie = ? where cpf = ?");
                psf.setString(1, f.getCnpj());
                psf.setString(2, f.getIe());
                psf.setString(3, f.getCpf());

                ps.execute();
                psf.execute();
            }
        }else if (o instanceof Funcionario) {
            Funcionario f = (Funcionario) o;

            if (f.getData_cadastro() == null) {
                PreparedStatement ps_pessoa = this.con.prepareStatement("insert into tb_pessoa"
                        + " (cpf, rg, nome, senha, numero_celular, email, cep, endereco, complemento, data_cadastro, data_nascimento, tipo)"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, 'funcionario')");
                ps_pessoa.setString(1, f.getCpf());
                ps_pessoa.setString(2, f.getRg());
                ps_pessoa.setString(3, f.getNome());
                ps_pessoa.setString(4, f.getSenha());
                ps_pessoa.setString(5, f.getNumero_celular());
                ps_pessoa.setString(6, f.getEmail());
                ps_pessoa.setString(7, f.getCep());
                ps_pessoa.setString(8, f.getEndereco());
                ps_pessoa.setString(9, f.getComplemento());
                ps_pessoa.setTimestamp(10, new Timestamp(f.getData_nascimento().getTimeInMillis()));

                ps_pessoa.execute();

                PreparedStatement psf = this.con.prepareStatement("insert into tb_funcionario "
                        + "(cargo, numero_ctps, numero_pis, cpf) values (?, ?, ?, ?) ");
                psf.setString(1, f.getCargo().toString());
                psf.setString(2, f.getNumero_ctps());
                psf.setString(3, f.getNumero_pis());
                psf.setString(4, f.getCpf());

                psf.execute();
                //System.out.println("O Funcionario com CPF = " + f.getCpf() + " foi cadastrado com sucesso!\n");
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set rg = ?, nome = ?, senha = ?, numero_celular = ?, "
                        + "email = ?, cep= ?, endereco = ?, complemento = ?, data_nascimento = ?, tipo = 'funcionario'"
                        + "where cpf = ?");
                ps.setString(1, f.getRg());
                ps.setString(2, f.getNome());
                ps.setString(3, f.getSenha());
                ps.setString(4, f.getNumero_celular());
                ps.setString(5, f.getEmail());
                ps.setString(6, f.getCep());
                ps.setString(7, f.getEndereco());
                ps.setString(8, f.getComplemento());
                ps.setTimestamp(9, new Timestamp(f.getData_nascimento().getTimeInMillis()));
                ps.setString(10, f.getCpf());

                PreparedStatement psf = this.con.prepareStatement("update tb_funcionario set cargo = ?, numero_ctps = ?, numero_pis = ? where cpf = ?");
                psf.setString(1, f.getCargo().toString());
                psf.setString(2, f.getNumero_ctps());
                psf.setString(3, f.getNumero_pis());
                psf.setString(4, f.getCpf());

                ps.execute();
                psf.execute();
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception{
        if(o instanceof Fornecedor){
            Fornecedor f = (Fornecedor) o;
            PreparedStatement ps1 = this.con.prepareStatement("delete from tb_fornecedor where cpf = ?");// deleta a informação da tabela receita_fornecedor
            ps1.setString(1, f.getCpf());
            ps1.execute();

            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");// deleta a informação da tabela receita_pessoa
            ps2.setString(1, f.getCpf());
            ps2.execute();
        }else if(o instanceof Produto){
            Produto p = (Produto) o; //converter o para o e que é do tipo Produto

            //PreparedStatement ps2 = this.con.prepareStatement("delete from tb_receita_produto where produto_id = ? ");// deleta a informação da tabela receita_produto
            //ps2.setInt(1, p.getId());
            //ps2.execute();

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

        }else if(o instanceof Funcionario) {
            Funcionario f = (Funcionario) o;
            PreparedStatement ps1 = this.con.prepareStatement("delete from tb_funcionario where cpf = ?");// deleta a informação da tabela receita_fornecedor
            ps1.setString(1, f.getCpf());
            ps1.execute();

            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");// deleta a informação da tabela receita_pessoa
            ps2.setString(1, f.getCpf());
            ps2.execute();
        }
    }

    @Override
    public List<Produto> listProdutos() throws Exception {

        List<Produto> listaProduto = null;

        PreparedStatement ps = this.con.prepareStatement("select id, nome, valor, quantidade, tipoproduto, fornecedor_id from tb_produto order by id asc;");

        ResultSet rs = ps.executeQuery();

        listaProduto = new ArrayList();
        while(rs.next()) {
            Produto end = new Produto();

            end.setId(rs.getInt("id"));
            end.setNome(rs.getString("nome"));
            end.setValor(rs.getFloat("valor"));
            end.setQuantidade(rs.getFloat("quantidade"));
            end.setTipoProduto(TipoProduto.valueOf(rs.getString("tipoproduto")));
            Fornecedor f = new Fornecedor();
            f.setCpf(rs.getString("fornecedor_id"));
            end.setFornecedor(f);

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

        } else if(c == Produto.class){
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

        } else if (c == Fornecedor.class) {
            List<Fornecedor> listaFornecedor = new ArrayList<>();

            PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.cep, p.complemento," +
                    " p.data_nascimento, p.email, p.endereco, p.nome, p.numero_celular, p.rg,f.cnpj,f.ie , " +
                    "p.senha from tb_fornecedor f join tb_pessoa p on p.cpf = f.cpf");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setCpf(rs.getString("cpf"));
                f.setCep(rs.getString("cep"));
                f.setComplemento(rs.getString("complemento"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(rs.getDate("data_nascimento"));
                f.setData_nascimento(calendar);
                f.setEmail(rs.getString("email"));
                f.setEndereco(rs.getString("endereco"));
                f.setNome(rs.getString("nome"));
                f.setNumero_celular(rs.getString("numero_celular"));
                f.setRg(rs.getString("rg"));
                f.setSenha(rs.getString("senha"));
                f.setCnpj(rs.getString("cnpj"));
                f.setIe(rs.getString("ie"));

                listaFornecedor.add(f);
            }
            return listaFornecedor;
        }

            return null;
    }

    @Override
    public List<Fornecedor> listFornecedor() throws Exception {
        List<Fornecedor> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.cep, pes.complemento, pes.data_nascimento, pes.data_cadastro, pes.email, pes.endereco, pes.nome, pes.numero_celular, pes.rg, pes.senha, forn.ie, forn.cnpj from tb_pessoa as pes INNER JOIN tb_fornecedor as forn on pes.cpf = forn.cpf;\n");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while(rs.next()) {
            Fornecedor forn = new Fornecedor();
            forn.setCpf(rs.getString("cpf"));
            forn.setComplemento(rs.getString("complemento"));
            forn.setCep(rs.getString("cep"));

            Calendar dtCadastro = Calendar.getInstance();
            dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            forn.setData_cadastro(dtCadastro);
            if(rs.getDate("data_nascimento") != null) {
                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                forn.setData_nascimento(dtNascimento);
            }

            forn.setEmail(rs.getString("email"));
            forn.setEndereco(rs.getString("endereco"));
            forn.setNome(rs.getString("nome"));
            forn.setNumero_celular(rs.getString("numero_celular"));
            forn.setRg(rs.getString("rg"));
            forn.setSenha(rs.getString("senha"));
            forn.setCnpj(rs.getString("cnpj"));
            forn.setIe(rs.getString("ie"));

            lista.add(forn);
        }

        return lista;
    }

    @Override
    public List<Funcionario> listFuncionario() throws Exception {
        List<Funcionario> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select pes.cpf, pes.cep, pes.complemento, "
                + "pes.data_nascimento, pes.data_cadastro, pes.email, pes.endereco, pes.nome, pes.numero_celular,"
                + " pes.rg, pes.senha, func.cargo, func.numero_ctps, func.numero_pis from tb_pessoa as pes INNER JOIN tb_funcionario as func "
                + "on pes.cpf = func.cpf;\n");

        ResultSet rs = ps.executeQuery();

        lista = new ArrayList();
        while(rs.next()) {
            Funcionario func = new Funcionario();
            func.setCpf(rs.getString("cpf"));
            func.setComplemento(rs.getString("complemento"));
            func.setCep(rs.getString("cep"));

            Calendar dtCadastro = Calendar.getInstance();
            dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            func.setData_cadastro(dtCadastro);
            if(rs.getDate("data_nascimento") != null) {
                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                func.setData_nascimento(dtNascimento);
            }

            func.setEmail(rs.getString("email"));
            func.setEndereco(rs.getString("endereco"));
            func.setNome(rs.getString("nome"));
            func.setNumero_celular(rs.getString("numero_celular"));
            func.setRg(rs.getString("rg"));
            func.setSenha(rs.getString("senha"));
            func.setCargo(Cargo.valueOf(rs.getString("cargo")));
            func.setNumero_ctps(rs.getString("numero_ctps"));
            func.setNumero_pis(rs.getString("numero_pis"));

            lista.add(func);
        }

        return lista;
    }

    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {

        Pessoa f = null;

        PreparedStatement ps =
                this.con.prepareStatement("select p.cpf, p.nome, p.senha from tb_pessoa p " +
                        " inner join tb_funcionario f ON p.cpf = f.cpf where p.cpf = ? and p.senha = ? ");

        ps.setString(1, cpf);
        ps.setString(2, senha);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            f = new Pessoa();
            f.setCpf(rs.getString("cpf"));
            f.setNome(rs.getString("nome"));
            f.setSenha(rs.getString("senha"));
        }

        ps.close();
        return f;
    }
}
