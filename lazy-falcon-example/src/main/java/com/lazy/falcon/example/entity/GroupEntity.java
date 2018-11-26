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
public class GroupEntity implements Serializable {

    @ApiParamItem("组名称")
    private String groupName;
    @ApiParamItem("组编码")
    private String groupCode;

    @ApiParamGroup(linkCls = RoleEntity.class, isParamItem = true, remark = "参看用户组关联的角色列表", groupTitle = "用户组关联的角色列表")
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
