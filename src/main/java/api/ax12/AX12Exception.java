package api.ax12;

import api.ax12.AX12.AX12_Error;

public class AX12Exception extends Exception {

	private static final long serialVersionUID = 1L;
	
	public final AX12_Error[] errors;
	
	public AX12Exception() {
		this(null, new AX12_Error[0]);
	}
	
	public AX12Exception(String msg, AX12_Error... errors) {
		super(msg);
		this.errors =  errors;
	}
	
	public AX12Exception(AX12_Error... errors) {
		super();
		this.errors =  errors;
	}
	
	public AX12_Error[] getErrors() {
		return this.errors;
	}
	
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		if (super.getMessage() != null) {
			sb.append(super.getMessage());
		}
		boolean first = true;
		for (AX12_Error err : this.errors) {
			if (sb.length() > 0) {
				if (first) {
					sb.append(' ');
					first = false;
				} else {
					sb.append(", ");
				}
			}
			sb.append(err.toString());
		}
		return sb.toString();
	}
	
	/**
	 * Vérifie si l'exception contient un motif d'erreur spécifique
	 * @param err
	 * @return
	 */
	public boolean contains(AX12_Error err) {
		for (AX12_Error r : this.errors) {
			if (r == err) {
				return true;
			}
		}
		return false;
	}
	
}
