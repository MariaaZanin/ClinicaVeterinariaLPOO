package br.edu.ifsul.cc.lpoo.cv.gui.produto.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;

import javax.swing.*;
import java.awt.*;

public class JPanelAProduto extends JPanel {

        private CardLayout cardLayout;
        private Controle controle;

        private JPanelAProdutoFormulario formulario;
        private JPanelAProdutoListagem listagem;

        public JPanelAProduto(Controle controle){

            this.controle = controle;
            initComponents();
        }

        private void initComponents(){

            cardLayout = new CardLayout();
            this.setLayout(cardLayout);

            formulario = new JPanelAProdutoFormulario(this, controle);
            listagem = new JPanelAProdutoListagem(this, controle);

            this.add(formulario, "tela_produto_formulario");
            this.add(listagem, "tela_produto_listagem");

            cardLayout.show(this, "tela_produto_listagem");
        }

        public void showTela(String nomeTela){

            if(nomeTela.equals("tela_produto_listagem")){
                listagem.populaTable();
            }else if(nomeTela.equals("tela_produto_formulario")){
               getFormulario().populaTipoProduto();
               getFormulario().populaFornecedor();
            }

            cardLayout.show(this, nomeTela);
        }

        /**
         * @return the controle
         */
        public Controle getControle() {
            return controle;
        }
        public JPanelAProdutoFormulario getFormulario() {return formulario;}


}
