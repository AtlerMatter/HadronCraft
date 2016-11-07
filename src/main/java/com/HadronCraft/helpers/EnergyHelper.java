package com.HadronCraft.helpers;

public class EnergyHelper {
	public final int lossConstant = 1200; //Constante de perda usada nas equações de perda para dar o resultado pretendido
	
	public double powerLoss(double Voltage, double Resistance){
		return ((lossConstant / Voltage) + (Resistance / lossConstant)) / 100;
	}
}
