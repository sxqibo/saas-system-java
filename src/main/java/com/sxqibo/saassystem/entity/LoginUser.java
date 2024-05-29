package com.sxqibo.saassystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sxqibo.saassystem.entity.admin.PlatformAdmin;
import com.sxqibo.saassystem.entity.tenant.TenantAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * 登录用户身份权限
 */
public class LoginUser implements UserDetails
{
    /**
     * 当前用户的唯一标识
     */
    private String token;

    /**
     * 登录的时间
     */
    private Long loginTime;

    /**
     * 过期的时间
     */
    private Long expireTime;

    private PlatformAdmin platformAdmin;

    private TenantAdmin tenantAdmin;

    public Long getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Long loginTime)
    {
        this.loginTime = loginTime;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Long expireTime)
    {
        this.expireTime = expireTime;
    }

    public PlatformAdmin getPlatformAdmin()
    {
        return platformAdmin;
    }

    public void setPlatformAdmin(PlatformAdmin platformAdmin)
    {
        this.platformAdmin = platformAdmin;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public LoginUser()
    {
    }

    public LoginUser(PlatformAdmin platformAdmin)
    {
        this.platformAdmin = platformAdmin;
    }

    public LoginUser(String token, Long loginTime, Long expireTime, PlatformAdmin platformAdmin)
    {
        this.token = token;
        this.loginTime = loginTime;
        this.expireTime = expireTime;
        this.platformAdmin = platformAdmin;
    }

    public LoginUser(TenantAdmin tenantAdmin)
    {
        this.tenantAdmin = tenantAdmin;
    }

    public LoginUser(String token, Long loginTime, Long expireTime, TenantAdmin tenantAdmin)
    {
        this.token = token;
        this.loginTime = loginTime;
        this.expireTime = expireTime;
        this.tenantAdmin = tenantAdmin;
    }

    /**
     * 获取用户被授予的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        return !Objects.isNull(platformAdmin) ? platformAdmin.getPassword() : tenantAdmin.getPassword();
    }

    @Override
    public String getUsername()
    {
        return !Objects.isNull(platformAdmin) ? platformAdmin.getUsername() : tenantAdmin.getUsername();
    }

    public TenantAdmin getTenantAdmin()
    {
        return tenantAdmin;
    }

    public void setTenantAdmin(TenantAdmin tenantAdmin)
    {
        this.tenantAdmin = tenantAdmin;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
