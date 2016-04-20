package concesionarioCoches;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * No pueden existir dos coches con la misma matrícula en el almacén del concesinario
 * no puede añadirse un coche al concecionario con alguno de sus atributos inválidos. Han de conocerse todas sus características 
 * Ninguno de los valores puede ser por defecto
 */
/**
 * Representa un concesionario de coches.
 * 
 * Lógicamente, no podrá añadirse un coche inválido almacén del concesinario)
 * 
 * Han de conocerse todas sus características Ninguno de los valores puede ser
 * por defecto
 * 
 * @author Antonio Luque Bravo
 * @version 1.0
 * 
 */
public class Concesionario implements Serializable {
	/**
	 * Almacén de los coches del concesionario
	 */
	private ArrayList<Coche> almacen = new ArrayList<Coche>();
	/**
	 * Nombre del concesionario
	 */
	private final String nombre = "IES Gran Capitán";

	// P:Por qué no se necesita que annadir devuelva boolean??????
	// P:Por qué no se especifican todas las excepciones de forma
	// explícita??????
	/**
	 * Añade un coche al concesinario
	 * 
	 * @param matricula
	 *            Matrícula del coche a añadir
	 * @param color
	 *            Color del coche a añadir
	 * @param modelo
	 *            Modelo del coche a añadir
	 * @throws Exception
	 *             Si no se ha podido añadir el coche al concesionario, porque
	 *             ya hay otro con la misma matrícula o porque faltan datos
	 */
	void annadir(String matricula, Color color, Modelo modelo) throws Exception {
		// Coche coche = Coche.instanciarCoche(matricula, color, modelo);

		// if (coche == null || almacen.contains(coche))
		// return false;
		// return almacen.add(coche);
		Coche coche = new Coche(matricula, color, modelo);
		if (!almacen.contains(coche))
			almacen.add(coche);
		else
			throw new CocheYaExisteException("El coche ya existe en el concesionario. ");
	}

	/**
	 * Elimina un coche del concesinario
	 * 
	 * @param matricula
	 *            Matrícula del coche a eliminar
	 * @throws MatriculaNoValidaException
	 *             Si la matrícula no es válida en su formato
	 * @return true si se ha eliminado. false en otro caso
	 */
	boolean eliminar(String matricula) throws MatriculaNoValidaException {
		return almacen.remove(new Coche(matricula));
	}

	/**
	 * Devuelve el número de coches en el almacén del concesionario
	 * 
	 * @return Número de coches en el almacén del concesionario
	 */
	int size() {
		return almacen.size();
	}

	/**
	 * Devuelve el Coche del almacén indicado por la matrícula
	 * 
	 * @param matricula
	 *            Matrícula a buscar
	 * @return Coche contenido en el almacén. null si no existe
	 * @throws MatriculaNoValidaException
	 *             Si la matrícula no es válida
	 */
	Coche get(String matricula) throws MatriculaNoValidaException, CocheNoExisteException {
		// Coche coche = Coche.instanciarCoche(matricula);
		// int index = almacen.indexOf(new Coche(matricula));
		// if (index != -1) {
		// P: qué sucede si el coche no está en el concesionario?

		try {
			return almacen.get(almacen.indexOf(new Coche(matricula)));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new CocheNoExisteException("El coche no está en el concesionario.");
		}

		// }
		// return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Concesionario " + nombre + "[almacen=" + almacen + "]";
	}

	public ArrayList<Coche> getCochesColor(Color color) {
		ArrayList<Coche> arrCochesColor = new ArrayList<Coche>();
		for (Coche coche : almacen) {
			if (coche.getColor() == color)
				arrCochesColor.add(coche);
		}
		return arrCochesColor;
	}

}
