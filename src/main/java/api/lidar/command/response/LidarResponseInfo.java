package api.lidar.command.response;

import api.lidar.utils.LidarHelper;

public class LidarResponseInfo {

	public int modelId;

	public int firmwareMinor;
	
	public int firmwareMajor;
	
	public int hardwareVersion;
	
	/**
	 * Le n° de série du Lidar
	 * serialNumber[0] : byte de poids le plus fort
	 * serialNumber[serialNumber.length - 1] byte de poids le plus faible
	 */
	public byte[] serialNumber;
	
	/**
	 * Convertit le numéro de série en chaîne hexa sans préfixe "0x"
	 * 
	 * @return
	 */
	public String getSerialNumberAsHexa() {
		String num = "";
		for (byte b : serialNumber) {
			num += LidarHelper.unsignedbyteToHex(b).replace("0x", "");
		}
		return num;
	}
	
	/**
	 * Renvoie la version du firmware sous forme d'une chaîne "versionMajeure.versionMineure"
	 * 
	 * @return
	 */
	public String getFirmwareVersion() {
		return this.firmwareMajor+"."+firmwareMajor;
	}
	
	@Override
	public String toString() {
		return "Model ID : "+this.modelId+"\nFirmware version : "+this.getFirmwareVersion()+"\nHardware version : "+this.hardwareVersion+"\nSerial number : "+this.getSerialNumberAsHexa();
	}
	
}
