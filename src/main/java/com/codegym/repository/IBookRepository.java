package com.codegym.repository;

import com.codegym.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IBookRepository extends CrudRepository<Book, Long> {
}
