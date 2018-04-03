package domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class Post extends DataModel {
    int id;
    private String author;
    private int authorId;
    private Post parent;
    private String body;
    private Date date;
    private Topic topic;
    private Replies replies;
    private String url;

    // TODO remove after done testing
    public Post(String author, int authorId, String body, Topic topic) {
        this.author = author;
        this.authorId = authorId;
        this.body = body;
        this.topic = topic;
    }

    @Override
    public String toJson() {
        Type postType = (new TypeToken<Post>() {}).getType();
        return (new Gson()).toJson(this, postType);
    }

    // TODO every datamodel setId method should also set the URL
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
        this.url = rootURI + "/posts/" + id;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Post getParent() {
        return parent;
    }
    public void setParent(Post parent) {
        this.parent = parent;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Topic getTopic() {
        return topic;
    }
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Replies getReplies() {
        return replies;
    }
    public void addReply(Post reply) {
        this.replies.addReply(reply);
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
