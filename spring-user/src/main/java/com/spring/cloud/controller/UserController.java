package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.controller.command.AuthenticationCommand;
import com.spring.cloud.controller.command.UserCommand;
import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.User;
import com.spring.cloud.service.UserService;
import com.spring.cloud.support.mvc.ResourceDesc;
import com.spring.cloud.utils.CommandUtils;
import com.spring.cloud.utils.RequestUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.SecurityUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping("/findUsers")
    @ResourceDesc(model = "用户管理", name = "根据条件查询用户分页列表", desc = "根据条件查询用户分页列表")
    public Map<String, Object> findUsers(String name, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> userList = userService.findUserList(name,pageable);
        return this.resultMap(CommandUtils.responsePage(userList.getTotalElements(), userList.getTotalPages(),
                CommandUtils.toCommands(userList.getContent(), UserCommand.class)));
    }

    @RequestMapping("/findUserById")
    @ResourceDesc(model = "用户管理", name = "根据ID查询用户", desc = "根据ID查询用户")
    public Map<String, Object> findRoleById(String userId) {
        User user = userService.findUserById(userId);
        return this.resultMap(new UserCommand().fromDomain(user));
    }

    @RequestMapping("/updateUser")
    @ResourceDesc(model = "用户管理", name = "添加或修改用户", desc = "添加或修改用户")
    public Map<String, Object> updateRoles(UserCommand userCommand) {
        User user = userCommand.toDomain();
        userService.saveOrUpdate(user);
        return this.resultMap(null);
    }

    @RequestMapping("/deleteUser")
    @ResourceDesc(model = "用户管理", name = "删除用户", desc = "删除用户")
    public Map<String, Object> deletedRole(String userId) {
        userService.deletedUser(userId);
        return this.resultMap(true);
    }

    @RequestMapping(value = "/findUserByName",method = RequestMethod.POST)
    @ResourceDesc(model = "用户管理", name = "根据用户名查询用户", desc = "根据用户名查询用户")
    public User findUserByName(String username) {
        return userService.findUserByName(username);
    }


    @RequestMapping(value = "/findAuthentication",method = RequestMethod.GET)
    @ResourceDesc(model = "用户管理", name = "查询用户权限", desc = "查询用户权限")
    public Map<String, Object> findAuthentication() {
        SecurityUser securityUser = RequestUserUtils.currentUser();
        List<Authentication> authentications = userService.findAuthentication(securityUser.getId());
        return this.resultMap(CommandUtils.toCommands(authentications, AuthenticationCommand.class));
    }

}

