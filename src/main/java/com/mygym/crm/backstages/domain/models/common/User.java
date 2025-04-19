package com.mygym.crm.backstages.domain.models.common;

import com.mygym.crm.backstages.domain.models.Authorities;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, name = "username")
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "enabled")
    private Boolean isActive;

    @OneToOne(mappedBy = "user")
    private Authorities authorities;

}
