package concesionarioCoches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import utiles.Fichero;
import utiles.Menu;
import utiles.Teclado;

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
public class Gestion {
	/**
	 * 
	 */
	private static Menu menuFicheros = new Menu("Ficheros.",
			new String[] { "Nuevo", "Abrir..", "Guardar", "Guardar Como..", "Salir." });
	private static boolean modificado;
	private static File archivoSeleccionado;

	public static File getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public static void setArchivoSeleccionado(File archivoSeleccionado) {
		Gestion.archivoSeleccionado = archivoSeleccionado;
	}

	public static boolean isModificado() {
		return modificado;
	}

	public static void setModificado(boolean modif) {
		modificado = modif;
	}

	/**
	 * Gestiona el manejo de ficheros.
	 */
	static void gestionarFicheros() {
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
		TestConcesionario.setConcesionario(new Concesionario());
		setArchivoSeleccionado(null);
		setModificado(false);
	}

	/**
	 * Abre un concesionario mediante un fichero almacenado en nuestro sistema
	 * de archivos, pidiendo al usuario su nombre y a&ntilde;adiendole la
	 * extensi&oacute;n.
	 */
	private static void abrir() {
		try {
			guardarSiConfirmacion();
			File fichero = new File(Teclado.leerCadena("Dame el nombre del archivo: "));
			fichero = Fichero.annadirExtension(fichero);
			TestConcesionario.setConcesionario((Concesionario) Fichero.abrir(fichero));
			setArchivoSeleccionado(fichero);
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
				Fichero.guardar(TestConcesionario.getConcesionario(), getArchivoSeleccionado());
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
			Fichero.guardar(TestConcesionario.getConcesionario(), file);
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
	 * @throws FileNotFoundException
	 *             Cuando el archivo no existe.
	 * @throws IOException
	 *             Cuando hay un problema de Entrada/Salida.
	 */
	private static void guardarSiConfirmacion() throws FileNotFoundException, IOException {
		if (isModificado()) {
			char respuesta = Teclado.leerCadena("¿Desea guardar el archivo?(s/n): ").toLowerCase().charAt(0);
			if (respuesta == 's') {
				guardar();
			}
		}
	}
}
