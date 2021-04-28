package com.smart.cms.security.service;
import com.smart.cms.dao.RoleDao;
import com.smart.cms.dao.UserDao;
import com.smart.cms.dao.UserRoleDao;
import com.smart.cms.po.RolePo;
import com.smart.cms.po.UserPo;
import com.smart.cms.po.UserRolePo;
import com.smart.cms.utils.other.ToolsUtils;
import com.smart.cms.vo.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 查询登陆用户信息
 *
 */
public class CustomUserDetailsService implements UserDetailsService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        logger.info("查询用户：{} 的信息...", usercode);
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (ToolsUtils.isEmpty(usercode)) {
            logger.error("用户名不能为空！");
            throw new UsernameNotFoundException("用户名不能为空！");
        }

        // 查询用户信息
        UserPo userPo = userDao.findUserInfoByUsercodeAndDelFlag(usercode, 0);
        if (userPo == null) {
            logger.error("用户：{}，不存在！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，不存在");
        }
        if (userPo.getDelFlag() == 2 ) {
            logger.error("用户：{}，被锁定！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，被锁定");
        }
        if (userPo.getDelFlag() == 1 ) {
            logger.error("用户：{}，被删除！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，被删除");
        }
        // 查询角色信息
        List<UserRolePo> userRolePos = userRoleDao.findRoleIdByUserIdAndDelFlag(userPo.getId(), 0);
        if (CollectionUtils.isEmpty(userRolePos)) {
            logger.error("用户：{}，未分配角色！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配角色");
        }
        userRolePos.stream().forEach(ur -> {
            Long roleId = ur.getRoleId();
            Optional<RolePo> rolePo = roleDao.findById(roleId);
            if (!rolePo.isPresent()) {
                logger.error("用户：{}，roleId:{},未查询到信息！", usercode, roleId);
                return;
            }
            authorities.add(new SimpleGrantedAuthority(rolePo.get().getRoleCode()));
        });
        if (CollectionUtils.isEmpty(authorities)) {
            logger.error("用户：{}，未分配权限！", usercode);
            throw new UsernameNotFoundException("用户：" + usercode + "，未分配权限");
        }
        SecurityUser securityUser = new SecurityUser(userPo, authorities);
        return securityUser;
    }
}
