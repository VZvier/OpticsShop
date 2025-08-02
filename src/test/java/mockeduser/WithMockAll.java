package mockeduser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(value = "MockRolesAllRoles", roles = {"USER", "STAFF", "SYSADMIN"})
public @interface WithMockAll {
}
