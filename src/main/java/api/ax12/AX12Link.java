package api.ax12;

public interface AX12Link {

	/**
	 * Envoie une commande et retourne l'éventuelle réponse détectée
	 * @param cmd la séquence de byte de la commande
	 * @param baudRate change le baudrate le temps de la transmition si cette opération est supportée
	 */
	public byte[] sendCommand(byte[] cmd, int baudRate) throws AX12LinkException;
	
	/**
	 * Renvoie le baudRate utilisé sur la liaison actuelle
	 * @return
	 */
	public int getBaudRate();
	
	/**
	 * Règle le baudrate de communication actuel
	 * @param baudRate
	 * @throws AX12LinkException
	 */
	public void setBaudRate(int baudRate) throws AX12LinkException;
	
	/**
	 * Met à un niveau logique haut ou bas la ligne DTR de la liaison série
	 * @param enable true = ligne à LOW, false = ligne à HIGH
	 * @throws AX12LinkException 
	 */
	public void enableDtr(boolean enable) throws AX12LinkException;
	
	/**
	 * Met à un niveau logique haut ou bas la ligne RTS de la liaison série
	 * @param enable true = ligne à LOW, false = ligne à HIGH
	 * @throws AX12LinkException
	 */
	public void enableRts(boolean enable) throws AX12LinkException;
	
	/**
	 * Désactive le couple de tous les ax avant d'activer le rejet de toutes
	 * les autres commandes qui leurs seront adressées
	 */
	public void disableAx12AndShutdownLink();
	
}
