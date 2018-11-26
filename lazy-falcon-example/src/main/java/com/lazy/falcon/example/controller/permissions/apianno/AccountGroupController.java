package com.lazy.falcon.example.controller.permissions.apianno;

import com.lazy.falcon.common.annotaion.Api;
import com.lazy.falcon.common.annotaion.ApiModule;
import com.lazy.falcon.common.annotaion.ApiParamGroup;
import com.lazy.falcon.example.dto.ResponseDto;
import com.lazy.falcon.example.entity.GroupEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 */
@RestController
@ApiModule(value = "系统权限-用户组控制器", moduleCls = GroupEntity.class)
public class AccountGroupController {

    /**
     * @return
     */
    @Api(value = "新增用户组", author = "laizhiyuan", desc = "该API提供新增用户组功能", createDate = "2018-11-11", lastUpdateTime = "2018-11-11")
    @PutMapping("/putGroups")
    public ResponseDto<GroupEntity> putGroup(@RequestBody @ApiParamGroup(includeItems = {"groupName", "roles"}) GroupEntity groupEntity) {

        return new ResponseDto<GroupEntity>("200", "新增用户组成功", groupEntity);
    }
}
