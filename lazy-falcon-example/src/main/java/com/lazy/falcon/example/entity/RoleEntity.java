package com.lazy.falcon.example.entity;

import com.lazy.falcon.core.tools.SetsUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/8.
 */
public class RoleEntity implements Serializable {

    private String roleName;
    private String roleDesc;

    private List<UserEntity> users = SetsUtils.list();

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
