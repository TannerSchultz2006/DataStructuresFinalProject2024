package cos.dstruct.klassroom.datatypes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Assignment implements Comparable<Assignment> {


    public Assignment(String title, Integer points, LocalDate due_date) {
        this.title = title;
        this.due_date = due_date;
        this.points = points;
        updateDaysUntilDue();
        updatePriority();
    }

    public void updatePriority() {
        updateDaysUntilDue();
        priority = Double.valueOf(points) / days_until_due;
    }

    @Override
    public int compareTo(Assignment o) {

        this.updatePriority();
        o.updatePriority();
        return Double.compare(this.priority, o.priority);
    }

    @Override
    public String toString() {
        return title + " due on " + due_date + " (" + days_until_due + " days until due)\n" + "Worth " + points +
                " points  " + " (priority (" + points + "/" + days_until_due + ") = " + (double) points / days_until_due +
                ") ";
    }

    private void updateDaysUntilDue() {
        days_until_due = ChronoUnit.DAYS.between(LocalDate.now(), due_date);
    }
    String title;
    LocalDate due_date;
    Integer points;
    Long days_until_due;
    Double priority;

}
