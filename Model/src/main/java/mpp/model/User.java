package mpp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends Entitate<Integer> {
    //Integer id;
    private String alias;

    public User(String alias) {
        this.alias = alias;
    }

    public User() {

    }

    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

}
