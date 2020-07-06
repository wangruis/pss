package com.shop.pss.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
public class User implements UserDetails {
    private Long id;
    private String name;
    private String idCard;
    private String phone;
    private String telephone;
    private String address;
    private boolean enabled;
    private String username;
    @JsonProperty("pwd")
    private String password;
    private String remark;
    private List<Role> roles;
    private String userface;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get idCard
     *
     * @return java.lang.String idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * set idCard
     *
     * @param idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * get phone
     *
     * @return java.lang.String phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * set phone
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * get telephone
     *
     * @return java.lang.String telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * set telephone
     *
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * get address
     *
     * @return java.lang.String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * set address
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get remark
     *
     * @return java.lang.String remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * set remark
     *
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * get userface
     *
     * @return java.lang.String userface
     */
    public String getUserface() {
        return userface;
    }

    /**
     * set userface
     *
     * @param userface
     */
    public void setUserface(String userface) {
        this.userface = userface;
    }
}