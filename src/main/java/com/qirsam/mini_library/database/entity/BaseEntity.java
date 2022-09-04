package com.qirsam.mini_library.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity<K extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected K id;
}
