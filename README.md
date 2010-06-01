Seam OpenID sample moded for accessing user attributes
------------------------------------------------------

This is an OpenID sample from the [Seam Framework][1] modified so as to automatically
retrieve user attributes during the sign-on process.

The main component of this sample is the [OpenIdAx][3] class (a sightly modified
version of the [OpenId class][2] in Seam).

[OpenIdAx][3] use the attribute exchange mechanism to obtain the
*email address*, *first name*, *last name* and *full name* from the user's OpenID provider.
Though each provider only support a subset of these attributes.

The application UI was also changed to display more information about the active user 
and to sign the Wall Posts with the poster *name* and *email address* if they're available.

![Screenshot](http://github.com/ajasmin/seam-openid-with-name-and-email/raw/master/screenshot.png)

The old openid4java jar file included in the 2.2.0.GA Seam distribution was also replaced
by a newer 0.9.5.593 version because the original had a number of issues.

To build this sample:

- edit build.xml to add jboss.home
- cd examples/openid
- ant explode

This project was motivated by a [Stackoverflow question][4]


  [1]: http://seamframework.org/
  [2]: https://anonsvn.jboss.org/repos/seam/branches/community/Seam_2_2/src/main/org/jboss/seam/security/openid/OpenId.java
  [3]: http://github.com/ajasmin/seam-openid-with-name-and-email/blob/master/examples/openid/src/org/jboss/seam/example/openid/OpenIdAx.java
  [4]: http://stackoverflow.com/questions/2936340/get-email-address-from-openid-using-jboss-seam