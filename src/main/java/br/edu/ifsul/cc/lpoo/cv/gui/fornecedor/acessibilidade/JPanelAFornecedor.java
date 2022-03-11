package br.edu.ifsul.cc.lpoo.cv.gui.fornecedor.acessibilidade;
import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.*;

public class JPanelAFornecedor extends JPanel {

        private CardLayout cardLayout;
        private Controle controle;

        private JPanelAFornecedorFormulario formulario;
        private JPanelAFornecedorListagem listagem;

        public JPanelAFornecedor(Controle controle){

            this.controle = controle;
            initComponents();
        }

        private void initComponents(){

            cardLayout = new CardLayout();
            this.setLayout(cardLayout);

            formulario = new JPanelAFornecedorFormulario(this, controle);
            listagem = new JPanelAFornecedorListagem(this, controle);

            this.add(formulario, "tela_fornecedor_formulario");
            this.add(listagem, "tela_fornecedor_listagem");

            cardLayout.show(this, "tela_fornecedor_listagem");
        }

        public void showTela(String nomeTela){

            if(nomeTela.equals("tela_fornecedor_listagem")){
                listagem.populaTable();
            }else if(nomeTela.equals("tela_fornecedor_formulario")){
                //getFormulario().populaTable();
            }

            cardLayout.show(this, nomeTela);
        }

        /**
         * @return the controle
         */
        public Controle getControle() {
            return controle;
        }
        public JPanelAFornecedorFormulario getFormulario() {return formulario;}
}
