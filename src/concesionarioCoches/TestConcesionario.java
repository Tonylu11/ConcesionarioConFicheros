package concesionarioCoches;

import utiles.Fichero;

//P: indica las etiquetas html utilizadas para la documentación de la clase
//P: Indica la utilidad de la etiqueta  -noqualifier  all a la hora de generar la documentación JavaDoc. Entrega un pantallazo indicando cómo se detalla en Eclipse

import utiles.Menu;
import utiles.Teclado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import concesionarioCoches.Color;
import concesionarioCoches.Modelo;

/**
 * Queremos modelar un concesionario de coches en Java. Nos limitaremos a las
 * siguientes opciones:
 * <ol>
 * <li>Alta de un coche (se pedirá matricula, color y modelo),</li>
 * <li>Baja de un coche (por matrícula)</li>
 * <li>Mostrar un coche (por matrícula)</li>
 * <li>Mostrar concesionario (todos los coches del concesionario)</li>
 * <li>Contar el número de coches en el concesionario</li>
 * <li>Mostrar coches de un color</li>
 * </ol>
 * <p>
 * Lógicamente, no podrá añadirse un coche inválido o ya contenido (No pueden
 * existir dos coches con la misma matrícula en el concesionario) Por cada p que
 * se dé de alta, han de conocerse todas sus características. Ninguna de las
 * características del coche puede ser por defecto.
 * 
 * @author Antonio Luque Bravo
 * @version 1.0
 * 
 */
public class TestConcesionario extends Concesionario {
	/**
	 * Menú principal de la aplicación
	 */
	static Menu menu = new Menu("Concesionario de coches",
			new String[] { "Alta Coche", "Baja Coche", "Mostrar Coche", "Mostrar concesionario",
					"Contar coches del concesionario", "Mostrar coches de un color", "Fichero..", "Salir" });
	/**
	 * Menú para seleccionar los colores del coche
	 */
	private static Menu menuColores = new Menu("Colores de los coches", Color.AZUL.generarOpcionesMenu());

	/**
	 * Menú para seleccionar los modelos del coche
	 */
	private static Menu menuModelos = new Menu("Modelos de los coches", Modelo.CORDOBA.generarOpcionesMenu());
	private static Menu menuFicheros = new Menu("Ficheros.",
			new String[] { "Nuevo", "Abrir..", "Guardar", "Guardar Como.." });
	/**
	 * Donde se gestionará el concesionario
	 */
	static Concesionario concesionario = new Concesionario();
	static File archivo;
	private static boolean modificado;
	private static File archivoSeleccionado;

	public static File getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public static void setArchivoSeleccionado(File archivoSeleccionado) {
		TestConcesionario.archivoSeleccionado = archivoSeleccionado;
	}

	public static boolean isModificado() {
		return modificado;
	}

	public static void setModificado(boolean modif) {
		modificado = modif;
	}

	/**
	 * Arranque del programa
	 * 
	 * @param args
	 *            No se utilizan
	 */
	public static void main(String[] args) {
		int opcion;
		do {
			opcion = menu.gestionar();
			switch (opcion) {
			case 1:// "Añadir Coche
				annadirCoche();
				break;
			case 2:// Eliminar Coche
				eliminarCoche();
				break;
			case 3:// Obtener Coche
				getCoche();
				break;
			case 4:// Mostrar lista
				System.out.println(concesionario);
				break;
			case 5:// Contar coches
				System.out.println("Número de coches en el concesionario: " + concesionario.size());
				break;
			case 6:// Mostrar coches de un color
				System.out.println(concesionario.getCochesColor(pedirColor()));
				break;
			case 7:// Ficheros..
				gestionarFicheros();
				break;
			case 8:// Salir
				System.out.println("Aaaaaaaaaaaaaaaaaaaaadios");
				return;
			}
		} while (opcion != 8);
	}

