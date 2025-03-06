package com.mygym.crm.backstages.domain.models.common;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "user_table")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isActive;

}
