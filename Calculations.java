import java.text.*;

class Calculations
{	
	double coffee;
	double water;
	String result;
	
	public String waterToCoffee(double water)
	{
		coffee = water / 12.0;
		result = String.format("%.2f", coffee );
		return result;
	}
	
	public String coffeeToWater(double coffee)
	{
		water = coffee * 12.0;
		result = String.format("%.2f", water);
		return result;
	}
}