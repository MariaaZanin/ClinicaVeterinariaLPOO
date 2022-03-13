package br.edu.ifsul.cc.lpoo.cv.gui.fornecedor.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
//import br.edu.ifsul.cc.lpoo.cv.model.Endereco;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;
import javafx.scene.input.DataFormat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

public class JPanelAFornecedorFormulario extends JPanel implements ActionListener {

        private JPanelAFornecedor pnlAFornecedor;
        private Controle controle;

        private BorderLayout borderLayout;
        private JTabbedPane tbpAbas;

        private JPanel pnlDadosCadastrais;
        private JPanel pnlCentroDadosCadastrais;

        private GridBagLayout gridBagLayoutDadosCadastrais;
        private JLabel lblNickname;
        private JTextField txfNickname;

        private JLabel lblSenha;
        private JPasswordField txfSenha;

        private JLabel lblCnpj;
        private JTextField txfCnpj;

        private JLabel lblIe;
        private JTextField txfIe;

        private JLabel lblCpf;
        private JTextField txfCpf;

        private JLabel lblCep;
        private JTextField txfCep;

        private JLabel lblComplemento;
        private JTextField txfComplemento;

        private JLabel lblDataCadastro;
        private JTextField txfDataCadastro;

        private JLabel lblDataNascimento;
        private JTextField txfDataNascimento;

        private JLabel lblEmail;
        private JTextField txfEmail;

        private JLabel lblEndereco;
        private JTextField txfEndereco;

        private JLabel lblNumeroCelular;
        private JTextField txfNumeroCelular;

        private JLabel lblRg;
        private JTextField txfRg;

        private Fornecedor fornecedor;
        private SimpleDateFormat format;

        private JPanel pnlSul;
        private JButton btnGravar;
        private JButton btnCancelar;

        public JPanelAFornecedorFormulario(JPanelAFornecedor pnlAFornecedor, Controle controle) {

            this.pnlAFornecedor = pnlAFornecedor;
            this.controle = controle;

            initComponents();

        }

