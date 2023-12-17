package esialrobotik.ia.api.ax12;

public interface AX12BaudrateResetListener {

	/**
	 * Indique la progression du reset du baudrate de l'esialrobotik.ia.actions
	 * @param currentStep l'étape courante
	 * @param count le nombre total d'étapes
	 * @return s'il faut arrêter la progression ou pas
	 */
	public boolean ax12BaudRateResetProgression(int currentStep, int count);
	
}
