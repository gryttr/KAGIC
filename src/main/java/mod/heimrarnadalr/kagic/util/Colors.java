package mod.heimrarnadalr.kagic.util;

import java.util.ArrayList;
import java.util.Random;

public class Colors {
	private static Random rand = new Random();
	
	public static int triLerp(int begin, int middle, int end) {
		int r = 0;
		int g = 0;
		int b = 0;
		float u = rand.nextFloat();
		
		if (rand.nextBoolean()) {
	        int b_r = (begin & 16711680) >> 16;
	        int b_g = (begin & 65280) >> 8;
	        int b_b = (begin & 255) >> 0;
	        int m_r = (middle & 16711680) >> 16;
	        int m_g = (middle & 65280) >> 8;
	        int m_b = (middle & 255) >> 0;

	        r = (int) (u * b_r + (1f - u) * m_r); 
			g = (int) (u * b_g + (1f - u) * m_g); 
			b = (int) (u * b_b + (1f - u) * m_b); 
		} else {
	        int m_r = (middle & 16711680) >> 16;
	        int m_g = (middle & 65280) >> 8;
	        int m_b = (middle & 255) >> 0;
	        int e_r = (end & 16711680) >> 16;
	        int e_g = (end & 65280) >> 8;
	        int e_b = (end & 255) >> 0;

	        r = (int) (u * m_r + (1f - u) * e_r); 
			g = (int) (u * m_g + (1f - u) * e_g); 
			b = (int) (u * m_b + (1f - u) * e_b); 
		}
		return (r << 16) + (g << 8) + b;
	}
	
	public static int arbiLerp(ArrayList<Integer> colors) {
		if (colors.size() == 0) {
			return 0;
		}
		if (colors.size() == 1) {
			return colors.get(0);
		}

		int r = 0;
		int g = 0;
		int b = 0;
		float u = rand.nextFloat();
		
		int bound = rand.nextInt(colors.size() - 1);
		
        int b_r = (colors.get(bound) & 16711680) >> 16;
        int b_g = (colors.get(bound) & 65280) >> 8;
        int b_b = (colors.get(bound) & 255) >> 0;
        int e_r = (colors.get(bound + 1) & 16711680) >> 16;
        int e_g = (colors.get(bound + 1) & 65280) >> 8;
        int e_b = (colors.get(bound + 1) & 255) >> 0;

        r = (int) (u * b_r + (1f - u) * e_r); 
		g = (int) (u * b_g + (1f - u) * e_g); 
		b = (int) (u * b_b + (1f - u) * e_b); 

		return (r << 16) + (g << 8) + b;}
}
