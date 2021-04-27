package com.smart.cms.dao;

import com.smart.cms.po.RolePo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RolePo, Long> {
}
