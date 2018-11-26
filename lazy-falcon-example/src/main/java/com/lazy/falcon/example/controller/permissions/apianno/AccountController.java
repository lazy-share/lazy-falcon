package com.lazy.falcon.example.controller.permissions.apianno;

import com.lazy.falcon.common.annotaion.Api;
import com.lazy.falcon.common.annotaion.ApiModule;
import com.lazy.falcon.common.annotaion.ApiParamGroup;
import com.lazy.falcon.common.annotaion.ApiParamItem;
import com.lazy.falcon.example.dto.ResponseDto;
import com.lazy.falcon.example.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 */
@RestController
@ApiModule(value = "系统权限-用户控制器", moduleCls = UserEntity.class)
@RequestMapping("/users")
public class AccountController {


    @Api(value = "新增用户", author = "laizhiyuan", desc = "该API提供新增用户功能", createDate = "2018-11-11", lastUpdateTime = "2018-11-11")
    @PutMapping("/putUser")
    public ResponseDto<UserEntity> putUser(@RequestBody @ApiParamGroup UserEntity entity) {
        return new ResponseDto<UserEntity>("200", "新增用户成功", entity);
    }

    @Api(value = "删除用户", author = "laizhiyuan", desc = "该API提供删除用户功能", createDate = "2018-11-11", lastUpdateTime = "2018-11-11")
    @DeleteMapping("/deleteUsers/{id}")
    public ResponseDto<Long> delUser(@PathVariable("id") Long id) {
        return new ResponseDto<Long>("200", "删除用户成功", id);
    }


    @Api(value = "根据id查询用户", author = "laizhiyuan", desc = "该API提供根据id查询用户功能", createDate = "2018-11-11", lastUpdateTime = "2018-11-11")
    @GetMapping("/getUserById")
    public ResponseDto<List<UserEntity>> getUser(@ApiParamItem("主键") @RequestParam("id") Long id) {
        return new ResponseDto<List<UserEntity>>("200", "根据id查询用户成功", new ArrayList<>());
    }


    /************ 测试  **********/
    public ResponseDto<List<UserEntity>> test() {

        return new ResponseDto<>("", "", null);
    }

    public static void main(String[] args) throws Exception {
//        Class clazz = AccountController.class;
//        Method method = clazz.getDeclaredMethod("test");
//        Class returyTypeCls = method.getReturnType();
//        String returnTypeClsStr = method.getGenericReturnType().getTypeName();
//
//        String parserStr = parserGenericStr(returnTypeClsStr);
//        System.out.println(parserStr);
//        returnTypeClsStr = returnTypeClsStr.split(parserStr)[1];
//
//        parserStr = parserGenericStr(returnTypeClsStr);
//        System.out.println(parserStr);
//        returnTypeClsStr = returnTypeClsStr.split(parserStr)[1];
//
//        System.out.println(returnTypeClsStr);

        System.out.println("<aaaa".split("<").length);
    }

    protected static String parserGenericStr(String genericStr) {
        String[] genericStrArr = genericStr.split("<");
        if (genericStrArr.length > 1) {
            String parserStr = genericStrArr[1];
            parserStr = parserStr.replaceAll(">", "");
            return parserStr;
        }
        return null;
    }

}
