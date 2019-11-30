package com.benjamin.controller;

import com.benjamin.dao.UserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.benjamin.pojo.User;

import javax.annotation.Resource;
import java.util.List;


@Controller
public class LoginController {


    //add into session
    private Session session = new SimpleSession();

    @RequestMapping(value = "/subLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(User user) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);

            System.out.println(subject.isPermitted("user:select"));


        } catch (AuthenticationException e) {
            return e.getMessage();
        }

        if (subject.hasRole("admin")){
            return  user.getUsername() + " has admin role";
        }


        session=subject.getSession();
        session.setAttribute("subject", subject);
        return "登录成功 " + user.getUsername() + " does not has admin role";
    }


    @RequestMapping(value="/testRole", method=RequestMethod.GET)
    //@RequiresRoles("admin")
    @ResponseBody
    public  String testRole(){
        return "testRole has admin role";
    }

    @RequestMapping(value="/testRole1", method=RequestMethod.GET)
    //@RequiresPermissions({"user:select","user:delete"})
    @ResponseBody
    public  String testRole1(){
        return "testRole1 has user:delete,add,select permissions";
    }

    @RequestMapping(value="/testPerms", method=RequestMethod.GET)
    @ResponseBody
    public  String testPerms(){

        return "test perms success";
    }

    @RequestMapping(value="/testPerms1", method=RequestMethod.GET)
    @ResponseBody
    public  String testPerms1(){

        return "test perms1 success";
    }

}