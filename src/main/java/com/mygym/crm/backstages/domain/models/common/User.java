package com.mygym.crm.backstages.domain.models.common;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "user_table")
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    @NonNull
    private Long userId;

    @Column(nullable = false)
    @NonNull
    private String firstName;

    @Column(nullable = false)
    @NonNull
    private String lastName;

    @Column(nullable = false, unique = true)
    @NonNull
    private String userName;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(nullable = false)
    @NonNull
    private Boolean isActive;

}
