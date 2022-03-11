package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cv.gui.fornecedor.JPanelFornecedor;
import br.edu.ifsul.cc.lpoo.cv.gui.fornecedor.acessibilidade.JPanelAFornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import javax.swing.JOptionPane;

public class Controle {

    private PersistenciaJDBC conexaoJDBC;

    private JFramePrincipal frame;

    private JPanelAutenticacao pnlAutenticacao;

    private JMenuBarHome menuBar;

    private JPanelHome pnlHome;

    private JPanelFornecedor  pnlFornecedor; // painel de manutencao para fornecedor
    private JPanelAFornecedor pnlAFornecedor; // painel de manutencao para fornecedor


    public Controle(){

    }

    public boolean conectarBD() throws Exception {

        conexaoJDBC = new PersistenciaJDBC();

        if(conexaoJDBC!= null){

            return conexaoJDBC.conexaoAberta();
        }

        return false;
    }

    public void fecharBD(){

        System.out.println("Fechando conexao com o Banco de Dados");
        conexaoJDBC.fecharConexao();
    }

    public void initComponents(){
        //inicia a interface gráfica.
        //"caminho feliz" : passo 5

        frame = new JFramePrincipal();

        pnlAutenticacao = new JPanelAutenticacao(this);

        menuBar = new JMenuBarHome(this);

        pnlHome = new JPanelHome(this);

        pnlFornecedor = new JPanelFornecedor(this);

        pnlAFornecedor = new JPanelAFornecedor(this);

        frame.addTela(pnlAutenticacao, "tela_autenticacao");
        frame.addTela(pnlHome, "tela_home");

        frame.addTela(pnlAFornecedor, "tela_fornecedor");//carta 3 - poderia adicionar opcionalmente: pnlJogador
        frame.addTela(pnlFornecedor, "tela_fornecedor_designer");//carta 3 - poderia adicionar opcionalmente: pnlJogador

        frame.showTela("tela_autenticacao");

        frame.setVisible(true);


    }

    public void autenticar(String cpf, String senha) {

        try{
            Fornecedor f =  conexaoJDBC.doLogin(cpf, senha);

            if(f != null){


                JOptionPane.showMessageDialog(pnlAutenticacao, "CPF "+ f.getCpf() +" autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);

                frame.setJMenuBar(menuBar);
                frame.showTela("tela_home");

            }else{
                JOptionPane.showMessageDialog(pnlAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(pnlAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showTela(String nomeTela){

        if(nomeTela.equals("tela_autenticacao")){

            //pnlAutenticacao.cleanForm();
            frame.showTela(nomeTela);
            pnlAutenticacao.requestFocus();

        }else{
            pnlAFornecedor.showTela("tela_fornecedor_listagem");
            frame.showTela(nomeTela);
        }
    }

    /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }

}