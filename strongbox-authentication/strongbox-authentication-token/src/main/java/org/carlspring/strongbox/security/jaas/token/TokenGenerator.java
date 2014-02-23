package org.carlspring.strongbox.security.jaas.token;

import org.carlspring.strongbox.security.jaas.User;

/**
 * @author mtodorov
 */
public class TokenGenerator
{


    public Token generateToken(User user)
    {
        Token token = new Token();
        token.setUsername();
        token.setPassword();

        return token;
    }

}
