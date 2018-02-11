package com.will.xunwu.repository;

import com.will.xunwu.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>{

    User findByName(String name);
}
