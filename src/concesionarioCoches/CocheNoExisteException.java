package concesionarioCoches;

/**
 * Representa a un coche.
 * <p>
 * Un coche tendrá las siguientes características:
 * <ol>
 * <li>Color. Se limitarán los colores a tres: plata, rojo y azul.</li>
 * 
 * 
 * <li>Modelo. Se limitarán los modelos de coches a siete: Córdoba (marca
 * Seat),Toledo (marca Seat),Ibiza (marca Seat), Serie 1 (marca BMW), Serie 2
 * (marca BMW),Serie 3 (marca BMW) y Serie 5 (marca BMW). Para solicitar el
 * modelo al dar de alta al coche podrá implementarse un método pedirModelo que
 * mediante la gestión de un menú, devolverá el modelo indicado.</li>
 * 
 * 
 * <li>Matrícula (única por coche) El formato de las matrículas será el nuevo:
 * combinación de cuatro números (de 0000 a 9999) y tres letras, comenzando por
 * BBB y terminando por ZZZ, excluyendo vocales, la LL, la Ñ y la Q.</li>
 * 
 * 
 * <ol>
 * <li>Matrículas válidas: 0000-BBB, 0000 BBB, 0000BBB, 1234ZZZ.</li>
 * <li>Matrículas inválidas: 000_BBB, 9999-BBQ, 0000-BÑB, 0000-DEF, 0000 bbb,
 * 0000 AAA</li>
 * </ol>
 * </ol>
 * 
 * @author Antonio Luque Bravo
 * @version 1.0
 * 
 */
public class CocheNoExisteException extends Exception {

	public CocheNoExisteException(String string) {
		super(string);
	}

}
