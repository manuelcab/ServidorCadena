package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * La clase `Cliente` representa un cliente que se conecta al servidor para solicitar números aleatorios y la suma.
 */
public class Cliente {

    /**
     * Método principal que inicia el cliente.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Configuración de conexión al servidor.
        String HOST = "localhost";
        int PUERTO = 8080;

        try {
            // Establecer conexión con el servidor.
            Socket cliente = new Socket(HOST, PUERTO);

            // Crear flujos de entrada/salida de objetos para comunicarse con el servidor.
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

            // Enviar solicitud al servidor.
            salida.writeObject("Solicitud de números aleatorios y su suma");

            // Recibir la respuesta del servidor: array de números aleatorios y su suma.
            int[] numerosAleatorios = (int[]) entrada.readObject();
            int suma = entrada.readInt();

            // Mostrar la respuesta del servidor.
            System.out.println("Arreglo de números aleatorios:");

            // Imprimir cada número aleatorio en el array.
            for (int numero : numerosAleatorios) {
                System.out.print(numero + " ");
            }

            // Mostrar la suma.
            System.out.println("\nSuma: " + suma);

            // Cerrar la conexión con el servidor.
            cliente.close();

        } catch (IOException | ClassNotFoundException e) {
            // Manejar excepciones en caso de error en la comunicación con el servidor.
            e.printStackTrace();
        }
    }
}
