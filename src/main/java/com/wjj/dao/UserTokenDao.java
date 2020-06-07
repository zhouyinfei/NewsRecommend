package com.wjj.dao;

import com.wjj.entity.UserToken;

public interface UserTokenDao {

    void addUserToken(UserToken userToken);
    
    void deleteUserToken(int id);
    
    void updateUserToken(UserToken userToken);
    
    UserToken getUserTokenById(int id);
    
    UserToken getUserTokenByName(String token);
    
}