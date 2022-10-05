package com.qirsam.mini_library.database.entity.library;

import com.qirsam.mini_library.database.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "author")
@Entity
@ToString
@EqualsAndHashCode(callSuper = false)
public
class Author extends BaseEntity<Integer> {

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private LocalDate birthDate;

    private String description;

    private String image;
}
