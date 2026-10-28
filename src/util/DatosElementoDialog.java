package util;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DatosElementoDialog {
	private String nombre;
	private String extension;

	public String getNombre() {
		return nombre;
	}
	public String getExtension() {
		return extension;
	}

	public DatosElementoDialog(String operacion) {
	      JTextField nombreField = new JTextField(5);
	      JTextField extensionField = new JTextField(5);

	      JPanel myPanel = new JPanel();
	      myPanel.add(new JLabel("Nombre:"));
	      myPanel.add(nombreField);
	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	      myPanel.add(new JLabel("Extensión:"));
	      myPanel.add(extensionField);

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	    		  operacion, JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  nombre= nombreField.getText();
	    	  extension=extensionField.getText();
	      }
	   }

}
