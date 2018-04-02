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

    @Override
    public String toJson() {
        Type postType = (new TypeToken<Post>() {}).getType();
        return (new Gson()).toJson(this, postType);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // TODO: should i have an authorID instead of author string?

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
