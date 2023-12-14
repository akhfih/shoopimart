package com.enigma.shopeymart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_admin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String Id;
    @Column(name = "name", unique = false, nullable = false, length = 30)
    private String name;
    @Column(name = "address", unique = false, nullable = false, length = 100)
    private String address;
    @Column(name = "mobile_phone", unique = true, nullable = false, length = 30)
    private String mobilePhone;
    @Column(name = "email", unique = true, nullable = false, length = 30)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;
}
