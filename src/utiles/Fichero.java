package utiles;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Crea otra versión de Concesionario de coches, ahora almacenará el
 * concesionario completo en el sistema de ficheros, del que se podrá recuperar
 * en cualquier momento. Utiliza el código del gitbub
 * https://github.com/lmagarin/Concesionario-de-coches/issues/1 Para ello: Añade
 * una opción Ficheros al menú principal Crea un menú para gestionar los
 * ficheros. Tendrá las opciones típicas: nuevo, abrir, guardar, guardar como...
 * El concesionario podrá guardarse en un fichero (guardar y guardar como...) El
 * concesionario podrá leerse de un fichero (abrir) Podrá crearse un
 * concesionario nuevo (nuevo) En caso de que se pueda perder información del
 * concesionario, se le preguntará al usuario (nuevo, abrir, guardar como...) Se
 * le añadirá la extensión ".obj". Deberás utilizar la clase File, que es una
 * representación abstracta de los nombres de los ficheros y directorios. Podrás
 * usar los métodos: File file = new File(String pathname) file.getPath();
 * file.exists(); PD: Utiliza el ARM para evitar el tener que utilizar el
 * close() de los flujos PPD: Procura colocar la creación del flujo en la misma
 * línea, evitando el uso de variables innecesarias
 * 
 * @author Antonio Luque Bravo
 * @version 1.0
 *
 */
public class Fichero {
	public static Object abrir(File archivo) throws ClassNotFoundException, IOException {
		archivo = annadirExtension(archivo);
		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(archivo)))) {
			return (Object) ois.readObject();
		}
	}

	public static void guardar(Object obj, File archivo) throws FileNotFoundException, IOException {
		archivo = annadirExtension(archivo);
		try (ObjectOutputStream salida = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(archivo, false)))) {
			salida.writeObject(obj);
		}
	}

	public static File annadirExtension(File archivo) {
		String extension = archivo.getPath();
		if (!extension.endsWith(".obj"))
			return new File(archivo + ".obj");
		return archivo;
	}

	public static boolean confirmarExistencia(File archivo) {
		archivo = annadirExtension(archivo);
		return archivo.exists();
	}
}
