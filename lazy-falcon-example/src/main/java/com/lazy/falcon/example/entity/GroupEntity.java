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
public class GroupEntity implements Serializable{

    private String groupName;
    private String groupCode;

    private List<RoleEntity> roles = SetsUtils.list();

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
