package ua.sumdu.j2se.Anton.tasks;

import java.util.Objects;

public class Task implements Cloneable {
    private String title;
    private int time;

    private int start;
    private int end;
    private int interval;

    private boolean active;

    private boolean repeatable;

    /**
     * constructor constructs an inactive task to run at a specified time without repeating with a given name.
     */
    public Task(String title, int time) {
        //The constructor of a Task class should necessarily generate the IllegalArgumentException exception in the case when the time was set as a negative number;
        if (time <= 0) {
            throw new IllegalArgumentException("Time was set as a negative number!");
        }
        if (title == null) {
            throw new NullPointerException("Title was set as a wrong!");
        }
        repeatable = false;
        this.title = title;
        this.time = time;


    }

    /**
     * constructor constructs an inactive task to run within the specified time range
     * (including the start and the end time) with the set repetition interval and with a given name.
     */
    public Task(String title, int start, int end, int interval) {
        if (start < 0) {
            throw new IllegalArgumentException("Start was set as a negative number!");
        }
        if (end <= start) {
            throw new IllegalArgumentException("End was set as a wrong number!");
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval was set as a wrong number!");
        }
        if (title == null) {
            throw new NullPointerException("Title was set as a wrong!");
        }
        repeatable = true;
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time &&
                start == task.start &&
                end == task.end &&
                interval == task.interval &&
                active == task.active &&
                repeatable == task.repeatable &&
                title.equals(task.title);
    }


    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active, repeatable);
    }

    @Override
    protected Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        if (isRepeated()) {
            return new StringBuilder("This repeatable Task with following parameters: ").append("\nActivity: ").append(active).append("\nTitle: ").append(title).append("\nStart time: ").append(start).append("\nEnd time: ").append(end).append("\nInterval: ").append(interval).toString();
            //  return null;
        } else {
            return new StringBuilder("This non repeatable Task with following parameters: ").append("\nActivity: ").append(active).append("\nTitle: ").append(title).append("\nTime: ").append(time).toString();
        }


    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new NullPointerException("Title was set as a wrong!");
        }
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeated() {
        return repeatable;
    }

    /**Methods for reading and changing execution time for non-repetitive tasks:v*/

    /**
     * if the task is a repetitive one, the method must return the start time of the repetition;
     */
    public int getTime() {
        if (!isRepeated()) {
            return time;
        } else return start;

    }

    /**
     * if the task was a repetitive one, it should become non-repetitive.
     */

    public void setTime(int time) {
        if (time <= 0) {
            throw new IllegalArgumentException("Time was set as a negative number!");
        }
        this.time = time;
        if (isRepeated()) {
            this.start = 0;
            this.end = 0;
            this.interval = 0;
            repeatable = false;
        }
    }

    /** Methods for reading and changing execution time for repetitive tasks:*/

    /**
     * if the task is a non-repetitive one, the method must return the time of the execution;
     */
    public int getStartTime() {
        if (isRepeated()) {
            return start;
        } else return time;
    }

    public int getEndTime() {
        if (isRepeated()) {
            return end;
        } else return time;
    }

    public int getRepeatInterval() {
        if (isRepeated()) {
            return interval;
        } else return 0;
    }

    /**
     * if the task is a non-repetitive one, it should become repetitive.
     */

    public void setTime(int start, int end, int interval) {
        if (start < 0) {
            throw new IllegalArgumentException("Start was set as a negative number!");
        }
        if (end <= start) {
            throw new IllegalArgumentException("End was set as a wrong number!");
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval was set as a wrong number!");
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        if (!isRepeated()) {
            this.time = 0;
            repeatable = true;
        }
    }

    /**
     You should add the int nextTimeAfter (int current)
     method to the Task class that returns the next start time of the task execution after the current time.
     If after the specified time the task is not executed anymore, the method must return -1.
     */

    /**
     * current - time at this moment
     */
    public int nextTimeAfter(int current) {

        //for non repeatable task:
        if (!isRepeated()) {
            if (time <= current) {
                //active =false;
                return -1; //
            } else {
                return time;
            }
        }
        //for  repeatable task:
        if (current > end) {
            return -1;
        }
        if (current < start) {
            return start;
        }
        //helpful variable's
        //last Iteration with current
        int count = start;
        // calculation last Iteration w/o current
        long lastIteration = end - start % interval == 0 ? end : end - ((end - start) % interval);
        // System.out.println("LastIretation w/o current: " + lastIteration);
        //the task will not be executed anymore
        //calculation last Iteration with current
        while (count <= current) {
            count += interval;
        }
        if (current >= lastIteration | count > lastIteration) {
            return -1;
        }
        // return last Iteration with current
        return count;

    }


}

