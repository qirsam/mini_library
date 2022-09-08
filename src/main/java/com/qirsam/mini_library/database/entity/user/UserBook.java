package com.qirsam.mini_library.database.entity.user;

import com.qirsam.mini_library.database.entity.BaseEntity;
import com.qirsam.mini_library.database.entity.library.Book;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_book")
@EqualsAndHashCode(callSuper=false)
public class UserBook extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status;
}
