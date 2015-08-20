/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.util.Enumeration;
import javax.swing.AbstractButton;

/**
 *
 * @author bdias
 */
public class Calculadora {
    
    public String getTipoOperacao (Enumeration<AbstractButton> buttons) {
        while(buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if(button.isSelected()) {
                return button.getText();
            }
        }
        
        return "";
    }
    
    public String coverte(String entrada, String tipoOperacao) {
        String retorno = "";
         int decimal;
        switch (tipoOperacao.toLowerCase()){
            case "decimal" :
                retorno = Integer.toBinaryString(Integer.parseInt(entrada)) + "\n"; // bin
                retorno+= "0o" + Integer.toOctalString(Integer.parseInt(entrada)) + "\n"; // oct
                retorno+= "0x"+ Integer.toHexString(Integer.parseInt(entrada)) + "\n"; // hex
                break;
            case "binary" :
                decimal = Integer.parseInt(entrada, 2);
                retorno = String.valueOf(decimal) + "\n"; // dec
                retorno+= "0o" + Integer.toOctalString(decimal) + "\n"; // oct
                retorno+= "0x"+ Integer.toHexString(decimal) + "\n"; // hex
                break;
            case "octal" :
                decimal = Integer.parseInt(entrada, 8);
                retorno = Integer.toBinaryString(decimal) + "\n"; // bin
                retorno+= String.valueOf(decimal) + "\n"; // dec
                retorno+= "0x"+ Integer.toHexString(decimal) + "\n"; // hex
                break;
            case "hex" :
                decimal = Integer.parseInt(entrada, 16);
                retorno = Integer.toBinaryString(decimal) + "\n"; // bin
                retorno+= String.valueOf(decimal) + "\n"; // dec
                retorno+= "0o" + Integer.toOctalString(decimal) + "\n"; // oct
                break;
        }
        
        return retorno;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
