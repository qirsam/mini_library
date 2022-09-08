package com.qirsam.mini_library.database.entity.user;

import com.qirsam.mini_library.database.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
@ToString(exclude = "userBooks")
@EqualsAndHashCode(of = "username", callSuper = false)
public class User extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstname;

    private String lastname;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserBook> userBooks = new ArrayList<>();





}
