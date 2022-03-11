package br.edu.ifsul.cc.lpoo.cv.model;

import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-03-02T08:25:01")
@StaticMetamodel(Agenda.class)
public class Agenda_ { 

    public static volatile SingularAttribute<Agenda, Calendar> data_inicio;
    public static volatile SingularAttribute<Agenda, Calendar> data_fim;
    public static volatile SingularAttribute<Agenda, String> observacao;
    public static volatile SingularAttribute<Agenda, Medico> medico;
    public static volatile SingularAttribute<Agenda, String> id;
    public static volatile SingularAttribute<Agenda, Funcionario> funcionario;
    public static volatile SingularAttribute<Agenda, TipoProduto> tipoProduto;

}