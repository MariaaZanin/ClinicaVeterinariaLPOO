package br.edu.ifsul.cc.lpoo.cv.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_receita")
public class Receita{

    @Id
    @SequenceGenerator(name = "seq_receita", sequenceName = "seq_receita", allocationSize = 1)
    @GeneratedValue(generator = "seq_receita", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String orientacao;

    @ManyToOne
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @ManyToMany(cascade = CascadeType.ALL) //P deletar em modo CASCATA
    @JoinTable(name = "tb_receita_produto", joinColumns = {@JoinColumn(name = "receita_id")}, //agregacao, vai gerar uma tabela associativa.
            inverseJoinColumns = {@JoinColumn(name = "produto_id")})
    private List<Produto> produtos;

    public Receita(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(String orientacao) {
        this.orientacao = orientacao;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
