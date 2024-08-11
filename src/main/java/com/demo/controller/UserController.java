package com.demo.controller;

import com.demo.domain.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        try {
            User u = userService.login(user);

            if (u != null) {
                request.getSession().setAttribute("USER_SESSION", u);
                return "redirect:/admin/main.jsp";
            }
            request.setAttribute("msg", "用户名或密码错误");
            return "forward:/admin/login.jsp";
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("msg","系统错误");
            return "forward:/admin/login.jsp";
        }
    }
    //访问主页面
    @RequestMapping("/toMainPage")
    public String toMainPage(){
        return "main";
    }
    /**
     * 登录注销
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout")
    public String LoginOut(HttpSession session,HttpServletRequest request) {
        try {
            // 销毁session
            session.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","系统异常!");
            return "login";
        }
        return "login";
    }
}
