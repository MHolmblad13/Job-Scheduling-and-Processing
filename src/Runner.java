import java.io.FileNotFoundException;

public class Runner {
	public static void main(String[] args) throws FileNotFoundException {
		RoundRobin rr = new RoundRobin();
		PriorityScheduling  ps = new PriorityScheduling();
		
		System.out.println("----- Run Round Robin -----");
		rr.runRoundRobin();
		System.out.println("----- Round Robin Complete -----");
		System.out.println("----- Run Priority Scheduling -----");
		ps.runPriorityScheduling();
		System.out.println("----- Priority Scheduling Complete -----");
	}
}
