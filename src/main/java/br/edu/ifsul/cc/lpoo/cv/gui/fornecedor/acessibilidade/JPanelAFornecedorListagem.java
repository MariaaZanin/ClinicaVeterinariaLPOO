package br.edu.ifsul.cc.lpoo.cv.gui.fornecedor.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JPanelAFornecedorListagem extends JPanel implements ActionListener {

        private JPanelAFornecedor pnlAFornecedor;
        private Controle controle;

        private BorderLayout borderLayout;
        private JPanel pnlNorte;
        private JLabel lblFiltro;
        private JTextField txfFiltro;
        private JButton btnFiltro;

        private JPanel pnlCentro;
        private JScrollPane scpListagem;
        private JTable tblListagem;
        private DefaultTableModel modeloTabela;

        private JPanel pnlSul;
        private JButton btnNovo;
        private JButton btnAlterar;
        private JButton btnRemover;

        private SimpleDateFormat format;

        public JPanelAFornecedorListagem(JPanelAFornecedor pnlAFornecedor, Controle controle){

            this.pnlAFornecedor = pnlAFornecedor;
            this.controle = controle;

            initComponents();
        }

        public void populaTable(){
            DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel();//recuperacao do modelo da tabela

            model.setRowCount(0);//elimina as linhas existentes (reset na tabela)
            try{
                List<Fornecedor> listFornecedor = controle.getConexaoJDBC().listFornecedor();
                for(Fornecedor f : listFornecedor){
                    model.addRow(new Object[]{f.getNome(), f.getCpf(), f.getCnpj(), f.getIe()});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao listar Fornecedores -:"+ex.getLocalizedMessage(), "Fornecedores", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

        private void initComponents(){

            borderLayout = new BorderLayout();
            this.setLayout(borderLayout);//seta o gerenciado border para este painel

            pnlNorte = new JPanel();
            pnlNorte.setLayout(new FlowLayout());

            lblFiltro = new JLabel("Filtrar por nome de Fornecedor:");
            pnlNorte.add(lblFiltro);

            txfFiltro = new JTextField(20);
            pnlNorte.add(txfFiltro);

            btnFiltro = new JButton("Filtrar");
            btnFiltro.addActionListener(this);
            btnFiltro.setFocusable(true);    //acessibilidade
            btnFiltro.setToolTipText("btnFiltrar"); //acessibilidade
            btnFiltro.setActionCommand("botao_filtro");
            pnlNorte.add(btnFiltro);

            this.add(pnlNorte, BorderLayout.NORTH);//adiciona o painel na posicao norte.

            pnlCentro = new JPanel();
            pnlCentro.setLayout(new BorderLayout());

            scpListagem = new JScrollPane();
            tblListagem =  new JTable();

            modeloTabela = new DefaultTableModel(
                    new String [] { "Nome", "CPF", "CNPJ", "IE",}, 0);

            tblListagem.setModel(modeloTabela);
            scpListagem.setViewportView(tblListagem);

            pnlCentro.add(scpListagem, BorderLayout.CENTER);

            this.add(pnlCentro, BorderLayout.CENTER);//adiciona o painel na posicao norte.

            filtrar();

            pnlSul = new JPanel();
            pnlSul.setLayout(new FlowLayout());

            btnNovo = new JButton("Novo");
            btnNovo.addActionListener(this);
            btnNovo.setFocusable(true);    //acessibilidade
            btnNovo.setToolTipText("btnNovo"); //acessibilidade
            btnNovo.setMnemonic(KeyEvent.VK_N);
            btnNovo.setActionCommand("botao_novo");

            pnlSul.add(btnNovo);

            btnAlterar = new JButton("Editar");
            btnAlterar.addActionListener(this);
            btnAlterar.setFocusable(true);  //acessibilidade
            btnAlterar.setToolTipText("btnAlterar"); //acessibilidade
            btnAlterar.setActionCommand("botao_alterar");

            pnlSul.add(btnAlterar);

            btnRemover = new JButton("Remover");
            btnRemover.addActionListener(this);
            btnRemover.setFocusable(true); //acessibilidade
            btnRemover.setToolTipText("btnRemvoer"); //acessibilidade
            btnRemover.setActionCommand("botao_remover");

            pnlSul.add(btnRemover);//adiciona o botao na fila organizada pelo flowlayout


            this.add(pnlSul, BorderLayout.SOUTH);//adiciona o painel na posicao norte.

            format = new SimpleDateFormat("dd/MM/yyyy");

        }

        @Override
        public void actionPerformed(ActionEvent arg0) {

            if(arg0.getActionCommand().equals(btnNovo.getActionCommand())){

                pnlAFornecedor.showTela("tela_fornecedor_formulario");

                pnlAFornecedor.getFormulario().setFornecedorFormulario(null); //limpando o formul??rio.

            }else if(arg0.getActionCommand().equals(btnAlterar.getActionCommand())){
                int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
                System.out.println("Indice: " + indice);
                if(indice > -1){
                    try {
                        PersistenciaJDBC persistencia = new PersistenciaJDBC();
                        Fornecedor f = new Fornecedor();

                        DefaultTableModel model = (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table
                        Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada
                        System.out.println("Linha: " + linha);
                        f = (Fornecedor) persistencia.find(f.getClass(), linha.get(1));
                        //Fornecedor f = (Fornecedor) linha; //model.addRow(new Object[]{u, u.getNome(), ...
                        System.out.println("Fornecedor: " + f);
                        pnlAFornecedor.showTela("tela_fornecedor_formulario");
                        pnlAFornecedor.getFormulario().setFornecedorFormulario(f);
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(this, "Erro ao editar Fornecedor -:"+ex.getLocalizedMessage(), "Fornecedores", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edi????o", JOptionPane.INFORMATION_MESSAGE);
                }


            }else if(arg0.getActionCommand().equals(btnRemover.getActionCommand())){
                int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
                if(indice > -1){
                    try {
                        DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table
                        Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada
                        String s = (String) linha.get(1);
                        Fornecedor f = new Fornecedor();
                        PersistenciaJDBC persistencia = new PersistenciaJDBC();
                        f = (Fornecedor) persistencia.find(f.getClass(), s);
                        pnlAFornecedor.getControle().getConexaoJDBC().remover(f);
                        JOptionPane.showMessageDialog(this, "Fornecedor removido!", "Fornecedor", JOptionPane.INFORMATION_MESSAGE);
                        populaTable(); //refresh na tabela
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao remover Fornecedor -:"+ex.getLocalizedMessage(), "Fornecedores", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remo????o", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        }

        public void filtrar(){
            final TableRowSorter<TableModel> ordenador = new TableRowSorter<TableModel>(modeloTabela);
            tblListagem.setRowSorter(ordenador);
            btnFiltro.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String text = txfFiltro.getText();
                    if(text.length() == 0) {
                        ordenador.setRowFilter(null);
                    } else {
                        try {
                            ordenador.setRowFilter(RowFilter.regexFilter(text, 0));
                        } catch(PatternSyntaxException pse) {
                            System.out.println("Erro ao filtrar");
                        }
                    }
                }
            });
        }
}

