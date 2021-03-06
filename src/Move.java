import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Move extends Thread {

	DifferentialPilot controllUnit;

	boolean greifarmOffen;
	boolean maincond = true;

	public Move(DifferentialPilot cont) {
		controllUnit = cont;
	}

	public void run() {
		while(true){
			while (maincond) {
			if (MainClass.emergencyBreak) {
				controllUnit.quickStop();
				System.exit(1);
			}

			if (MainClass.objectgefunden) {
				controllUnit.travel(MainClass.distanceOnUltra / MainClass.diameterOfWheel, false);

				// todo: ausrichten
				schliesseGreifarm();
			} else if (MainClass.linegefunden) {
				controllUnit.travel(-5);
				controllUnit.rotate(120);
			} else {
				controllUnit.travel(15);
				controllUnit.rotate(30);
				controllUnit.rotate(-60);
				controllUnit.rotate(30);
			}
		}
	}
	}

	public void schliesseGreifarm() {
		if (greifarmOffen) {
			Motor.C.rotate(-90);
			greifarmOffen = false;
		}
	}

	public void offneGreifarm() {
		if (!greifarmOffen) {
			Motor.C.rotate(90);
			greifarmOffen = true;
		}
	}
	
	public synchronized void pause(){
		try {
			wait();
			System.out.println("paused");
		} catch (InterruptedException e) {
			System.out.println("interruptedexception");
			e.printStackTrace();
		}
	}
}
