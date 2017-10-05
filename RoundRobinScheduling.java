//Yuna Frolov 
//Yair Shlomi 


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinScheduling {


    public static void main(String[] args) {


        ArrayList <Process> processes = new ArrayList <Process>();
        float[] results = new float[3];
        int[] quantum = {1,50,1000};
        float best = Float.MAX_VALUE;
        int index = 0;
        Create_processes(processes);


        for(int i = 0; i < 3; i++) {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$\trunning with quantum time of: " + quantum[i] + "\t$$$$$$$$$$$$$$$$$$$$$$$$");
            results[i] = Scheduling(processes, quantum[i]);
            if (results[i] < best) {
                index = i;
                best = results[i];

            }
            processes.clear();
            Create_processes(processes);
            System.out.println("\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
        }

        System.out.println("the best quantum is: " + quantum[index] + ", with average wait time of: " + results[index]);





    }



    static void Create_processes (ArrayList <Process> processes)
    {
        int index = 0;
        processes.add(new Process(index, 0, 250));
        index++;
        processes.add(new Process(index, 50, 170));
        index++;
        processes.add(new Process(index, 130, 75));
        index++;
        processes.add(new Process(index, 190, 100));
        index++;
        processes.add(new Process(index, 210, 130));
        index++;
        processes.add(new Process(index, 350, 50));
    }

    static float  Scheduling (ArrayList processes, int quantum ) {

        ArrayList <Process> processes_temp = new ArrayList <Process>(processes);
        Queue <Process> Scheduler = new LinkedList<Process>();
        Queue <Process> ProcessQueue = new LinkedList<Process>(processes_temp);

        Process temp;
        int timer = 0;
        int total_wait = 0;
        int total_turnatound_time = 0;
        int total_burst = 0;
        float average_burst = 0;
        float average_wait = 0;
        float average_turnatound = 0;
        float wait_ratio = 0;
        int cpu_index = -1;
        int finished = 0;
        int offset = 0;
        boolean cpu_occupied = false;

        while (finished < 6 ) {

            //enqueue
            if(!ProcessQueue.isEmpty() && ProcessQueue.peek().getArrival_time() == timer){
                //System.out.println("process " + ProcessQueue.peek().getName() + " has entered the queue on " + timer + "." );
                Scheduler.add(ProcessQueue.poll());
            }

            //dequeue and execution
            if(!Scheduler.isEmpty() && !cpu_occupied) {
                temp = Scheduler.poll();
                cpu_occupied = true;
                cpu_index = temp.getIndex();
                if(processes_temp.get(cpu_index).getTimes_run() == 0 ) {
                    processes_temp.get(cpu_index).setStart_time(timer);
                   // System.out.println("process " + processes_temp.get(cpu_index).getName() + " started to get CPU time on " + timer + ".");
                }
               // else System.out.println("process " + processes_temp.get(cpu_index).getName() + " got CPU time on " + timer + ".");

                processes_temp.get(cpu_index).setTimes_run();

            }


            //update cpu times
            if (cpu_occupied && (timer != 0))
                processes_temp.get(cpu_index).setCpu_time();
           // }
            for (Process pr : Scheduler) {
                if(pr.getIndex() != cpu_index)
                    pr.setWait_time();
            }

            //process finished running
            if((cpu_occupied && (processes_temp.get(cpu_index).getBurst_time() - processes_temp.get(cpu_index).getCpu_time()) == 0)) {
                processes_temp.get(cpu_index).setFinish_time(timer);
                processes_temp.get(cpu_index).setFinished(true);
                processes_temp.get(cpu_index).setTurnatound_time();
               // System.out.println("**** process " + processes_temp.get(cpu_index).getName() + " finished running on " + timer + ". ****");
                cpu_occupied = false;
                cpu_index = -1;
                finished++;
                if (timer % quantum != 0)
                    offset = timer % quantum;

            }

           if ((timer - offset) % quantum == 0 && timer != 0 && cpu_occupied) {

            // System.out.println("Scheduler stopped running " + processes_temp.get(cpu_index).getName() + " on " + timer +
          //              " and now the process is in the queue again.");
         //      System.out.println("the cpu is now available");
               Scheduler.add(processes_temp.get(cpu_index));
               processes_temp.get(cpu_index).setArrival_temp(timer);

          //     System.out.println("process " + processes_temp.get(cpu_index).getName() + " has entered the queue on " + timer + "." );
               cpu_occupied = false;
               cpu_index = -1;




           }


            //dequeue and execution again in the same time slice
            if(!Scheduler.isEmpty() && !cpu_occupied) {
                temp = Scheduler.poll();
                cpu_occupied = true;
                cpu_index = temp.getIndex();
                processes_temp.get(cpu_index).setArrival_temp(timer);
                if(processes_temp.get(cpu_index).getTimes_run() == 0) {
                    processes_temp.get(cpu_index).setStart_time(timer);
                   // System.out.println("process " + processes_temp.get(cpu_index).getName() + " started to get CPU time on " + timer + ".");
                }
             //   else System.out.println("process " + processes_temp.get(cpu_index).getName() + " got CPU time on " + timer + ".");

                processes_temp.get(cpu_index).setTimes_run();
            }



            timer++;


        }


        //System.out.println();
        //System.out.println();
        System.out.println("****finished running****");
        //System.out.println();
       // System.out.println();

        System.out.println("\n##################################################################");

        //calculate and print the events times and the then the average times of running the processes
        for (Process temp_process: processes_temp) {
             System.out.println("arrival time of " + temp_process.getName() + " = " + temp_process.getArrival_time());
            System.out.println("start of CPU time of " + temp_process.getName() + " = " + temp_process.getStart_time());
            System.out.println("finish time of " + temp_process.getName() + " = " + temp_process.getFinish_time());
           // System.out.println("total wait time for " + temp_process.getName() + " = " + temp_process.getWait_time());
         //   System.out.println("total turnatound time for " + temp_process.getName() + " = " + temp_process.getTurnatound_time());
            System.out.println("************************************************************************");

            total_wait += temp_process.getWait_time();
            total_turnatound_time += temp_process.getTurnatound_time();
            total_burst += temp_process.getBurst_time() ;
        }

        average_wait = total_wait / 6;
        average_turnatound = total_turnatound_time / 6;
        //average_burst = total_burst / 6;

        System.out.println("the average waiting time for a process to finish execute, with quantum = " + quantum + " is " + average_wait + ".");
        System.out.println("the average time for a process from arriving the queue until finish execute, with quantum = " + quantum + " is " + average_turnatound + ".");


        processes_temp.clear();
        Scheduler.clear();
        ProcessQueue.clear();
        //this will be the parameter which will help us to decide which quantum time is the bust. the lower the better.
        //this parameter measures the average times that processes waits in the queue, waiting for cpu time
        return average_wait;



    }


}
























