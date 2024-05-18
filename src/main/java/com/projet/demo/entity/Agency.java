package com.projet.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Agency {

    @Id
    private String imm;

    private String name;

    private String patentId;
    private String image;


    /*@OneToOne
    private CreditCard creditCard;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Service> services;

    public void showActiveServicesOnly() {
        services = services
                .stream()
                .filter(Service::isActive)
                .toList();
    }
    */

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Agent> agents = new ArrayList<>();


}
