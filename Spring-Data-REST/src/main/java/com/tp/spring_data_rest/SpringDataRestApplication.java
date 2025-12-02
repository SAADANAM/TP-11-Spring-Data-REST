package com.tp.spring_data_rest;

import com.tp.spring_data_rest.entities.Client;
import com.tp.spring_data_rest.entities.Compte;
import com.tp.spring_data_rest.entities.TypeCompte;
import com.tp.spring_data_rest.repositories.ClientRepository;
import com.tp.spring_data_rest.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class SpringDataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepo, ClientRepository clientRepo, RepositoryRestConfiguration restConfig) {
        return args -> {
            restConfig.exposeIdsFor(Compte.class);

            // Créer les clients sans passer la liste de comptes
            Client premierClient = new Client();
            premierClient.setNom("Amal");
            clientRepo.save(premierClient);

            Client secondClient = new Client();
            secondClient.setNom("Ali");
            clientRepo.save(secondClient);

            // Créer les comptes
            compteRepo.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, premierClient));
            compteRepo.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, premierClient));
            compteRepo.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, secondClient));

            compteRepo.findAll().forEach(compteCourant -> System.out.println(compteCourant.toString()));
        };
    }
}