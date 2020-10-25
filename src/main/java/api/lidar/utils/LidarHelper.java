package api.lidar.utils;

public class LidarHelper {

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	/**
	 * Affiche la valeur de b en exa
	 * 
	 * @param b
	 * @return
	 */
	public static String unsignedByteIntToHex(int b) {
		return "0x" + HEX_ARRAY[b >> 4] + HEX_ARRAY[b & 0x0F];
	}
	
	public static String unsignedBytesToHex(byte... bytes) {
		String s = "";
		for (byte b : bytes) {
			s += unsignedbyteToHex(b) + " ";
		}
		return s;
	}
	
	/**
	 * Affiche la valeur de b en hexa
	 * La valeur est affichée en considérant que le byte est codé non signé
	 * 
	 * @param b
	 * @return
	 */
	public static String unsignedbyteToHex(byte b) {
		return LidarHelper.unsignedByteIntToHex(unsignedByteToInt(b));
	}
	
	public static int unsignedByteToInt(byte b) {
		if (b>=0) {
			return (int) b;
		} else {
			return (int) (b+256);
		}
	}
	
	public static int intFromBytes(byte lowByte, byte highByte) {
		return (unsignedByteToInt(highByte) << 8) | unsignedByteToInt(lowByte);
	}
	
}
