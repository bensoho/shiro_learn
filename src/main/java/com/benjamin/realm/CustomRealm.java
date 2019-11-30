package com.benjamin.realm;

import com.benjamin.dao.UserDao;
import com.benjamin.dao.impl.UserDaoImpl;
import com.benjamin.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;
//    Map<String,String>userMap = new HashMap<>(16);
//    {
//        userMap.put("benjamin", "3285541c519ec7cef7077b06baae58d5");
//        super.setName("customRealm");
//    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String>roles = getRolesByUserName(userName);
        Set<String>permissions = getPersmissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    private Set<String> getPersmissionsByUserName(String userName) {

        List<String>list = userDao.queryPermissionsByUserName(userName);
        Set<String> sets = new HashSet<>(list);
//        sets.add("user:select");
//        sets.add("user:delete");
        return sets;
    }

    private Set<String> getRolesByUserName(String userName) {
        List<String>list = userDao.queryRolesByUserName(userName);
        Set<String> sets = new HashSet<>(list);
//        sets.add("admin");
//        sets.add("user");
        return sets;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //1.从主体传过来的认证信息中获得用户名
        String userName = (String) authenticationToken.getPrincipal();
        //2.通过用户名从数据库中获取凭证
        String password = getPasswordByUserName(userName);

//        System.out.println("password:"+password);

        if (password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,password,"customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("mark"));
        return authenticationInfo;
    }

    private String getPasswordByUserName(String userName) {
        User user = userDao.getUserByUserName(userName);

        //访问数据库，获取凭证
        if (user != null) {
            return user.getPassword();
        }
        return null;

    }

    public static void main(String[] args) {
//        Md5Hash md5Hash = new Md5Hash("123456","mark",2);
//        System.out.println(md5Hash);






    }
}
