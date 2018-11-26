package com.lazy.falcon.example.entity;

import com.lazy.falcon.common.annotaion.ApiParamGroup;
import com.lazy.falcon.common.annotaion.ApiParamItem;
import com.lazy.falcon.common.tools.SetsUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/8.
 */
public class RoleEntity implements Serializable {

    @ApiParamItem("角色名称")
    private String roleName;
    @ApiParamItem("角色描述")
    private String roleDesc;

    @ApiParamGroup(linkCls = UserEntity.class, excludeItems = {"loginName"}, isParamItem = true, remark = "参看角色关联用户", groupTitle = "角色关联用户")
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
