package domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Topic extends DataModel {
    private int id;
    private String name;
    private List<Post> posts;
    private String url;

    public Topic(String name) {
        this.name = name;
    }

    @Override
    public String toJson() {
        Type topicType = (new TypeToken<Topic>() {}).getType();
        return (new Gson()).toJson(this, topicType);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
        this.url = rootURI + "/topics/" + id;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }
    public void addPost(Post post) {
        if (this.posts == null)
            this.posts = new ArrayList<Post>();
        this.posts.add(post);
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
