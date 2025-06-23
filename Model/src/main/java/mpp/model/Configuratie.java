package mpp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "configuratie")
public class Configuratie extends Entitate<Integer> {
    private String litere;
    private Integer numar1;
    private Integer numar2;
    private Integer numar3;
    private Integer numar4;

    public Configuratie() {}

    public Configuratie(String litere, Integer numar1, Integer numar2, Integer numar3, Integer numar4) {
        this.litere = litere;
        this.numar1 = numar1;
        this.numar2 = numar2;
        this.numar3 = numar3;
        this.numar4 = numar4;
    }

    public String getLitere() {
        return litere;
    }
    public void setLitere(String litere) {
        this.litere = litere;
    }
    public Integer getNumar1() {
        return numar1;
    }
    public void setNumar1(Integer numar1) {
        this.numar1 = numar1;
    }
    public Integer getNumar2() {
        return numar2;
    }
    public void setNumar2(Integer numar2) {
        this.numar2 = numar2;
    }
    public Integer getNumar3() {
        return numar3;
    }
    public void setNumar3(Integer numar3) {
        this.numar3 = numar3;
    }
    public Integer getNumar4() {
        return numar4;
    }
}
