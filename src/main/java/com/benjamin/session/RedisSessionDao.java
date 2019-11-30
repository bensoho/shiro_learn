package com.benjamin.session;

import com.benjamin.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

public class RedisSessionDao extends AbstractSessionDAO {

    @Resource
    private JedisUtil jedisUtil;
    private final String shiro_session_prefix="imooc-session";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);


        return null;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        return null;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {

    }

    @Override
    public void delete(Session session) {

    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }
}
