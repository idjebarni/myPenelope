package com.user.model;
import lombok.Data;

import java.util.Objects;

public class User {


    private Integer id;
    private String username;
    private String mdp;
    private String nom;
    private String prenom;

    public User(Integer id, String username, String mdp, String nom, String prenom) {
        this.id = id;
        this.username = username;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", mdp='" + mdp + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getMdp(), user.getMdp()) &&
                Objects.equals(getNom(), user.getNom()) &&
                Objects.equals(getPrenom(), user.getPrenom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getMdp(), getNom(), getPrenom());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

}
