package com.akikun.family.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.akikun.family.annotation.DisableOnCondition;
import com.akikun.family.model.support.BaseResponse;

/**
 * DisableApi注解测试类
 *
 * @author guqing
 * @date 2020-02-14 17:51
 */
@RestController
@RequestMapping("/api/admin/test/disableOnCondition")
public class DisableOnConditionController {

    @GetMapping("/no")
    @DisableOnCondition
    public BaseResponse<String> blockAccess() {
        return BaseResponse.ok("测试禁止访问");
    }

    @GetMapping("/yes")
    public BaseResponse<String> ableAccess() {
        return BaseResponse.ok("成功");
    }
}