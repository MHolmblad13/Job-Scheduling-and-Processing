
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Job {
	private String jobDes; // all job info
	private String jobName; // name of job
	private int inputAmt; // additional inputs
	private int[] inputsNum = new int[100];
	private String[] inputsIO = new String[100];
	private int[] inputsIONum = new int[100];
	private String priorityNumber;
	private int pnum;
	private Scanner jobReader;

	// counters
	private int i = 0;
	private int inputsNumCount = 0;
	private int inputsIOCount = 0;
	private int inputsIONumCount = 0;
	private boolean IODone = false;
	
	private int totalIOTime;
	private int completionTime;

	public Job(String jobDes) {
		this.jobDes = jobDes;
		jobReader = new Scanner(this.jobDes);
		jobName = jobReader.next();
		priorityNumber = jobReader.next();
		pnum = Integer.parseInt(priorityNumber);

		while (jobReader.hasNext()) {
			inputsNum[i] = jobReader.nextInt();
			if (jobReader.hasNext())
				inputsIO[i] = jobReader.next();
			i++;
		}

		i = 0;

		inputAmt = inputsNum.length + inputsIO.length;
	}

	public String getJobName() {
		return jobName;
	}

	public int getInputAmt() {
		return inputAmt;
	}

	public int[] getInputsNum() {
		return inputsNum;
	}

	public String[] getInputsIO() {
		return inputsIO;
	}

	public int getPriorityNumber() {
		return pnum;
	}

	public void decrement(int timeSlice) {
		inputsNum[inputsNumCount] -= timeSlice;
	}

	public boolean checkInputLeft() {
		if (inputsNum[inputsNumCount] <= 0) {
			inputsNumCount++;
			return true;
		} else
			return false;
	}

	public int getIOKindNum() {
		if (inputsIO[inputsIOCount] == null) {
			return 0;
		} else {
			switch (inputsIO[inputsIOCount]) {
				case "I":
					totalIOTime+=50;
					return 50;
				case "O":
					totalIOTime+=50;
					return 50;
				case "T":
					totalIOTime+=200;
					return 200;
				default:
					return 0;
			}
		}
	}

	public String getIOKindString() {
		switch (inputsIO[inputsIOCount]) {
		case "I":
			inputsIOCount++;
			return "Input IO";
		case "O":
			inputsIOCount++;
			return "Output IO";
		default:
			inputsIOCount++;
			return "Terminal IO";
		}
	}

	public void getIONum(int n) {
		if (n == 50)
			inputsIONum[inputsIONumCount] = 50;
		else
			inputsIONum[inputsIONumCount] = 200;
	}

	public void decrementIO(int timeSlice) {
		inputsIONum[inputsIONumCount] -= timeSlice;
		if (inputsIONum[inputsIONumCount] <= 0) {
			IODone = true;
			inputsIONumCount++;
		}
	}

	public boolean getIODone() {
		return IODone;
	}

	public void incrementCount() {
		i++;
	}

	public int getTotalIOTime() {
		return totalIOTime;
	}

	public int totalNumTime() {
		int sum = IntStream.of(inputsNum).sum();
		return sum;
	}
	
	public void obtainCompletionTime(int totalTime) {
		completionTime = totalTime;
	}
	
	public int getCompletionTime() {
		return completionTime;
	}

}
