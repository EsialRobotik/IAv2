package api.lidar.tests;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import api.lidar.link.RpLidarLink;
import api.lidar.utils.PaquetSniffer;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import api.lidar.RpLidarA2;
import api.lidar.RpLidarScanHandlerInterface;

public class RpLidarMain implements RpLidarScanHandlerInterface {
	
	protected long mesuresRecues;
	protected long lastOutputTime;
	protected long scanSTartTime;
	
	public static void main(String[] args) {
		RpLidarMain main = new RpLidarMain();
//		main.snifSerial();
		main.mainProgramm();
		//main.graphicalInterface();
	}
	
	private List<SerialPort> getAvailableSerialPortList(){
		@SuppressWarnings(	"unchecked")
		Enumeration<CommPortIdentifier> p = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier cpi;
		CommPort cp;
		List<SerialPort> liste = new ArrayList<>();
		
		while(p.hasMoreElements()){
			try {
				cpi = p.nextElement();
				if(cpi != null && !cpi.isCurrentlyOwned()){
					cp = cpi.open(RpLidarMain.class.getName(), 500);
					if(cp instanceof SerialPort){
						liste.add((SerialPort)cp);
					}
				}
			} catch (PortInUseException e) {
			}
		}
		return liste;
	}

	@Override
	public void handleLidarScan(int quality, boolean nouvelleRotation, double angle, double distance) {
		this.mesuresRecues++;
		if (this.mesuresRecues % 5000 == 0) {
			double speed =  5000000. / ((double)(System.currentTimeMillis() - this.lastOutputTime));
			long time = (System.currentTimeMillis() - this.scanSTartTime) / 1000;
			System.out.println("Tps exécution "+time+" secondes ; "+this.mesuresRecues+" mesures reçues (env "+Math.round(speed)+" / seconde)");
			this.lastOutputTime = System.currentTimeMillis();
		}
	}

	@Override
	public void scanStopped(SCAN_STOP_REASON reason) {
		System.out.println("Scan stoppé :"+reason);
	}

	@Override
	public void lidarRecoverableErrorOccured(RECOVERRABLE_ERROR error) {
		System.out.println("Recoverrable error : "+error);
	}
	
	public void snifSerial() {
		List<SerialPort> liste = this.getAvailableSerialPortList();
		if (liste.isEmpty()) {
			System.out.println("Aucun port série.");
			System.exit(0);
		}
		
		try {
			RpLidarLink link = new RpLidarLink(liste.get(0));
			System.out.println("Utilisation du port "+liste.get(0).getName());
			PaquetSniffer sniffer = new PaquetSniffer(link);
			sniffer.sniffRequests();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mainProgramm() {
		List<SerialPort> liste = this.getAvailableSerialPortList();
		if (liste.isEmpty()) {
			System.out.println("Aucun port série.");
			System.exit(0);
		}
		try {
			System.out.println("Port utilisé : "+liste.get(0).getName());
			RpLidarLink rplink = new RpLidarLink(liste.get(0));
			RpLidarA2 lidar = new RpLidarA2(rplink);
			
			rplink.enableRotation(false);
			lidar.reset();
			Thread.sleep(1000);
			this.mesuresRecues = 0;
			this.lastOutputTime = System.currentTimeMillis();
			this.scanSTartTime = this.lastOutputTime;
			lidar.startScan(this);
			Thread.sleep(100);
			lidar.waitScanToTerminate();
			lidar.reset();			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void graphicalInterface() {
		List<SerialPort> liste = this.getAvailableSerialPortList();
		if (liste.isEmpty()) {
			System.out.println("Aucun port série.");
			System.exit(0);
		}
		
		try {
			System.out.println("Utilisation du port "+liste.get(0).getName());
			RpLidarLink link = new RpLidarLink(liste.get(0));
			DrawFrame df = new DrawFrame(new RpLidarA2(link));	
			df.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
