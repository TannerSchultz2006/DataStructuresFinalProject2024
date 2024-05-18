package cos.dstruct.klassroom.datatypes;

import com.google.common.hash.Hashing;
import cos.dstruct.klassroom.Exclude;
import cos.dstruct.klassroom.datastructures.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class User implements java.io.Serializable, Comparable<User> {

    public ArrayList<String> todo_list;
    @Exclude
    public LinkedList<String> banner_text;
    public MinHeap<Assignment> assignment_heap;
    // Due to the nature of JSON and the GSON serializer on save and load
    // I convert the linked list into an array and back so that it can be
    // serialized and deserialized, hence the arraylist.
    public ArrayList<String> linked_list;
    public ArrayList<Assignment> min_heap;
    private static final int version_max = 0;
    private final KHash Uhash;
    private final String username;
    private final String password;
    private final KHash Phash;

    public User(String username, String password) {
        this.username = username;
        Uhash = new KHash(username);
        this.password = password;
        Phash = new KHash(password);
        todo_list = new ArrayList<>();
        banner_text = new LinkedList<>("Welcome Silly Goose");
        linked_list = new ArrayList<>();
        linked_list.add(0, "Welcome Silly Goose");

        assignment_heap = new MinHeap<>();
        min_heap = new ArrayList<>();

    }

    public static class KHash implements Comparable<KHash> {

        public KHash(String str) {
            khash = Hashing.sha256().hashString(str, StandardCharsets.UTF_8).hashCode();
        }

        @Override
        public int compareTo(KHash o) {
            return khash.compareTo(o.khash);
        }
        Integer khash;

    }

    public String getUsername() {
        return username;
    }

    public void addAssignment(Assignment assignment) {
        assignment_heap.insert(assignment);
    }

    public KHash getUhash() {
        return Uhash;
    }

    public void attemptLogin(String input_password) {
        if (!password.equals(input_password)) {
            throw new IllegalArgumentException("Invalid Login Credentials, User may not login");
        }
    }

    public void prepareForSave() {
        linked_list = new ArrayList<>();
        for (Object banner : banner_text) {
            linked_list.add(linked_list.size(), (String) banner);
        }
        min_heap = assignment_heap.heap;
    }

    public void loadAfterSave() {
        banner_text = new LinkedList<>(linked_list);
        assignment_heap = new MinHeap<>(min_heap);
    }

    @Override
    public String toString() {
        return "Username: " + username + "\tPassword: " + password;
    }

    @Override
    public int compareTo(User o) {
        return this.Uhash.compareTo(o.Uhash);
    }

}
