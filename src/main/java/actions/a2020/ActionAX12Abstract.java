package actions.a2020;

import java.util.ArrayList;

import actions.ActionExecutor;
import api.ax12.AX12;
import api.ax12.AX12Exception;
import api.ax12.AX12Link;
import api.ax12.AX12LinkException;
import api.ax12.value.AX12Position;

/**
 * 
 * @author gryttix
 *
 */
public abstract class ActionAX12Abstract implements ActionExecutor {
    
    // Utilis� pour la lecture des r�ponses des actions
    protected ArrayList<Byte> lecture;
    
    // Une seule instance de l'actions : on change son adresse pour chaque commande
    private AX12 ax12;
    
    protected boolean fini = false;
    
    protected enum AX12_NAME {
    	GOBELET1(10),
    	GOBELET2(3),
    	GOBELET3(2),
    	GOBELET4(1),
    	GOBELET5(9),
    	RAIL(5),
    	BRASGAUCHE(4),
    	BRASDROIT(7);
    	
    	
    	public final int adresse;
    	private AX12_NAME(int adresse) {
    		this.adresse = adresse;
    	}
    }
    
    // Les diverses actions possibles par AX12
	protected enum ACTION_AX12 {
		// Attrapage des gobelets
		ATTRAPER_GOBELET1(AX12_NAME.GOBELET1, 119.41),
		ATTRAPER_GOBELET2(AX12_NAME.GOBELET2, 186.0),
		ATTRAPER_GOBELET3(AX12_NAME.GOBELET3, 180.6),
		ATTRAPER_GOBELET4(AX12_NAME.GOBELET4, 127.5),
		ATTRAPER_GOBELET5(AX12_NAME.GOBELET5, 182.0),
		
		// D�pose des gobelets
		RELACHER_GOBELET1(AX12_NAME.GOBELET1, 270),
		RELACHER_GOBELET2(AX12_NAME.GOBELET2, 25.8),
		RELACHER_GOBELET3(AX12_NAME.GOBELET3, 67.7),
		RELACHER_GOBELET4(AX12_NAME.GOBELET4, 279.0),
		RELACHER_GOBELET5(AX12_NAME.GOBELET5, 30.6),
		
		// Gestion du rail
		RAIL_BAISSER_MAX(AX12_NAME.RAIL, 291.9),
		RAIL_BAISSER_POUR_RECOLTER(AX12_NAME.RAIL, 280.0),
		RAIL_BAISSER_POUR_DEPOSE(AX12_NAME.RAIL, 291.9),
		RAIL_LEVER_POUR_RECOLTER(AX12_NAME.RAIL, 27.4),
		RAIL_LEVER_MAX(AX12_NAME.RAIL, 29.0),
		
		// Gestion des bras
		BRAS_GAUCHE_RANGER(AX12_NAME.BRASGAUCHE, 60.0),
		BRAS_GAUCHE_BAISSER(AX12_NAME.BRASGAUCHE, 150.0),
		BRAS_DROIT_RANGER(AX12_NAME.BRASDROIT, 150.0),
		BRAS_DROIT_BAISSER(AX12_NAME.BRASDROIT, 60.0);
		
		public final AX12_NAME ax12;
		public final double angle;
		ACTION_AX12(AX12_NAME ax12, double angle) {
			this.ax12 = ax12;
			this.angle = angle;
		}
	}

    public ActionExecutor init(AX12Link serialAX12) {
        ax12 = new AX12(1, serialAX12);
        return this;
    }
    
    @Override
    public void execute() {
    	// Ptit hack pour mettre une elasticit� sur tous les actions
    	try {
    		ax12.setAddress(254);
			ax12.setCwComplianceSlope(128);
			ax12.setCcwComplianceSlope(128);
		} catch (AX12LinkException e) {
			e.printStackTrace();
		} catch (AX12Exception e) {
			e.printStackTrace();
		}
		
		fini = false;
    	this.childExecution();
    	fini = true;
    }
    
    @Override
    public boolean finished() {
        return fini;
    }
	
	/**
	 * Applique l'�tat demand�
	 * Cette fonction s'appelle go parce que do est d�j� pris :'(
	 * @param et
	 */
	protected void go(ACTION_AX12 et) {
		if (ax12 == null) {
			return;
		}
		
		int essaisRestants = 5;
		
		ax12.setAddress(et.ax12.adresse);
		
		while(essaisRestants > 0) {
			try {
				ax12.setServoPosition(AX12Position.buildFromDegrees(et.angle));
				essaisRestants = 0;
			} catch (AX12LinkException e) {
				e.printStackTrace();
				essaisRestants--;
				System.out.println("essais restant : "+essaisRestants);
			} catch (AX12Exception e) {
				e.printStackTrace();
				essaisRestants--;
				System.out.println("essais restant : "+essaisRestants);
			}	
		}
	}
	
	/**
	 * Attend une certaine dur�e en ms
	 * @param duree tps � attendre en ms
	 */
	protected void attend(long duree) {
		try {
			Thread.sleep(duree);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Attend que tous les actions de la liste aient fini de bouger
	 * Attention aux blagues avec le mode rotation continue ;)
	 * @param ax12
	 */
	protected void attendreImmobilisation(AX12_NAME... liste) {
		boolean bouge = false;
		int maxExceptionTolerance = 10;
		
		do {
			if (bouge) {
				// Pour �viter de spammer la liaison s�rie, on est pas � 50ms pr�s
				attend(50);
			}
			bouge = false;
			for (AX12_NAME ax : liste) {
				ax12.setAddress(ax.adresse);
				try {
					if (ax12.isMoving()) {
						bouge = true;
						break;
					}
				} catch (AX12LinkException e) {
					e.printStackTrace();
					if (maxExceptionTolerance-- < 0) {
						bouge = true;
					}
				} catch (AX12Exception e) {
					e.printStackTrace();
					if (maxExceptionTolerance-- < 0) {
						bouge = true;
					}
				}
			}
		} while (bouge);
	}
    
	/**
	 * Les commandes utiles des classes enfant
	 */
    protected abstract void childExecution();

	@Override
	public void resetActionState() {
		this.fini = false;
	}

}