        public Fornecedor getFornecedorbyFormulario() {

            //validacao do formulario
            //validacao do formulario
            String msg = "";
            if(txfNickname.getText().trim().length() < 4)
                msg += "Nome invalido, informe um nome com ao menos 4 digitos \n";

            else if(new String(txfSenha.getPassword()).trim().length() < 3){
                msg += "Senha invalida, informe uma senha com ao menos 3 digitos \n";

            }else if(txfCpf.getText().trim().length() != 11){
                msg += "Cpf invalido, informe um CPF com 11 digitos \n";

            }else if(txfCep.getText().trim().length() != 8){
                msg += "CEP invalido, informe um CEP com 8 digitos \n";

            }else if(txfComplemento.getText().trim().length() < 5){
                msg += "Complemento invalido, informe um complemento com ao menos 5 digitos \n";

            }else if(txfEmail.getText().trim().length() < 10){
                msg += "Email invalido, informe um email com ao menos 10 digitos \n";

            }else if(txfEndereco.getText().trim().length() < 7){
                msg += "Endereco invalido, informe um endereco com ao menos 7 digitos \n";

            }else if(txfNumeroCelular.getText().trim().length() < 9){
                msg += "Telefone invalido, informe um email com ao menos 9 digitos \n";

            }else if(txfRg.getText().trim().length() != 10){
                msg += "RG invalido, informe um RG com 10 digitos \n";

            }else if (txfCnpj.getText().trim().length() < 10) {
                msg += "CNPJ invalido, informe um CNPJ com ao menos 10 digitos \n";

            } else if (txfIe.getText().trim().length() < 6) {
                msg += "IE invalido, informe um IE com ao menos 6 digitos \n";
            } else {
                Calendar dtNascimento = Calendar.getInstance();
                try {
                    DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    dtNascimento.setTime(formato.parse(txfDataNascimento.getText().trim()));
                } catch (Exception ex) {
                    txfDataNascimento.requestFocus();
                    msg = "Data de nascimento invalida, informe a data de nascimento no formato: dd/MM/yyyy \n";
                }
            }
            if (msg != "") {
                JOptionPane.showMessageDialog(this, msg);
            } else {
                Fornecedor f = new Fornecedor();
                f.setNome(txfNickname.getText().trim());
                f.setSenha(new String(txfSenha.getPassword()).trim());
                f.setCnpj(txfCnpj.getText().trim());
                f.setIe(txfIe.getText().trim());
                f.setCpf(txfCpf.getText().trim());
                f.setCep(txfCep.getText().trim());
                f.setComplemento(txfComplemento.getText().trim());
                f.setEmail(txfEmail.getText().trim());
                f.setEndereco(txfEndereco.getText().trim());
                f.setNumero_celular(txfNumeroCelular.getText().trim());
                f.setRg(txfRg.getText().trim());
                Calendar data_nasc = Calendar.getInstance();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    data_nasc.setTime(formato.parse(txfDataNascimento.getText().trim()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao gravar data nascimento");
                    ex.printStackTrace();
                }
                f.setData_nascimento(data_nasc);

                if (fornecedor != null)
                    f.setData_cadastro(fornecedor.getData_cadastro());

                return f;
            }

            return null;
        }

        public void setFornecedorFormulario(Fornecedor f) {

            if (f == null) {//se o parametro estiver nullo, limpa o formulario
                txfNickname.setText("");
                txfSenha.setText("");
                txfCnpj.setText("");
                txfIe.setText("");
                txfCpf.setText("");
                txfCep.setText("");
                txfComplemento.setText("");
                txfDataNascimento.setText("");
                txfEmail.setText("");
                txfEndereco.setText("");
                txfNumeroCelular.setText("");
                txfRg.setText("");
                txfDataCadastro.setText("");

                txfNickname.setEditable(true);
                fornecedor = null;
            } else {
                fornecedor = f;
                //txfNickname.setEditable(false);
                txfNickname.setText(fornecedor.getNome());
                txfSenha.setText(fornecedor.getSenha());
                txfCnpj.setText(fornecedor.getCnpj());
                txfIe.setText(fornecedor.getIe());
                txfCpf.setEditable(false);
                txfCpf.setText(fornecedor.getCpf());
                txfCep.setText(fornecedor.getCep());
                txfComplemento.setText(fornecedor.getComplemento());
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                //System.out.println("FORMATO: " + formato.format(fornecedor.getData_nascimento().getTime()));
                txfDataNascimento.setText(formato.format(fornecedor.getData_nascimento().getTime()));
                txfEmail.setText(fornecedor.getEmail());
                txfEndereco.setText(fornecedor.getEndereco());
                txfNumeroCelular.setText(fornecedor.getNumero_celular());
                txfRg.setText(fornecedor.getRg());
                txfDataCadastro.setText(formato.format(fornecedor.getData_cadastro().getTime()));

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

            lblSenha = new JLabel("Senha:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 1;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblSenha, posicionador);

            txfSenha = new JPasswordField(10);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 1;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfSenha, posicionador);

            lblCpf = new JLabel("CPF:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 2;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblCpf, posicionador);

            txfCpf = new JTextField(20);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 2;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfCpf, posicionador);

            lblCep = new JLabel("CEP:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 3;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblCep, posicionador);

            txfCep = new JTextField(9);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 3;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfCep, posicionador);

            lblComplemento = new JLabel("Complemento:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 4;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblComplemento, posicionador);

            txfComplemento = new JTextField(20);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 4;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfComplemento, posicionador);

            lblDataNascimento = new JLabel("Data de Nascimento:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 5;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblDataNascimento, posicionador);

            txfDataNascimento = new JTextField(10);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 5;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfDataNascimento, posicionador);

            lblDataCadastro = new JLabel("Data de Cadastro:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 6;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblDataCadastro, posicionador);

            txfDataCadastro = new JTextField(10);
            txfDataCadastro.setEditable(false);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 6;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfDataCadastro, posicionador);

            lblEmail = new JLabel("Email:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 7;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblEmail, posicionador);

            txfEmail = new JTextField(30);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 7;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfEmail, posicionador);

            lblEndereco = new JLabel("Endereço:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 8;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblEndereco, posicionador);

            txfEndereco = new JTextField(30);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 8;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfEndereco, posicionador);

            lblNumeroCelular = new JLabel("Número de Celular:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 9;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblNumeroCelular, posicionador);

            txfNumeroCelular = new JTextField(30);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 9;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfNumeroCelular, posicionador);

            lblRg = new JLabel("RG:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 10;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblRg, posicionador);

            txfRg = new JTextField(12);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 10;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfRg, posicionador);

            lblCnpj = new JLabel("CNPJ:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 11;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblCnpj, posicionador);

            txfCnpj = new JTextField(18);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 11;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfCnpj, posicionador);

            lblIe = new JLabel("IE:");
            posicionador = new GridBagConstraints();
            posicionador.gridy = 12;
            posicionador.gridx = 0;
            pnlDadosCadastrais.add(lblIe, posicionador);

            txfIe = new JTextField(30);
            posicionador = new GridBagConstraints();
            posicionador.gridy = 12;
            posicionador.gridx = 1;
            posicionador.anchor = GridBagConstraints.LINE_START;
            pnlDadosCadastrais.add(txfIe, posicionador);

            tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);

            pnlSul = new JPanel();
            pnlSul.setLayout(new FlowLayout());

            btnGravar = new JButton("Gravar");
            btnGravar.addActionListener(this);
            btnGravar.setFocusable(true);    //acessibilidade
            btnGravar.setToolTipText("btnGravarFornecedor"); //acessibilidade
            btnGravar.setMnemonic(KeyEvent.VK_G);
            btnGravar.setActionCommand("botao_gravar_formulario_fornecedor");

            pnlSul.add(btnGravar);

            btnCancelar = new JButton("Cancelar");
            btnCancelar.addActionListener(this);
            btnCancelar.setFocusable(true);    //acessibilidade
            btnCancelar.setToolTipText("btnCancelarFornecedor"); //acessibilidade
            btnCancelar.setActionCommand("botao_cancelar_formulario_fornecedor");

            pnlSul.add(btnCancelar);

            this.add(pnlSul, BorderLayout.SOUTH);

        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (arg0.getActionCommand().equals(btnGravar.getActionCommand())) {

                Fornecedor f = getFornecedorbyFormulario();//recupera os dados do formulÃ¡rio

                if (f != null) {
                    try {
                        //System.out.println("DADOS FORNECEDOR" + f.getCpf());
                        pnlAFornecedor.getControle().getConexaoJDBC().persist(f);

                        JOptionPane.showMessageDialog(this, "Fornecedor armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);

                        pnlAFornecedor.showTela("tela_fornecedor_listagem");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar Fornecedor! : " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }

                } else {

                    JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
                }


            } else if (arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
                pnlAFornecedor.showTela("tela_fornecedor_listagem");
            }
        }

}
