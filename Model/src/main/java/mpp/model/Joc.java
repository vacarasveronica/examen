package mpp.model;

import java.time.LocalDateTime;

public class Joc extends Entitate<Integer>{
    private User user;
    private Configuratie configuratie;
    private Integer nrPuncte;
    private LocalDateTime timpIncepere;
    private String literePropuse;
    private String litereGenerate;

    public Joc(User user, Configuratie configuratie,Integer nrPuncte,LocalDateTime timpIncepere,String literePropuse,String litereGenerate) {
        this.user = user;
        this.configuratie = configuratie;
        this.nrPuncte = nrPuncte;
        this.timpIncepere = timpIncepere;
        this.literePropuse = literePropuse;
        this.litereGenerate = litereGenerate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Configuratie getConfiguratie() {
        return configuratie;
    }
    public void setConfiguratie(Configuratie configuratie) {
        this.configuratie = configuratie;
    }
    public Integer getNrPuncte() {
        return nrPuncte;
    }
    public void setNrPuncte(Integer nrPuncte) {
        this.nrPuncte = nrPuncte;
    }
    public LocalDateTime getTimpIncepere() {
        return timpIncepere;
    }
    public void setTimpIncepere(LocalDateTime timpIncepere) {
        this.timpIncepere = timpIncepere;
    }
    public String getLiterePropuse() {
        return literePropuse;
    }
    public void setLiterePropuse(String literePropuse) {
        this.literePropuse = literePropuse;
    }
    public String getLitereGenerate() {
        return litereGenerate;
    }
    public void setLitereGenerate(String litereGenerate) {
        this.litereGenerate = litereGenerate;
    }
}
