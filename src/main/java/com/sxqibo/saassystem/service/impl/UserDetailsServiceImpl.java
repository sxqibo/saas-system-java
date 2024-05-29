package com.sxqibo.saassystem.service.impl;

import com.sxqibo.saassystem.common.core.exception.BaseException;
import com.sxqibo.saassystem.common.enums.UserStatus;
import com.sxqibo.saassystem.entity.LoginUser;
import com.sxqibo.saassystem.entity.admin.PlatformAdmin;
import com.sxqibo.saassystem.entity.tenant.TenantAdmin;
import com.sxqibo.saassystem.service.IPlatformAdminService;
import com.sxqibo.saassystem.service.ITenantAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private IPlatformAdminService platformAdminService;

    @Autowired
    private ITenantAdminService tenantAdminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        PlatformAdmin platformAdmin = null;

        if (username.endsWith("@platform")) {
            username = username.substring(0, username.length() - "@platform".length());
            platformAdmin = platformAdminService.selectAdminByUsername(username);

            if (Objects.isNull(platformAdmin)) {
                LOGGER.warn("登录用户：{} 不存在.", username);
                throw new UsernameNotFoundException("登录用户：" + username + "不存在");
            }

            if (UserStatus.DISABLE.getCode().equals(platformAdmin.getStatus())) {
                LOGGER.warn("登录用户：{} 已被禁用.", username);
                throw new BaseException("登录用户：" + username + "已被禁用");
            }
        }

        TenantAdmin tenantAdmin = null;

        if (username.endsWith("@tenant")) {
            username = username.substring(0, username.length() - "@tenant".length());
            tenantAdmin = tenantAdminService.selectAdminByUsername(username);

            if (Objects.isNull(tenantAdmin)) {
                LOGGER.warn("登录用户：{} 不存在.", username);
                throw new UsernameNotFoundException("登录用户：" + username + "不存在");
            }

            if (UserStatus.DISABLE.getCode().equals(tenantAdmin.getStatus())) {
                LOGGER.warn("登录用户：{} 已被禁用.", username);
                throw new BaseException("登录用户：" + username + "已被禁用");
            }
        }

        LOGGER.info("登录用户：{} 登录成功", username);
        return !Objects.isNull(platformAdmin) ? new LoginUser(platformAdmin) : new LoginUser(tenantAdmin);
    }
}
