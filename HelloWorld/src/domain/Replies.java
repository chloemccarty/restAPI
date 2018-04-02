package domain;

import java.util.List;

public class Replies {
    private int id;
    private Post parent;
    private List<Post> replies;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Post getParent() {
        return parent;
    }
    public void setParent(Post parent) {
        this.parent = parent;
    }

    public List<Post> getReplies() {
        return replies;
    }
    public void addReply(Post reply) {
        this.replies.add(reply);
    }
}
