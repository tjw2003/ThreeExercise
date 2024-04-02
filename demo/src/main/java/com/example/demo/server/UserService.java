package com.example.demo.server;

import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;//注入UserMapper
    /*
     * 根据id查询用户
     */
    public User select(int id){
        return userMapper.select(id);
    }


    //查询所有用户
    public List<User> userList(){
        return userMapper.userList();
    }

    //增加保存用户
    public void save(User user){
        userMapper.save(user);
    }

    //根据id删除用户
    public int delete(int id){
        return userMapper.delete(id);
    }

    //根据id查找用户
    public User findUserById(int id){
        return userMapper.findUserById(id);
    }

    //更改用户信息
    public int update(User user){
        return userMapper.update(user);
    }

}
