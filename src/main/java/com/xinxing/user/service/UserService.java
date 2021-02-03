package com.xinxing.user.service;

import com.xinxing.user.dto.LoginDto;
import com.xinxing.user.dto.TokenDto;
import com.xinxing.user.dto.UserDto;
import com.xinxing.user.util.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private Map<String, String> tokenIds;
    private Map<String, String> idTokens;
    private Map<String, UserDto> emailUsers;
    private Map<String, UserDto> idUsers;

    @PostConstruct
    private void init() {
        idTokens = new ConcurrentHashMap<>();
        tokenIds = new ConcurrentHashMap<>();
        //不落库用俩map顶住
        emailUsers = new ConcurrentHashMap<>();
        idUsers = new ConcurrentHashMap<>();
    }

    public boolean loginValidate(String email,String password){
        UserDto userDto = emailUsers.get(email);
        if(userDto == null || !password.equals(userDto.getPassword())){
            return false;
        }
        return true;
    }

    public TokenDto login(LoginDto loginDto){
        UserDto userDto = emailUsers.get(loginDto.getEmail());
        //生成一个唯一的id做为token
        String token = IdUtils.getInstance().generate().toString();
        if(idTokens.containsKey(userDto.getId())){
            //已登陆踢掉上一次登陆
            String tokenId = idTokens.remove(token);
            if(StringUtils.isNotBlank(tokenId)){
                tokenIds.remove(tokenId);
            }
        }
        idTokens.put(userDto.getId(),token);
        //用户绑定token
        tokenIds.put(token,userDto.getId());
        return new TokenDto(token);
    }

    public UserDto getUserByToken(String token){
        String userId = tokenIds.get(token);
        if(StringUtils.isBlank(userId)){
            //如果取不到则表示token无效,返回空对象
            return null;
        }
        UserDto userDto = idUsers.get(userId);
        return new UserDto(userDto.getId(), userDto.getFirst(), userDto.getLast(), userDto.getEmail(), null);
    }

    public UserDto createUser(UserDto userDto) {
        userDto.setId(IdUtils.getInstance().generate().toString());
        emailUsers.put(userDto.getEmail(), userDto);
        idUsers.put(userDto.getId(), userDto);
        return userDto;
    }

    public boolean hasUser(String email) {
        return emailUsers.containsKey(email);
    }

    public boolean logout(String token){
        String userId = tokenIds.remove(token);
        if(StringUtils.isNotBlank(userId)){
            idTokens.remove(userId);
        }
        return true;
    }
}
