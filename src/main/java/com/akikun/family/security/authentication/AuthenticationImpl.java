package com.akikun.family.security.authentication;

import com.akikun.family.security.support.UserDetail;

/**
 * Authentication implementation.
 *
 * @author johnniang
 */
public class AuthenticationImpl implements Authentication {

    private final UserDetail userDetail;

    public AuthenticationImpl(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @Override
    public UserDetail getDetail() {
        return userDetail;
    }
}
