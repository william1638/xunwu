package com.will.xunwu.repository;

import com.will.xunwu.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 角色服务
 */
public interface RoleRepository extends CrudRepository<Role,Long> {

    List<Role> findRolesByUserId(Long userId);
}
