package services;

import com.google.gson.Gson;
import domain.DataModel;
import domain.Post;
import domain.Topic;


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
    @Produces("application/json")
    public String getTopics() {
        return "{}";
        //return getURLs(topicDB.values());
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

    @POST
    @Path("{id}")
    @Consumes("application/json")
    public Response writePost(@PathParam("id") int id, String post) {
        Gson gson = new Gson();
        Post p = gson.fromJson(post, Post.class);
        // TODO: figure out how the heck to get the next id
        PostResource.addPost(p);

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

    @GET
    @Produces("application/json")
    public String getTopic(@QueryParam("search") String search) {
        List<Topic> matches = new ArrayList<Topic>();
        for (Topic e : topicDB.values()) {
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
