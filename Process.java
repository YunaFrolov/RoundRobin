//Yuna Frolov
//Yair Shlomi

public class Process {


    int index;  //index in the processes table
    int arrival_time; //arrival time to the queue
    int arrival_temp; //updating arrival time to the queue after
    int burst_time;  //total time needed for execution
    int start_time; //time when first got cpu time
    int finish_time; // time when finished running
    int cpu_time; // total cpu time so far
    int wait_time; // total time spent waiting in queue
    int turnatound_time; //time since entering the queue until finished
    int times_run;
    boolean finished; // flag to know if the process has finished


    String name; //name of process;

    public Process (int index, int arrival_time, int burst_time){
        this.arrival_time = arrival_time;
        arrival_temp = arrival_time;
        this.burst_time = burst_time;
        this.index = index;
        this.name = "P"+index;
        finish_time = 0;
        finished = false;
        times_run = 0;

    }

    public int getIndex() {
        return index;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public int getArrival_temp() {
        return arrival_temp;
    }

    public void setArrival_temp(int arrival_temp) {
        this.arrival_temp = arrival_temp;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(int finish_time) {
        this.finish_time = finish_time;
    }

    public int getCpu_time() {
        return cpu_time;
    }

    public void setCpu_time() {
        cpu_time++;
    }

    public int getWait_time() {
        return wait_time;
    }

    public void setWait_time() {
        wait_time++;
    }

    public int getTurnatound_time() {
        return turnatound_time;
    }

    public void setTurnatound_time() {
        turnatound_time = finish_time - arrival_time;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getTimes_run() {
        return times_run;
    }

    public void setTimes_run() {
        times_run++;
    }

    public String getName() {
        return name;
    }


}

