package com.qirsam.mini_library.database.entity.library;

import com.qirsam.mini_library.database.entity.BaseEntity;
import com.qirsam.mini_library.database.entity.user.UserBook;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book")
@Entity
@ToString(exclude = "userBooks")
@EqualsAndHashCode(callSuper = false)
public class Book extends BaseEntity<Long> {

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    private String description;


    @Builder.Default
    @OneToMany(mappedBy = "book")
    private List<UserBook> userBooks = new ArrayList<>();
}
