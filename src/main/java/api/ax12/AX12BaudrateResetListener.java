package api.ax12;

public interface AX12BaudrateResetListener {

	/**
	 * Indique la progression du reset du baudrate de l'actions
	 * @param currentStep l'�tape courante
	 * @param count le nombre total d'�tapes
	 * @return s'il faut arr�ter la progression ou pas
	 */
	public boolean ax12BaudRateResetProgression(int currentStep, int count);
	
}
