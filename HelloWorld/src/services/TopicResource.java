package services;

import com.google.gson.Gson;
import domain.DataModel;
import domain.Post;
import domain.Topic;
import domain.User;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/topics")
public class TopicResource {

    private static Map<Integer, Topic> topicDB = new ConcurrentHashMap<Integer, Topic>();
    private static AtomicInteger idCounter = new AtomicInteger();

    // probably has no message body
    @GET
   // @Path("/")
    @Produces("application/json")
    public String getTopics() {
        return getURLs(topicDB.values());
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getTopic(@PathParam("id") int id) {
        Topic topic = topicDB.get(id);
        /* if (topic == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } */
        return topic.toJson();
    }


    // TODO include userId as a header parameter in every call
    @POST
    @Path("{id}")
    @Consumes("application/json")
    public Response writePost(@PathParam("id") int id, String post) {
        Gson gson = new Gson();
        Post p = gson.fromJson(post, Post.class);
        // add the post to our database of posts
        PostResource.addPost(p);

        // update the user
        int uid = p.getAuthorId();
        User u = UserResource.getUserRef(uid);
        u.incrNumPosts();

        // update the topic
        Topic topic = topicDB.get(id);
        topic.addPost(p);

        // TODO: create response
        return null;
    }

    @POST
    @Consumes("application/json")
    public Response createTopic(String topic) {
        // add topic
        Gson gson = new Gson();
        Topic t = gson.fromJson(topic, Topic.class);
        addTopic(t);
        // TODO: return a response
        return null;
    }

    // TODO: for some reason, this method is being called for a
    // GET on /topics
    @GET
    @Path("?")
    @Produces("application/json")
    public String getTopic(@QueryParam("search") String search) {
        List<Topic> matches = new ArrayList<Topic>();
        if (topicDB.values() == null)
            System.out.println("Oops!");
        for (Topic e : topicDB.values()) {
            if (e == null) {
                System.out.println("wazoops");
                return "{oops}";
            }
            if (e.getName().contains(search)) {
                matches.add(e);
            }
        }

        return getURLs(matches);
    }

    // Helper Methods for Parsing
    private String getURL(int id) {
        return DataModel.rootURI + "/topics/" + id;
    }
    private String getURLs(Collection<Topic> topics) {
        List<String> urls = new ArrayList<String>();
        for (Topic t: topics) {
            urls.add(getURL(t.getId()));
        }
        Gson gson = new Gson();
        return gson.toJson(urls);
    }

    /// Adds the topic to the database of posts and gives it an ID
    public static void addTopic(Topic t) {
        int id = idCounter.getAndIncrement();
        t.setId(id);
        topicDB.put(id, t);
    }
}
