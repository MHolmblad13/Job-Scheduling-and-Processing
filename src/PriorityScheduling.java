import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PriorityScheduling {

	private int timeSlice; // units of time allowed to process at a time
	private int degreeMulti; // degree of multiprogramming
	private Scanner input; // input reader
	private int numJobs = 0; // job number variable used to keep track of how many jobs have been done
	private int numJobsTotal = 0; // permanent job number variable
	private int count = 0;
	private int jobInReady = 0;
	private int inputCount = 0;
	private int jobLoaded = 0; // this counter keeps track of how many jobs have been loaded
	private int completionCount = 0;
	private int pcount = 1; // used for sorting the ready queue by priority
	private int i = 0; // general counter
	private Job temp; // used for moving an element from the back of the queue to the front

	// time variables
	private int[] arrivalTime = new int[100];
	private int[] completionTime = new int[100];
	private int[] cpuTime = new int[100];
	private int[] ioTime = new int[100];
	private int readyQueueTime;
	private int totalTime = 0;

	private ArrayList<Job> jobs;
	private String[] jobInputList;

	private Queue<Job> load = new LinkedList<Job>();
	private Queue<Job> ready = new LinkedList<Job>();
	private Queue<Job> shuffle = new LinkedList<Job>(); // this queue is to help let us know what jobs are loaded and
	private Queue<Job> waiting = new LinkedList<Job>();
	private Queue<Job> waitingBackup = new LinkedList<Job>();
	private Queue<Job> priority = new LinkedList<Job>();
	private boolean jobsNotDone = true;

	public void runPriorityScheduling() throws FileNotFoundException {
		this.Reset();

		for (Job job : jobs) {
			System.out.println(totalTime + " Job " + job.getJobName() + " arrived.");
			load.add(job);
			arrivalTime[i] = totalTime;
			i++;
			numJobs++;
		}

		System.out.println("Num Jobs: " + numJobs);
		numJobs = numJobs / 2; // above for each loop is counting each job twice, this mitigates that feature
		numJobsTotal = numJobs;
		System.out.println("Num Jobs: " + numJobs);

		while (jobsNotDone) {
			// Get jobs ready that we can
			if (numJobsTotal != jobLoaded) {
				while (count < degreeMulti) {
					shuffle.add(load.poll());
					System.out.println(totalTime + " Job " + shuffle.element().getJobName() + " loaded and ready.");
					ready.add(shuffle.poll());
					jobLoaded++;
					System.out.println("Job loaded num: " + jobLoaded);
					count++; // takes care of degree of multiprogramming
				}
			}

			// Start running
			if (ready.isEmpty() == false) {
				PrioritySort();
				System.out.println(totalTime + " Job " + ready.element().getJobName() + " running.");
				totalTime += timeSlice;
				ready.element().decrement(timeSlice);

				// deal with IO or finish the job
				if (ready.element().checkInputLeft()) {
					int n = ready.element().getIOKindNum();
					if (n > 0) {
						String kind = ready.element().getIOKindString();
						System.out.println(totalTime + " Job " + ready.element().getJobName() + " needs " + kind);
						ready.element().getIONum(n);
						waiting.add(ready.poll());
					} else {
						System.out.println(totalTime + " Job " + ready.element().getJobName() + " done.");
						ready.element().obtainCompletionTime(totalTime);
						completionCount++;
						ready.poll();

						count--; // makes it so more jobs can be worked on
						numJobs--; // decrements total number of jobs left

						if (numJobs == 0) {
							jobsNotDone = false;
						}
					}
					// don't need to deal with IO
				} else {
					System.out.println(totalTime + " Job " + ready.element().getJobName() + " timed out.");
					temp = ready.poll();
					ready.add(temp);
				}

			}

			if (waiting.isEmpty() == false) {
				for (int j = 0; j < waiting.size(); j++) {
					waiting.element().decrementIO(timeSlice);
					System.out.println("decrement IO happened " + timeSlice);
					waitingBackup.add(waiting.poll());
				}
				if (waitingBackup.element().getIODone()) {
					System.out.println(totalTime + " Job " + waitingBackup.element().getJobName() + " I/O call done.");
					ready.add(waitingBackup.poll());
				}
				for (int j = 0; j < waitingBackup.size(); j++) {
					waiting.add(waitingBackup.poll());
				}

				if (ready.isEmpty() == true)
					totalTime += timeSlice;

			}

		}

		System.out.println("All jobs done at " + totalTime);
		i = 0;
		for (Job job : jobs) {
			System.out.println("The CPU time for " + job.getJobName() + " is " + cpuTime[i]);
			System.out.println("The IO time for " + job.getJobName() + " is " + job.getTotalIOTime());
			System.out.println("The time in ready queue for "+ job.getJobName() + " is " + (job.getTotalIOTime() - arrivalTime[i] - cpuTime[i] - job.getTotalIOTime()));
			System.out.println("The arrival time for " + job.getJobName() + " is " + arrivalTime[i]);
			System.out.println("The completion time for " + job.getJobName() + " is " + job.getCompletionTime());
			i++;
		}

	}

	private void Reset() throws FileNotFoundException {
		input = new Scanner(new File("job"));
		jobs = new ArrayList<Job>();
		jobInputList = new String[100];

		timeSlice = input.nextInt();
		degreeMulti = input.nextInt();
		input.nextLine();
		while (input.hasNext()) {
			jobs.add(new Job(input.nextLine()));
			numJobs++;
		}
	}

	private void PrioritySort() {
		pcount = 1;
		int tempPnum;
		int catchLoop = 0;
		
		while (!ready.isEmpty()) {
			tempPnum = ready.element().getPriorityNumber();
			if(tempPnum == pcount) {
				priority.add(ready.poll());
				pcount++;
				catchLoop = 0;
			} else {
				temp = ready.poll();
				ready.add(temp);
				catchLoop++;
				if(catchLoop == ready.size()) {
					pcount++;
					catchLoop = 0;
				}
			}
			
			
		}

		while(priority.isEmpty() == false) {
			ready.add(priority.poll());
		}
	}

}
