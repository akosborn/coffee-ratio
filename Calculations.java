import java.text.*;

class Calculations
{	
	private double coffee;
	private double water;
	private String result;
	private int specifiedRatio = 12;
	
	public String waterToCoffee(double water)
	{
		coffee = water / specifiedRatio;
		result = String.format("%.2f", coffee );
		return result;
	}
	
	public String coffeeToWater(double coffee)
	{
		water = coffee * specifiedRatio;
		result = String.format("%.2f", water);
		return result;
	}
	
	public void setSpecifiedRatio(int specifiedRatio)
	{
		this.specifiedRatio = specifiedRatio;
	}
	
	
}