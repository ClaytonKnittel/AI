package network.graphics;

import java.awt.Color;

public class ColorMath {
	
	public static Color blend(Color c1, Color c2, float percentC1) {
		return new Color ((int) (c1.getRed() * percentC1 + c2.getRed() * (1 - percentC1)),
				(int) (c1.getGreen() * percentC1 + c2.getGreen() * (1 - percentC1)),
				(int) (c1.getBlue() * percentC1 + c2.getBlue() * (1 - percentC1)));
	}
	
}
