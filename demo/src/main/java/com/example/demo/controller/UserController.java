package com.example.demo.controller;


import com.example.demo.bean.User;
import com.example.demo.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @RequestMapping("/insertPage")
//    public String index(){
//        return "insertPage";
//    }
//
//    @RequestMapping("select/{id}")
//    @ResponseBody
//    public String save(@PathVariable int id){
//        return userService.select(id).toString();
//    }

    @GetMapping
    public List<User> getUserList() {
        System.out.println("asdasdasdasda");
        return userService.userList();
    }

//    @RequestMapping("/insert")
//    public String save(User user){
//        userService.save(user);
//        System.out.println("新增了用户：" + user);
//        return "redirect:/userList";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable Integer id){
//        userService.delete(id);
//        return "redirect:/userList";
//    }
//
//    @GetMapping("/updatePage/{id}")
//    public String updatePage(Model model,@PathVariable int id){
//        User user = userService.findUserById(id);
//        model.addAttribute("user",user);
//        return "updatePage";
//    }

//    @PostMapping("/update")
//    public String updateUser(User user){
//        userService.update(user);
//        System.out.println("修改的用户为 ： " + user.getUsername());
//        return "redirect:/userList";
//    }


//    @RequestMapping("/insert")
    //方法的参数user用于接收客户端提交的表单数据
//    public String save(User user){
//        //将表单数据保存到数据库中
//        userService.save(user);
//        //执行重定向到userList的URL
//        return "redirect:/userList";
//    }
    //@GetMapping注解意味着客户端发送一个对应URL的HTTP GET请求时才会处理这个请求

    //删除一条用户数据
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.delete(id);
    }
    //通过Id获得一条用户数据
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        User user = userService.findUserById(id);
        return user;
    }
    //更新上面通过Id获得的用户数据
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable int id){
        //后端拿到前端发送来的新的用户名和密码数据
        String username = user.getUsername();
        String password = user.getPassword();
        //构建更新后的User对象
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUsername(username);
        updatedUser.setPassword(password);
        //更新信息内容
        userService.update(updatedUser);
        return ResponseEntity.ok().body("{\"message\": \"更新成功\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        //获取用户的用户名和密码信息
        List<User> users = userService.userList();
        //遍历用户列表，检查用户名和密码是否匹配
        for(User user1 : users) {
            if(user1.getUsername().equals(username) && user1.getPassword().equals(password)){
                return ResponseEntity.ok().body("{\"message\":\"登录成功\", \"username\":\"" + username + "\"}");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败，用户名或密码错误！");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        //获取前端发送来的注册用户信息
        String username = user.getUsername();
        String password = user.getPassword();
        //获取用户的用户名和密码信息
        List<User> users = userService.userList();
        //遍历用户列表，检查用户名是否存在
        for(User user1 : users) {
            //如果用户名重复，则注册失败
            if(user1.getUsername().equals(username)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在，注册失败！");
            }
        }
        //构建新的用户对象
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        //把新的用户信息保存到数据库中
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("注册成功");
    }

    //增加一条用户数据
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user){
        //后端拿到前端发送来的新的用户名和密码数据
        String username = user.getUsername();
        String password = user.getPassword();
        //获取当前的用户的用户名和密码信息
        List<User> users = userService.userList();
        //遍历用户列表，检查用户名和密码是否匹配
        for(User user1 : users) {
            //如果用户名重复，则插入失败
            if(user1.getUsername().equals(username)){
                return ResponseEntity.ok().body("{\"message\": \"用户名已存在，插入失败\"}");
            }
        }
        //构建新的用户对象
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        //把新的用户信息保存到数据库中
        userService.save(user);
        return ResponseEntity.ok().body("{\"message\": \"插入成功\"}");
    }


}

