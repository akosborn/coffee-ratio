import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.Document;
import java.text.*;
import javax.swing.text.*;

public class CoffeeGui
{
	JFrame frame;
	JPanel mainPanel, waterPanel, coffeePanel;
	GridBagConstraints c = new GridBagConstraints();
	JLabel waterLabel, coffeeLabel;
	JTextField waterTextField, coffeeTextField;
	Calculations calc = new Calculations();
	NumberFormat formatter = new DecimalFormat("###.##");
	NumberFormat numberFormat = NumberFormat.getNumberInstance();
	DocumentListener waterTFListener = new WaterTextFieldListener();
	DocumentListener coffeeTFListener = new CoffeeTextFieldListener();
	
	public void createAndDisplayGui()
	{
		frame = new JFrame("Sir Coffee");
		frame.setSize(800, 500);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		frame.getContentPane().add(mainPanel);
		
		waterPanel = new JPanel();
		waterPanel.setLayout(new BoxLayout(waterPanel, BoxLayout.Y_AXIS));
		waterLabel = new JLabel("Water (in mL)");
		waterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		waterPanel.add(waterLabel);
		
		waterTextField = new JTextField();
		waterTextField.setColumns(10);
		waterTextField.getDocument().addDocumentListener(waterTFListener);
		waterPanel.add(waterTextField);
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,70); // sets padding between coffePanel
		mainPanel.add(waterPanel, c);
		
		
		coffeePanel = new JPanel();
		coffeePanel.setLayout(new BoxLayout(coffeePanel, BoxLayout.Y_AXIS));
		coffeeLabel = new JLabel("Coffee (in grams)");
		coffeeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		coffeePanel.add(coffeeLabel);
		
		coffeeTextField = new JTextField();
		coffeeTextField.setColumns(10);
		coffeeTextField.getDocument().addDocumentListener(coffeeTFListener);
		coffeePanel.add(coffeeTextField);
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0,70,0,0); // sets padding between waterPanel
		mainPanel.add(coffeePanel, c);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class WaterTextFieldListener implements DocumentListener
	{
		public void insertUpdate(DocumentEvent e)
		{
			String text = waterTextField.getText();
			
			try
			{
				double d = Double.parseDouble(text);
				coffeeTextField.getDocument().removeDocumentListener(coffeeTFListener);
				coffeeTextField.setText(calc.waterToCoffee(d));
				coffeeTextField.getDocument().addDocumentListener(coffeeTFListener);
			} catch (Exception ex) {ex.printStackTrace();}		
		}
		
		public void removeUpdate(DocumentEvent e)
		{
			String text = waterTextField.getText();
			
			if(text.length() > 0)
			{
				try
				{
					double d = Double.parseDouble(text);
					coffeeTextField.getDocument().removeDocumentListener(coffeeTFListener);
					coffeeTextField.setText(calc.waterToCoffee(d));
					coffeeTextField.getDocument().addDocumentListener(coffeeTFListener);
				} catch (Exception ex) { }	
			}else 
			{
				coffeeTextField.getDocument().removeDocumentListener(coffeeTFListener);
				coffeeTextField.setText("");
				coffeeTextField.getDocument().addDocumentListener(coffeeTFListener);
			}
		}
		
		public void changedUpdate(DocumentEvent e)
		{
			String text = waterTextField.getText();
			
			try
			{
				double d = Double.parseDouble(text);
				coffeeTextField.getDocument().removeDocumentListener(coffeeTFListener);
				coffeeTextField.setText(calc.waterToCoffee(d));
				coffeeTextField.getDocument().addDocumentListener(coffeeTFListener);
			} catch (Exception ex) { }	
		}
	}
	
	class CoffeeTextFieldListener implements DocumentListener
	{
		public void insertUpdate(DocumentEvent e)
		{
			String text = coffeeTextField.getText();
			
			try
			{
				double d = Double.parseDouble(text);
				waterTextField.getDocument().removeDocumentListener(waterTFListener);
				waterTextField.setText(calc.coffeeToWater(d));
				waterTextField.getDocument().addDocumentListener(waterTFListener);
			} catch (Exception ex) { }		
		}
		
		public void removeUpdate(DocumentEvent e)
		{
			String text = coffeeTextField.getText();
			
			if(text.length() > 0)
			{
				try
				{
					double d = Double.parseDouble(text);
					waterTextField.getDocument().removeDocumentListener(waterTFListener);
					waterTextField.setText(calc.coffeeToWater(d));
					waterTextField.getDocument().addDocumentListener(waterTFListener);
				} catch (Exception ex) { }	
			}else
			{
				waterTextField.getDocument().removeDocumentListener(waterTFListener);
				waterTextField.setText("");
				waterTextField.getDocument().addDocumentListener(waterTFListener);
			}
		}
		
		public void changedUpdate(DocumentEvent e)
		{
			String text = coffeeTextField.getText();
			
			System.out.println("CoffeeTextFieldListener changedUpdate triggered");
			
			try
			{
				double d = Double.parseDouble(text);
				waterTextField.getDocument().removeDocumentListener(waterTFListener);
				waterTextField.setText(calc.coffeeToWater(d));
				waterTextField.getDocument().addDocumentListener(waterTFListener);				
			} catch (Exception ex) { }	
		}
	}
}
