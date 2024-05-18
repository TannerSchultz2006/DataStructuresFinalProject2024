package cos.dstruct.klassroom;

import com.google.gson.*;
import cos.dstruct.klassroom.controllers.*;
import cos.dstruct.klassroom.database.*;
import cos.dstruct.klassroom.datatypes.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Klassroom extends Application {


    public Scene signup_screen;
    public Scene login_screen;
    public Scene home_screen;
    public Scene assignment_screen;
    public Stage assingment_stage;
    public Stage stage;

    // See @Exclude.java for more details about the code and code generation.
    public ExclusionStrategy exclusionAnnotation = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(Exclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };
    public User active_user;
    public UserTree user_tree;
    private HomeHandler home_handler;

    public static class LocalDateToGSON implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }

    }

    public static void main(String[] args) {
        launch();
    }

    public User getActiveUser() {
        return active_user;
    }

    public void pushTodo(String todo) {
        active_user.todo_list.add(todo);
    }

    public void popTodo() {
        active_user.todo_list.remove(active_user.todo_list.size() - 1);
    }

    public void loginUser(String username, String password) throws IllegalArgumentException {
        User testUser;
        try {
            // If wrong username or password catches IllegalArgException
            testUser = user_tree.getUser(username);
            testUser.attemptLogin(password);

            active_user = testUser;
            active_user.loadAfterSave();

            stage.setScene(home_screen);
            home_handler.startUpHome();
            refreshHome();
            stage.show();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username, String password) throws IllegalArgumentException {
        user_tree.newUser(username, password);
    }

    public void showLoginScreen() {
        stage.setScene(login_screen);
        stage.show();
    }

    public void showSignupScreen() {
        stage.setScene(signup_screen);
        stage.show();
    }

    public void showAssignmentScreen() {
        assingment_stage.show();
    }

    public void saveState() {
        try {
            active_user.prepareForSave();
            UserList user_list = new UserList();
            user_list.user_list = user_tree.toUserList();
            Files.writeString(Path.of("UserList.json"), gson.toJson(user_list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signOut() {
        active_user = null;
        stage.setScene(login_screen);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initVariables();
        setupScreens();
        this.stage = stage;
        try {
            stage.setTitle("Klassroom");
            stage.setScene(login_screen);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshHome() {
        home_handler.updateStackListview();
        home_handler.updateAssignmentQueue();
    }

    public void addAssignment(String assignment_name, Integer points, LocalDate date) {
        active_user.addAssignment(new Assignment(assignment_name, points, date));
    }

    private void initVariables() throws IOException {
        gson = gson.newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateToGSON()).create();
        Path user_path = Path.of("UserList.json");
        try {
            Files.createFile(user_path);
        } catch (Exception e) {
            Files.setLastModifiedTime(user_path, FileTime.from(Instant.now()));
        }
        UserList user_list = gson.fromJson(Files.readString(user_path), UserList.class);
        if (user_list == null) {
            user_list = new UserList();
        }
        user_tree = new UserTree();
        user_tree.fromUserList(user_list.user_list);
    }

    private void setupScreens() {

        FXMLLoader login_loader = new FXMLLoader(getClass().getResource("/loginscene.fxml"));
        FXMLLoader signup_loader = new FXMLLoader(getClass().getResource("/signupscene.fxml"));
        FXMLLoader home_loader = new FXMLLoader(getClass().getResource("/homescene.fxml"));
        FXMLLoader assignment_loader = new FXMLLoader(getClass().getResource("/createassignment.fxml"));

        home_loader.setController(home_handler = new HomeHandler(this));
        home_loader.setRoot(home_screen);

        login_loader.setController(new LoginHandler(this));
        login_loader.setRoot(login_screen);

        signup_loader.setController(new SignupHandler(this));
        signup_loader.setRoot(signup_screen);

        assignment_loader.setController(new AssignmentHandler(this));
        assignment_loader.setRoot(assignment_screen);

        assingment_stage = new Stage();

        try {
            signup_screen = signup_loader.load();
            login_screen = login_loader.load();
            home_screen = home_loader.load();
            assignment_screen = assignment_loader.load();

            assingment_stage.setScene(assignment_screen);
        } catch (IOException e) {
            System.err.println("YOU SILLY GOOSE");
            e.printStackTrace();
        }
    }
    Gson gson = new GsonBuilder().addSerializationExclusionStrategy(exclusionAnnotation).create();

}