	/**
	 * Muestra por consola el coche solicitando su la matrícula
	 */
	private static void getCoche() {
		try {
			System.out.println(concesionario.get(Teclado.leerCadena("Introduce la matrícula")));
		} catch (MatriculaNoValidaException | CocheNoExisteException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Elimina un coche solicitando su la matrícula
	 */
	private static void eliminarCoche() {
		try {
			if (concesionario.eliminar(Teclado.leerCadena("Introduce la matrícula"))) {
				System.out.println("Coche eliminado");
				setModificado(true);
			} else
				System.out.println(
						"El coche no se ha podido eliminar. No existe un coche con esa matrícula en el concesionario");
		} catch (MatriculaNoValidaException e) {
			System.out.println(e.getMessage() + "No se ha podido eliminar el coche en el concesionario");
		}
	}

	/**
	 * Añade un coche solicitando sus datos por consola
	 */
	private static void annadirCoche() {
		try {
			concesionario.annadir(Teclado.leerCadena("Introduce la matrícula"), pedirColor(), pedirModelo());
			System.out.println("Coche añadido con éxito");
			setModificado(true);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "No se ha podido añadir el coche en el concesionario");
		}
	}

	/**
	 * Solicita al usuario un modelo de coche Según el enunciado del examen:
	 * 
	 * <pre>
	 * Para solicitar el Modelo al dar de alta al coche se implementará un método
	 * pedirModelo que mediante la gestión de un menú, devolverá el modelo indicado
	 * </pre>
	 * 
	 * @return modelo introducido por el usuario. null si el usuario no
	 *         introduce ninguno válido
	 */
	private static Modelo pedirModelo() {
		int opcion = menuModelos.gestionar();
		Modelo[] arrModelos = Modelo.CORDOBA.getValues();
		if (opcion == arrModelos.length + 1)
			return null;
		return arrModelos[opcion - 1];
	}

	/**
	 * Solicita al usuario un color de coche. Según el enunciado del examen:
	 * 
	 * <pre>
	 * Para solicitar el color al dar de alta al coche se implementará un método
	 * pedirColor que mediante la gestión de un menú, devolverá el color indicado
	 * </pre>
	 * 
	 * @return color introducido por el usuario. null si el usuario no introduce
	 *         ninguno válido
	 */
	private static Color pedirColor() {
		int opcion = menuColores.gestionar();
		Color[] arrColores = Color.AZUL.getValues();
		if (opcion == arrColores.length + 1)
			return null;
		return arrColores[opcion - 1];
	}

	/**
	 * Gestiona el manejo de ficheros.
	 */
	private static void gestionarFicheros() {
		switch (menuFicheros.gestionar()) {
		case 1:
			nuevo();
			break;
		case 2:
			abrir();
			break;
		case 3:
			guardar();
			break;
		case 4:
			guardarComo();
			break;
		case 5:
			break;
		}
	}

	/**
	 * Crea un nuevo concesionario, preguntando si existe alguno modificado.
	 */
	private static void nuevo() {
		if (isModificado())
			try {
				guardarSiConfirmacion();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		concesionario = new Concesionario();
		setArchivoSeleccionado(null);
		setModificado(false);
	}

	/**
	 * Abre un concesionario mediante un fichero almacenado en nuestro sistema
	 * de archivos, pidiendo al usuario su nombre y a&ntilde;adiendole la
	 * extensi&oacute;n.
	 */
	private static void abrir() {
		guardarSiModificado();
		try {
			File fichero = new File(Teclado.leerCadena("Dame el nombre del archivo: "));
			fichero = Fichero.annadirExtension(fichero);
			concesionario = (Concesionario) Fichero.abrir(fichero);
		} catch (ClassNotFoundException e) {
			System.out.println("Fichero con información distinta.");
		} catch (IOException e) {
			System.out.println("No se puede abrir el fichero.");
		}
	}

	/**
	 * Guarda el concesionario en un archivo, de no existir ningun archivo, se
	 * invocar&aacute; a guardarComo().
	 */
	private static void guardar() {
		if (getArchivoSeleccionado() == null)
			guardarComo();
		else {
			try {
				Fichero.guardar(concesionario, getArchivoSeleccionado());
				setModificado(false);
			} catch (IOException e) {
				System.err.println("No se ha podido guardar.");
			}
		}
	}

	/**
	 * Guarda el concesionario en un archivo pidiendo su nombre y
	 * a&ntilde;adiendo la extensi&oacute;n del mismo, tambien comprueba si
	 * existe algun fichero con ese nombre.
	 */
	private static void guardarComo() {
		try {
			File file = new File(Teclado.leerCadena("Introduce el nombre del fichero a guardar: "));
			file = Fichero.annadirExtension(file);
			deseaSobreescribir(file);
			Fichero.guardar(concesionario, file);
			setModificado(false);
			setArchivoSeleccionado(file);
		} catch (IOException e) {
			System.out.println("El sistema no puede guardar el fichero.");
		}
	}

	/**
	 * Le pregunta al usuario si desea sobreescribir el archivo que ya existe
	 * con ese nombre.
	 * 
	 * @param file
	 *            archivo a comprobar su existencia.
	 */
	private static void deseaSobreescribir(File file) {
		if (Fichero.confirmarExistencia(file)) {
			char respuesta = Teclado.leerCadena("¿Desea guardar el archivo?(s/n): ").toLowerCase().charAt(0);
			if (respuesta == 'n') {
				return;
			}
		}
	}

	/**
	 * Pregunta al usuario si desea guardar el archivo modificado al intentar
	 * crear un concesionario nuevo con nuevo().
	 * 
	 * @return devuelve true si se desea guardar, false de lo contrario.
	 * @throws FileNotFoundException
	 *             Cuando el archivo no existe.
	 * @throws IOException
	 *             Cuando hay un problema de Entrada/Salida.
	 */
	private static boolean guardarSiConfirmacion() throws FileNotFoundException, IOException {
		if (isModificado()) {
			char respuesta = Teclado.leerCadena("¿Desea guardar el archivo?(s/n): ").toLowerCase().charAt(0);
			if (respuesta == 's') {
				guardar();
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Comprueba si el archivo ha sido modificado, de ser as&iacute; se
	 * llamar&aacute; a guardarSiConfirmacion() para preguntarle al usuario si
	 * desea guardarlo o no.
	 * 
	 * @return Devuelve false si el archivo no esta modificado.
	 */
	private static boolean guardarSiModificado() {
		if (isModificado()) {
			try {
				guardarSiConfirmacion();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return false;
	}
}
