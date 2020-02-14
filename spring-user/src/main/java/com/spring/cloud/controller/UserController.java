package com.spring.cloud.controller;
import com.spring.cloud.base.BaseController;
import com.spring.cloud.controller.command.AuthenticationCommand;
import com.spring.cloud.controller.command.RoleCommand;
import com.spring.cloud.controller.command.UserCommand;
import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.Role;
import com.spring.cloud.entity.User;
import com.spring.cloud.service.RoleService;
import com.spring.cloud.service.UserService;
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
    public Map<String, Object> findUsers(String name, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> userList = userService.findUserList(name,pageable);
        return this.resultMap(CommandUtils.responsePage(userList.getTotalElements(), userList.getTotalPages(),
                CommandUtils.toCommands(userList.getContent(), UserCommand.class)));
    }

    @RequestMapping("/findUserById")
    public Map<String, Object> findRoleById(String userId) {
        User user = userService.findUserById(userId);
        return this.resultMap(new UserCommand().fromDomain(user));
    }

    @RequestMapping("/updateUser")
    public Map<String, Object> updateRoles(UserCommand userCommand) {
        User user = userCommand.toDomain();
        userService.saveOrUpdate(user);
        return this.resultMap(null);
    }

    @RequestMapping("/deleteUser")
    public Map<String, Object> deletedRole(String userId) {
        userService.deletedUser(userId);
        return this.resultMap(true);
    }

    @RequestMapping(value = "/findUserByName",method = RequestMethod.POST)
    public User findUserByName(String username) {
        return userService.findUserByName(username);
    }


    @RequestMapping(value = "/findAuthentication",method = RequestMethod.GET)
    public Map<String, Object> findAuthentication() {
        SecurityUser securityUser = RequestUserUtils.currentUser();
        List<Authentication> authentications = userService.findAuthentication(securityUser.getId());
        return this.resultMap(CommandUtils.toCommands(authentications, AuthenticationCommand.class));
    }

}

