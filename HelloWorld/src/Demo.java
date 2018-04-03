import com.google.gson.Gson;
import domain.Post;
import domain.Topic;
import domain.User;
import services.TopicResource;

public class Demo {

    public static void main(String[] args) {
        User me = new User("mark", "password", true);
        Gson gson = new Gson();
        System.out.println(me.toJson());

        Topic t = new Topic("Fortnite");
        Post post = new Post("mark", 0, "this is a cool game", t);
        System.out.println(gson.toJson(t));
        System.out.println(gson.toJson(post));
    }
}
