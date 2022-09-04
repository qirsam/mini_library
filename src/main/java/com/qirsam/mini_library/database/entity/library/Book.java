package com.qirsam.mini_library.database.entity.library;

import com.qirsam.mini_library.database.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book")
@Entity
@ToString
@EqualsAndHashCode(callSuper = false)
public class Book extends BaseEntity<Long> {

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(nullable = false)
    private String description;
}
