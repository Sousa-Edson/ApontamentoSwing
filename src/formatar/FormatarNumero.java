/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formatar;

/**
 *
 * @author edson
 */
import java.text.DecimalFormat;

public class FormatarNumero {

    public static String returnaMilhar(int valorInteiro) {

        // Cria um objeto DecimalFormat para formatar o número com separador de milhares
        DecimalFormat formato = new DecimalFormat("#,###");

        // Formata o número
        return formato.format(valorInteiro);

    }
}
