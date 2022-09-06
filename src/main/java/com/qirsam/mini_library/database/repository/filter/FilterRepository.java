package com.qirsam.mini_library.database.repository.filter;

import java.util.List;

public interface FilterRepository<T, F> {

    List<T> findAllByFilter(F filter);
}
