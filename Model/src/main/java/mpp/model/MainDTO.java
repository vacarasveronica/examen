package mpp.model;

import java.time.LocalDateTime;

public class MainDTO {
    private String alias;
    private LocalDateTime time;
    private Integer punctaj;

    public MainDTO(String alias, LocalDateTime time, Integer punctaj) {
        this.alias = alias;
        this.time = time;
        this.punctaj = punctaj;
    }
    public String getAlias() {
        return alias;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public Integer getPunctaj() {
        return punctaj;
    }
}
