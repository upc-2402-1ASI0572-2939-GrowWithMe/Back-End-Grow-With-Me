package java.com.growwithme.iam.infrastructure.authorization.sfs.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class EmailPasswordAuthenticationTokenBuilder {
    public static UsernamePasswordAuthenticationToken build(
            UserDetails principal,
            HttpServletRequest request
    ) {
        var emailPasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );
        return emailPasswordAuthenticationToken;
    }
}
