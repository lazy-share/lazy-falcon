package com.lazy.falcon.example.controller.permissions;

import com.lazy.falcon.core.annotaion.GenApiDocByJsonFile;
import com.lazy.falcon.example.dto.ResponseDto;
import com.lazy.falcon.example.entity.GroupEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 */
@RestController
public class GroupController {

    /**
     *
     * @return
     */
    @GenApiDocByJsonFile("doc/groups/put_group")
    @PutMapping("/groups")
    public ResponseDto putGroup(@RequestBody GroupEntity groupEntity){

        return ResponseDto.buildSuccessfully("新增用户组成功",groupEntity);
    }
}
