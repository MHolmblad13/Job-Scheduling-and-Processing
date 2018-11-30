# Job Scheduling and Processing

## Read in a job file which follows the specific format as follows:

25 (This number represents CPU Burst Time)

2  (This number represents the degree of multiprogramming that the system has) 

### The rest of the lines in the file follow the following format:

JobName Priority BurstTime I/O BurstTime I/O ....

 * Where BurstTime is any integer
  
 * I/O will either be a T for interactive I/O, O for output, or I for input
  
 * There burst times are 200, 50, 50 respectively.
  
## Execution  
  
Once the job file is read in, then the simulates an operating system completing all the jobs.

The program then outputs the Computation Time, I/O time, Ready Queue Time, arrival time, and completion time.

The program also runs the jobs in a round robin fashion and by priority.

This was a project in my Operating Systems class.
