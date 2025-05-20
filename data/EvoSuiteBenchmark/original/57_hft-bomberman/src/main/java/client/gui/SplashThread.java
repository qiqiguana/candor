package client.gui;

public class SplashThread extends Thread {

	SplashFrame splash;
	private boolean run = true;

	public void run() {
		
		splash = new SplashFrame();
		splash.setVisible(true);
		try {
			while (run) {
				synchronized (this) {
					wait();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		splash.setVisible(false);
		splash = null;
		StartFrame.getInstance().setVisible(true);
	}

	public void setRun(boolean run) {
		this.run = run;
	}

}
