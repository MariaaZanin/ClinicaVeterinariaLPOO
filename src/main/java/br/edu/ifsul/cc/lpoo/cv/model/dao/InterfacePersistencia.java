package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Receita;

import java.util.List;

public interface InterfacePersistencia {

    //QUESTÃO 02
    public Boolean conexaoAberta();
    public void fecharConexao();
    public Object find(Class c, Object id) throws Exception;
    public void persist(Object o) throws Exception;
    public void remover(Object o) throws Exception;

    public List mostraTudo(Class c) throws Exception;
    public List<Produto> listProdutos() throws Exception;
    public List<Receita> listReceitas() throws Exception;
}
