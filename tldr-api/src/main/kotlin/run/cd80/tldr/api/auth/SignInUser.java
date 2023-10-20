package run.cd80.tldr.api.auth;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;

public interface SignInUser extends OAuth2User, Serializable {
}
