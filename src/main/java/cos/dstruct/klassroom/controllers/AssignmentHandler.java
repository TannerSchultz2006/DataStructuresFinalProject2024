package cos.dstruct.klassroom.controllers;

import cos.dstruct.klassroom.Klassroom;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.time.LocalDate;

public class AssignmentHandler {

    public TextField name_textfield;
    public TextField points_textfield;
    public DatePicker duedate_datefield;
    private final Klassroom klassroom;

    public AssignmentHandler(Klassroom klassroom) {
        this.klassroom = klassroom;
    }


    public void createAssignmentEvent(ActionEvent event) {

        try {
            LocalDate date = duedate_datefield.getValue();

            String assignment_name = name_textfield.getText();
            Integer points = Integer.parseInt(points_textfield.getText());

            klassroom.addAssignment(assignment_name, points, date);

            name_textfield.setText("");
            points_textfield.setText("");
            klassroom.assingment_stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        } finally {
            klassroom.refreshHome();
        }

    }

}
