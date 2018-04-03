package com.hzchina.account.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @ClassName: StatelessDefaultSubjectFactory
 * @Description: 用于控制session的生成
 * @author tjf
 * @date 2016年4月8日下午3:26:20
 * @Version V1.00
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
