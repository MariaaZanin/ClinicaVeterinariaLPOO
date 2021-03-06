package br.edu.ifsul.cc.lpoo.cv.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_raca")
public class Raca implements Serializable{

    @Id
    @SequenceGenerator(name = "seq_raca", sequenceName = "seq_raca", allocationSize = 1)
    @GeneratedValue(generator = "seq_raca", strategy = GenerationType.SEQUENCE)
    private String id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;

    public Raca(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }


}
