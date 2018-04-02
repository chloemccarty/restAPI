package services;

import domain.DataModel;
import com.google.gson.Gson;
import domain.Post;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/posts")
public class PostResource {

    private static Map<Integer, Post> postDB = new ConcurrentHashMap<Integer, Post>();
    private static AtomicInteger idCounter = new AtomicInteger();

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getPost(@PathParam("id") int id) {
        Post post = postDB.get(id);
        if (post == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return post.toJson();
    }

    @GET
    @Path("{id}/replies")
    @Produces("application/json")
    public String getReplies(@PathParam("id") int id) {
        Gson gson = new Gson();
        List<Post> replies = postDB.get(id).getReplies().getReplies();
        List<String> urls = new ArrayList<String>();

        return getURLs(replies);
    }

    @GET
    @Produces("application/json")
    public String getPosts(@QueryParam("search") String search){
        List<Post> matches = new ArrayList<Post>();

        for (Post e : postDB.values()) {
            if (e.getBody().contains(search)) {
                matches.add(e);
            }
        }
        return getURLs(matches);
    }

    /// Returns list of links to the posts by the given authors
    @GET
    @Path("author/{id}")
    @Produces("application/json")
    public String getPosts(@PathParam("id") int authorId) {
        List<Post> matches = new ArrayList<Post>();
        for (Post e : postDB.values()) {
            if (e.getAuthorId() == authorId) {
                matches.add(e);
            }
        }

        return getURLs(matches);
    }

    @POST
    @Path("{id}/replies")
    @Consumes("application/json")
    public Response createReply(@PathParam("id") int parentId, String post) {
        Gson gson = new Gson();
        Post reply = gson.fromJson(post, Post.class);
        addPost(reply);

        postDB.get(parentId).addReply(reply);
        // TODO return Response
        return null;
    }

    // Helper Methods for Parsing
    private static String getURL(int id) {
        return DataModel.rootURI + "/posts/" + id;
    }
    private static String getURLs(Collection<Post> posts) {
        List<String> urls = new ArrayList<String>();
        for (Post p: posts) {
            urls.add(getURL(p.getId()));
        }
        Gson gson = new Gson();
        return gson.toJson(urls);
    }

/// Adds the post to the database of posts and gives it an ID
    public static void addPost(Post p) {
        int id = idCounter.getAndIncrement();
        p.setId(id);
        postDB.put(id, p);
    }
}
