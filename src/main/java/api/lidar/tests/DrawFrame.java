package api.lidar.tests;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import api.lidar.LidarException;
import api.lidar.RpLidarA2;
import api.lidar.RpLidarScanHandlerInterface;

public class DrawFrame extends JFrame implements RpLidarScanHandlerInterface {

	protected JButton btnStartStop;
	protected JButton btnReset;
	
	protected RpLidarA2 lidar;
	protected JPanel panel;
	protected Graphics graphics;
	
	protected int retention = 1000;
	protected int[] posX;
	protected int[] posY;
	protected int offset;
	
	public DrawFrame(RpLidarA2 lidar) {
		super("RpLidarA2");
		this.lidar = lidar;
		
		this.retention = 1000;
		this.offset = 0;
		this.posX = new int[this.retention];
		this.posY = new int[this.retention];
		
		this.initIG();
	}
	
	protected void initIG() {
		this.setSize(new Dimension(640, 640));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		btnStartStop = new JButton("Start");
		btnStartStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (DrawFrame.this.lidar.scanIsRunning()) {
					try {
						DrawFrame.this.lidar.stopScan();
						DrawFrame.this.btnStartStop.setText("Start");
					} catch (LidarException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						DrawFrame.this.lidar.startScan(DrawFrame.this);
						DrawFrame.this.btnStartStop.setText("Stop");
					} catch (LidarException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DrawFrame.this.lidar.reset();
				} catch (LidarException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		this.setResizable(false);
		
		panel = new JPanel();
		graphics = panel.getGraphics();
		panel.add(btnStartStop);
		panel.add(btnReset);
		this.add(panel);
	}

	@Override
	public void handleLidarScan(int quality, boolean nouvelleRotation, double angle, double distance) {
		int centerX = 320;
		int centerY = 340;
		double maxDistance = 10.;
		
		angle = (angle-90.)*Math.PI/180.;
		int x = centerX + (int) (((Math.cos(angle) * distance) / maxDistance));
		int y = centerY + (int) (((Math.sin(angle) * distance) / maxDistance));
		
		panel.getGraphics().setColor(Color.BLACK);
		panel.getGraphics().drawRect(x, y, 1, 1);
		panel.getGraphics().setColor(Color.RED);
		panel.getGraphics().drawRect(centerX-3, centerY-3, 7, 7);
		panel.getGraphics().fillRect(centerX-3, centerY-3, 7, 7);
	}
	
	@Override
	public void repaint() {
		super.repaint();
		
	}

	@Override
	public void scanStopped(SCAN_STOP_REASON reason) {
		System.out.println(reason);
		btnStartStop.setText("Start");
	}

	@Override
	public void lidarRecoverableErrorOccured(RECOVERRABLE_ERROR error) {
		System.out.println("Recoverable error : "+error);
	}
}
