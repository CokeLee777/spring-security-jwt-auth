package io.github.cokelee777.springsecurityjwtauth.security.token.domain;

import java.io.Serializable;
import java.util.Date;

public interface Token extends Serializable {

    Date createdAt = new Date();

}
