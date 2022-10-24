package com.akikun.family.security.authentication;

import org.springframework.lang.NonNull;
import com.akikun.family.security.support.UserDetail;

/**
 * Authentication.
 *
 * @author johnniang
 */
public interface Authentication {

    /**
     * Get user detail.
     *
     * @return user detail
     */
    @NonNull
    UserDetail getDetail();
}
