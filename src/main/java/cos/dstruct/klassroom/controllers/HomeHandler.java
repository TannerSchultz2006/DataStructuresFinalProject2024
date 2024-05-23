package cos.dstruct.klassroom.controllers;

import cos.dstruct.klassroom.Klassroom;
import cos.dstruct.klassroom.datastructures.MinHeap;
import cos.dstruct.klassroom.datatypes.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.*;

public class HomeHandler {

    public ListView<String> todo_listview;

    public ListView assignments_listview;

    public Label rotating_banner;
    public BannerManager banner_manager;


    public TextField banner_field;
    public TextField todo_textfield;
    public HomeHandler(Klassroom klassroom) {
        this.klassroom = klassroom;
    }

    class BannerManager extends TimerTask {

        public BannerManager(User user, Label banner) {
            this.banner = banner;
            this.user = user;
            banner.setText(user.banner_text.getData());
        }

        @Override
        public void run() {
            user.banner_text.next();
            Platform.runLater(() -> banner.setText(user.banner_text.getData()));
        }

        private void removeBannerText() {
            user.banner_text.remove();
        }

        private void addBannerText(String banner_text) {
            user.banner_text.insert(banner_text);
        }
        User user;
        Label banner;

    }

    public void startUpHome() {
        active_user = klassroom.getActiveUser();
        updateStackListview();
        banner_manager = new BannerManager(active_user, rotating_banner);
        Timer banner_timer = new Timer();
        banner_timer.schedule(banner_manager, 0, 3000);
        klassroom.stage.setOnCloseRequest(e -> {
            banner_timer.cancel();
            Platform.exit();
        });
    }

    public void updateStackListview() {
        todo_listview.getItems().clear();
        ArrayList<String> users_todolist = klassroom.getActiveUser().todo_list;
        List<String> reversed_list = users_todolist.reversed();


        todo_listview.getItems().addAll(reversed_list);
    }

    public void addAssignmentScreenEvent(ActionEvent event) {
        klassroom.showAssignmentScreen();
    }

    public void saveState(ActionEvent event) {
        klassroom.saveState();
    }

    public void popTodo(ActionEvent event) {
        todo_listview.getItems().removeFirst();
        klassroom.popTodo();
    }

    public void pushTodo(ActionEvent event) {
        klassroom.pushTodo(todo_textfield.getText());
        updateStackListview();
        todo_textfield.setText("");
    }

    public void removeBannerEvent(ActionEvent event) {
        banner_manager.removeBannerText();
    }

    public void addBannerEvent(ActionEvent event) {
        String banner_text = banner_field.getText();
        banner_manager.addBannerText(banner_text);
        banner_field.setText("");
    }

    public void dequeueEvent(ActionEvent event) {
        active_user.min_heap.removeFirst();
        active_user.assignment_heap = new MinHeap<>(active_user.min_heap);
        updateAssignmentQueue();
    }

    public void signOutEvent(ActionEvent event) {
        banner_manager.cancel();
        klassroom.signOut();
    }

    public void updateAssignmentQueue() {
        assignments_listview.getItems().clear();
        assignments_listview.getItems().addAll(MinHeap.heapSort(klassroom.getActiveUser().assignment_heap.heap));
    }
    Klassroom klassroom;
    User active_user;

}
