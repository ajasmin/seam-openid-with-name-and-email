package org.jboss.seam.example.openid;

import org.jboss.seam.annotations.*;

@Name("authenticator")
public class Auth
{
    @In(create=false) OpenIdAx openidax;

    public boolean authenticate()
    {
        System.out.println("AUTH: " + openidax + "-" + openidax.getValidatedId());
        
        return true;
    }


    public boolean authenticateOpenID() {
        return true;
    }

}
