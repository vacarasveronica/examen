package mpp.rest;

public class JocDTO {
    public Integer nrPuncte;
    public String literePropuse;
    public String litereGenerate;

    public JocDTO(Integer nrPuncte, String literePropuse, String litereGenerate) {
        this.nrPuncte = nrPuncte;
        this.literePropuse = literePropuse;
        this.litereGenerate = litereGenerate;
    }
}
