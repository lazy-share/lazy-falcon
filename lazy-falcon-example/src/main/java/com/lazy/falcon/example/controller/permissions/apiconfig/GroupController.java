package com.lazy.falcon.example.controller.permissions.apiconfig;

import com.lazy.falcon.common.annotaion.ApiConfig;
import com.lazy.falcon.common.annotaion.ApiModule;
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
@ApiModule(value = "系统权限-用户组", moduleCls = GroupEntity.class)
public class GroupController {

    /**
     * @return
     */
    @ApiConfig("doc/groups/put_group")
    @PutMapping("/groups")
    public ResponseDto<GroupEntity> putGroup(@RequestBody GroupEntity groupEntity) {

        return new ResponseDto<GroupEntity>("200", "新增用户组成功", groupEntity);
    }
}
