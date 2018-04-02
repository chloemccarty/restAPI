package services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class BoardApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> perRequests = new HashSet<Class<?>>();

    public BoardApplication() {
        singletons.add(new UserResource());
        // need them to be per-requests so i can put JAX-RS annotatoins on them
        // see page 58
        perRequests.add(UserResource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return perRequests;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
