package cos.dstruct.klassroom.database;

import cos.dstruct.klassroom.datatypes.User;

import java.util.ArrayList;

public class UserTree {

    private final Node null_sentinel;
    private int size;

    public UserTree() {
        null_sentinel = new Node();
        size = 0;
    }

    private static class Node {

        Node() {
            left = null;
            right = null;
            user = null;
        }
        Node(Node left, User user, Node right) {
            this.left = left;
            this.user = user;
            this.right = right;
        }
        Node left;
        Node right;
        User user;

    }

    public void newUser(String username, String password) {
        instert(new User(username, password));
    }

    public void instert(User user) {
        Node current;
        Node node = new Node(null_sentinel, user, null_sentinel);

        if (size == 0) {
            null_sentinel.right = null_sentinel.left = node;
            size++;
        } else {
            current = null_sentinel.right;
            User.KHash uuid = user.getUhash();
            while (true) {
                int val = uuid.compareTo(current.user.getUhash());
                if (val < 0) {
                    if (current.left == null_sentinel) {
                        current.left = node;
                        size++;
                        break;
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null_sentinel) {
                        current.right = node;
                        size++;
                        break;
                    } else {
                        current = current.right;
                    }
                }
            }
        }
    }

    public User getUser(String username) {
        Node current = null_sentinel.right;
        User.KHash uuid = new User.KHash(username);
        while (true) {
            int val = uuid.compareTo(current.user.getUhash());
            if (val < 0) {
                current = current.left;
            } else if (val > 0) {
                current = current.right;
            } else {
                if (current != null_sentinel) {
                    return current.user;
                } else {
                    throw new IllegalArgumentException("No such user is found");
                }
            }
        }
    }

    public ArrayList<User> toUserList() {
        return recToUserList(null_sentinel.right);
    }

    public void fromUserList(ArrayList<User> user_list) {
        for (User user : user_list) {
            instert(user);
        }
    }

    private ArrayList<User> recToUserList(Node node) {
        ArrayList<User> user_list = new ArrayList<>();

        if (node.left != null_sentinel) {
            user_list.addAll(recToUserList(node.left));
        }

        if (node.right != null_sentinel) {
            user_list.addAll(recToUserList(node.right));
        }

        user_list.add(node.user);
        return user_list;

    }

}
