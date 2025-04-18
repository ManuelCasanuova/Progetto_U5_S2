package it.epicode.Progetto_U5_S2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "dipendenti")

public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long dipendenteId;

    @Column(nullable = false,  unique = true,  length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @Column(nullable = false, length = 50)
    private String cognome;

    @Column(nullable = false, length = 50, unique = true)
    private String email;



    public Dipendente(String username, String nome, String cognome, String email) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Dipendente: " + dipendenteId + '\n' +
                "Username:  " + username + '\n' +
                "Nome: " + nome + '\n' +
                "Cognome: " + cognome + '\n' +
                "Email: " + email;
    }
}