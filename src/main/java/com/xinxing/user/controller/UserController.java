package com.xinxing.user.controller;

import com.xinxing.user.dto.CreateUserResponseDto;
import com.xinxing.user.dto.HeartDto;
import com.xinxing.user.dto.LoginDto;
import com.xinxing.user.dto.TokenDto;
import com.xinxing.user.dto.UserDto;
import com.xinxing.user.properties.ConfigProperties;
import com.xinxing.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@Tag(name = "User Manager",description = "用户管理")
public class UserController {

    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private UserService userService;

    @Operation(summary = "心跳")
    @GetMapping("/heartbeat")
    public HeartDto heartbeat(){
        HeartDto dto = new HeartDto(configProperties.getVersion(),configProperties.getReleasedAt());
        return dto;
    }

    @Operation(summary = "创建用户")
    @PostMapping("/user")
    public CreateUserResponseDto createUser(@RequestBody @Valid UserDto user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error -> {
                throw new ValidationException(error.getDefaultMessage());
            });
        }
        if(userService.hasUser(user.getEmail())){
            throw new ValidationException("该邮箱已注册");
        }
        //save user
        userService.createUser(user);
        return new CreateUserResponseDto(user.getId());
    }

    @Operation(summary = "登录")
    @PostMapping("/user/login")
    public TokenDto login(@RequestBody @Valid LoginDto loginDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error -> {
                throw new ValidationException(error.getDefaultMessage());
            });
        }
        if(!userService.loginValidate(loginDto.getEmail(),loginDto.getPassword())){
            //不明确提示避免爆破
            throw new ValidationException("用户名或密码错误");
        }
        return userService.login(loginDto);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/user")
    public UserDto createUser(String token){
        return userService.getUserByToken(token);
    }

    @Operation(summary = "注销")
    @PostMapping("/user/logout")
    public void logout(@RequestBody @Valid TokenDto tokenDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error -> {
                throw new ValidationException(error.getDefaultMessage());
            });
        }
        userService.logout(tokenDto.getToken());
    }
}
