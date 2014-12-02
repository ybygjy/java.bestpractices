package org.ybygjy.ftp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.AbstractUserManager;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.ftpserver.util.EncryptUtils;

/**
 * 适用于CNGI项目的FTP身份认证
 * @author WangYanCheng
 * @version 2011-6-16
 */
public class CNGIFtpUserManager extends AbstractUserManager {
    private static Map<String, User> userInfo = new HashMap<String, User>();
    static {
        BaseUser adminUser = new BaseUser();
        adminUser.setHomeDirectory("C:\\");
        adminUser.setEnabled(true);
        adminUser.setName("admin");
        adminUser.setPassword(EncryptUtils.encryptMD5("admin"));
        adminUser.setMaxIdleTime(0);
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission("/"));
        authorities.add(new ConcurrentLoginPermission(0, 0));
        authorities.add(new TransferRatePermission(0, 0));
        adminUser.setAuthorities(authorities);
        BaseUser anonymous = new BaseUser();
        anonymous.setHomeDirectory("D:\\");
        anonymous.setEnabled(true);
        anonymous.setName("anonymous");
        userInfo.put("admin", adminUser);
        userInfo.put("anonymous", anonymous);
    }
    public CNGIFtpUserManager(String adminName, PasswordEncryptor passwordEncryptor) {
        super(adminName, passwordEncryptor);
    }
    public User authenticate(Authentication authentication) throws AuthenticationFailedException {
        User rtnUser = null;
        if (authentication instanceof UsernamePasswordAuthentication) {
            UsernamePasswordAuthentication upauth = (UsernamePasswordAuthentication) authentication;
            String user = upauth.getUsername();
            String password = upauth.getPassword();
            if (userInfo.containsKey(user)) {
                rtnUser = userInfo.get(user);
                rtnUser = getPasswordEncryptor().matches(password, rtnUser.getPassword()) ? rtnUser : null;
            }
        } else if (authentication instanceof AnonymousAuthentication) {
            try {
                if (doesExist("anonymous")) {
                    return getUserByName("anonymous");
                } else {
                    throw new AuthenticationFailedException("Authentication failed");
                }
            } catch (AuthenticationFailedException e) {
                throw e;
            } catch (FtpException e) {
                throw new AuthenticationFailedException("Authentication failed", e);
            }
        } else {
            throw new IllegalArgumentException("Authentication not supported by this user manager");
        }
        return rtnUser;
    }

    public void delete(String username) throws FtpException {
        if (userInfo.containsKey(username)) {
            userInfo.remove(username);
        }
    }

    public boolean doesExist(String username) throws FtpException {
        return userInfo.containsKey(username);
    }

    public String[] getAllUserNames() throws FtpException {
        String[] userKey = new String[userInfo.keySet().size()];
        userKey = userInfo.keySet().toArray(userKey);
        return userKey;
    }

    public User getUserByName(String username) throws FtpException {
        User rtnUser = null;
        if (doesExist(username)) {
            rtnUser = (User) userInfo.get(username);
        }
        return rtnUser;
    }

    public void save(User user) throws FtpException {
        if (!doesExist(user.getName())) {
            userInfo.put(user.getName(), user);
        }
    }

}
