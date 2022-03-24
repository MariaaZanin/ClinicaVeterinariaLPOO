package br.edu.ifsul.cc.lpoo.cv.gui.produto.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.*;
import javafx.scene.input.DataFormat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class JPanelAProdutoFormulario extends JPanel implements ActionListener {

        private JPanelAProduto pnlAProduto;
        private Controle controle;

        private BorderLayout borderLayout;
        private JTabbedPane tbpAbas;

        private JPanel pnlDadosCadastrais;
        private JPanel pnlCentroDadosCadastrais;

        private GridBagLayout gridBagLayoutDadosCadastrais;
        private JLabel lblNickname;
        private JTextField txfNickname;

        private JLabel lblId;
        private JTextField txfId;

        private JLabel lblQuantidade;
        private JTextField txfQuantidade;

        private JLabel lblTipoProduto;
        private JComboBox cbxTipoProduto;

        private JLabel lblValor;
        private JTextField txfValor;

        private JLabel lblFornecedor;
        private JComboBox cbxFornecedor;

        private Produto produto;
        //private SimpleDateFormat format;

        private JPanel pnlSul;
        private JButton btnGravar;
        private JButton btnCancelar;

        public JPanelAProdutoFormulario(JPanelAProduto pnlAProduto, Controle controle) {

            this.pnlAProduto = pnlAProduto;
            this.controle = controle;

            initComponents();
        }

        public void populaTipoProduto() {
            cbxTipoProduto.removeAllItems();
            DefaultComboBoxModel model = (DefaultComboBoxModel) cbxTipoProduto.getModel();

            model.addElement("Selecione");
            try {
                List<TipoProduto> listTipoProdutos = Arrays.asList(TipoProduto.values());

                listTipoProdutos.forEach(tp -> {
                    model.addElement(tp);
                });
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao listar os Tipos Produtos:"+e.getLocalizedMessage(), "Tipos Produtos", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        public void populaFornecedor(){
            cbxFornecedor.removeAllItems();

            DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxFornecedor.getModel();

            model.addElement("Selecione");
                try {

                    List<Fornecedor> listFornecedor = controle.getConexaoJDBC().mostraTudo(Fornecedor.class);
                    for(Fornecedor fornecedor : listFornecedor){
                        model.addElement(fornecedor);
                    }

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(this, "Erro ao listar Fornecedores -:"+ex.getLocalizedMessage(), "Fornecedores", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
        }

        public Produto getProdutobyFormulario() {

            //validacao do formulario
            String msg = "";
            if(txfNickname.getText().trim().length() < 4) {
                msg += "Nome invalido, informe um nome com ao menos 4 digitos \n";

            }else if(txfQuantidade.getText().trim().length() < 1){
                msg += "Quantidade invalido, informe uma quantidade com ao menos 1 digitos \n";

            }else if(txfValor.getText().trim().length() < 2){
                msg += "Valor invalido, informe um valor com ao menos 2 digitos \n";

            }
            if(msg != ""){
                JOptionPane.showMessageDialog(this, msg);
            }else {

                Produto p = new Produto();
                p.setNome(txfNickname.getText().trim());
                if(produto != null) p.setId(produto.getId());
                p.setQuantidade(Float.valueOf(txfQuantidade.getText()));
                p.setTipoProduto((TipoProduto) cbxTipoProduto.getSelectedItem());
                p.setValor(Float.valueOf(txfValor.getText()));
                p.setFornecedor((Fornecedor) cbxFornecedor.getSelectedItem());

                return p;
            }

            return null;
        }

        public void setProdutoFormulario(Produto p) {

            if (p == null) {//se o parametro estiver nullo, limpa o formulario
                txfNickname.setText("");
                txfQuantidade.setText("");
                cbxTipoProduto.setSelectedIndex(0);
                txfValor.setText("");
                cbxFornecedor.setSelectedIndex(0);

                txfNickname.setEditable(true);
                produto = null;
            } else {
                produto = p;
                txfNickname.setText(produto.getNome());
                txfQuantidade.setText(p.getQuantidade().toString());
                cbxTipoProduto.getModel().setSelectedItem(produto.getTipoProduto());
                txfValor.setText(p.getValor().toString());
                cbxFornecedor.getModel().setSelectedItem(p.getFornecedor());

            }

        }

        private void initComponents() {

            borderLayout = new BorderLayout();
            this.setLayout(borderLayout);

            tbpAbas = new JTabbedPane();
            this.add(tbpAbas, BorderLayout.CENTER);

            pnlDadosCadastrais = new JPanel();
            gridBagLayoutDadosCadastrais = new GridBagLayout();
            pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);

            lblNickname = new JLabel("Nome:");
            GridBagConstraints posicionador = new GridBagConstraints();
            posicionador.gridy = 0;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblNickname, posicionador);

            txfNickname = new JTextField(20);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 0;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfNickname, posicionador);

            lblQuantidade = new JLabel("Quantidade:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 1;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblQuantidade, posicionador);

            txfQuantidade = new JTextField(5);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 1;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfQuantidade, posicionador);

            lblTipoProduto = new JLabel("Tipo Produto:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 2;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblTipoProduto, posicionador);

            cbxTipoProduto = new JComboBox();
            posicionador = new GridBagConstraints();
            posicionador.gridy = 2;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(cbxTipoProduto, posicionador);

            lblValor = new JLabel("Valor:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 3;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblValor, posicionador);

            txfValor = new JTextField(7);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 3;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfValor, posicionador);

            lblFornecedor = new JLabel("Fornecedor:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 4;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblFornecedor, posicionador);

            cbxFornecedor = new JComboBox();
            posicionador = new GridBagConstraints();
            posicionador.gridy = 4;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(cbxFornecedor, posicionador);

            tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

            pnlSul = new JPanel();
            pnlSul.setLayout(new FlowLayout());

            btnGravar = new JButton("Gravar");
            btnGravar.addActionListener(this);
            btnGravar.setFocusable(true);    //acessibilidade
            btnGravar.setToolTipText("btnGravarProduto"); //acessibilidade
            btnGravar.setMnemonic(KeyEvent.VK_G);
            btnGravar.setActionCommand("botao_gravar_formulario_produto");

            pnlSul.add(btnGravar);

            btnCancelar = new JButton("Cancelar");
            btnCancelar.addActionListener(this);
            btnCancelar.setFocusable(true);    //acessibilidade
            btnCancelar.setToolTipText("btnCancelarProduto"); //acessibilidade
            btnCancelar.setActionCommand("botao_cancelar_formulario_Produto");

            pnlSul.add(btnCancelar);

            this.add(pnlSul, BorderLayout.SOUTH);

        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

                Produto p = getProdutobyFormulario();//recupera os dados do formulÃ¡rio

                if (p != null) {
                    try {
                        //System.out.println("DADOS Produto: " + p.getId());
                        pnlAProduto.getControle().getConexaoJDBC().persist(p);

                        JOptionPane.showMessageDialog(this, "Produto armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);

                        pnlAProduto.showTela("tela_produto_listagem");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar Produto! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }

                } else {

                    JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
                }


            } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
                pnlAProduto.showTela("tela_produto_listagem");
            }
        }
}

