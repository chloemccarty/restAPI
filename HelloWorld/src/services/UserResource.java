package services;

import domain.DataModel;
import domain.User;
import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Path("/users")
public class UserResource {
    private static Map<Integer, User> userDB = new ConcurrentHashMap<Integer, User>();
    private static AtomicInteger idCounter = new AtomicInteger();

    public UserResource() { }

    // TODO look into this
    // subresource locator that creates a User to service the request
    public UserResource(Map<Integer, User> userDB) {
        this.userDB = userDB;
    }

    @POST
    @Consumes("application/json")
    public Response createUser(String user) {
        Gson gson = new Gson();
        User u = gson.fromJson(user, User.class);
        int id = idCounter.getAndIncrement();
        u.setId(id);
        userDB.put(id, u);

        //TODO return response
        return null;
    }

    @GET
    @Produces("application/json")
    public String getUsers() {
        //return "{ }";
        return getURLs(userDB.values());
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getUser(@PathParam("id") int id) {
        final User user = userDB.get(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return user.toJson();
    }



    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void updateUser(@PathParam("id") int id, String user) {
        Gson gson = new Gson();
        User update = gson.fromJson(user, User.class);
        User current = userDB.get(id);
        if (current == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        current.setAdmin(update.isAdmin());
        current.setPassword(update.getPassword());
        current.setBanned(update.isBanned());
        current.setBanEnd(update.getBanEnd());
        current.setNumPosts(update.getNumPosts());
        // current.setConversations(update.getConversations());

    }

    //TODO: should be post? Nah... do an empty PUT on this uri
    @PUT
    @Path("user/{id}/report")
    @Consumes("application/json")
    public void reportUser(@PathParam("id") int id) {
        // TODO: implement user reporting
    }

    @PUT
    @Path("user/{id}/ban")
    @Consumes("application/json")
    public void banUser(@PathParam("id") int id) {
        // TODO: implement user banning
    }

    /// Adds the post to the database of posts and gives it an ID
    public static void addUser(User u) {
        int id = idCounter.getAndIncrement();
        u.setId(id);
        userDB.put(id, u);
    }

    // Helper Methods for Parsing
    private String getURL(int id) {
        return DataModel.rootURI + "/users/" + id;
    }
    private String getURLs(Collection<User> users) {
        List<String> urls = new ArrayList<String>();
        for (User u: users) {
            urls.add(getURL(u.getId()));
        }
        Gson gson = new Gson();
        return gson.toJson(urls);
    }
}
