package br.edu.ifsul.cc.lpoo.cv.model;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-03-13T16:08:23")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ extends Pessoa_ {

    public static volatile SingularAttribute<Funcionario, String> numero_pis;
    public static volatile SingularAttribute<Funcionario, Cargo> cargo;
    public static volatile SingularAttribute<Funcionario, String> numero_ctps;

}