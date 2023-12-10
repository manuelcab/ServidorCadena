package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * La clase `Servidor` representa un servidor simple que responde a solicitudes de clientes.
 */
public class Servidor {

    private ServerSocket servidor;

    /**
     * Constructor de la clase Servidor.
     *
     * @param puerto    Puerto en el cual el servidor escuchará conexiones.
     * @param ipAddress Dirección IP en la cual el servidor se enlazará.
     */
    public Servidor(int puerto, String ipAddress) {
        try {
            // Inicializar el servidor en el puerto y la dirección IP especificados.
            servidor = new ServerSocket(puerto, 0, InetAddress.getByName(ipAddress));
            System.out.println("Servidor iniciado en " + ipAddress + " puerto " + puerto);
        } catch (IOException ex) {
            // Manejar excepciones en caso de error al iniciar el servidor.
            ex.printStackTrace();
        }
    }

    /**
     * Procesa la solicitud de un cliente.
     *
     * @param cliente Socket representando la conexión con un cliente.
     */
    public void ContestarMensaje(Socket cliente) {
        try {
            // Establecer flujos de entrada/salida de objetos para comunicarse con el cliente.
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());

            // Leer la solicitud del cliente como una cadena.
            String solicitud = (String) entrada.readObject();
            System.out.println("Solicitud del cliente: " + solicitud);

            // Generar números aleatorios y calcular la suma.
            int[] numerosAleatorios = generarNumerosAleatorios();
            int suma = calcularSuma(numerosAleatorios);

            // Enviar los números aleatorios y la suma al cliente.
            salida.writeObject(numerosAleatorios);
            salida.writeInt(suma);
            salida.flush();
        } catch (IOException | ClassNotFoundException e) {
            // Manejar excepciones en caso de error en la comunicación con el cliente.
            e.printStackTrace();
        }
    }

    /**
     * Genera un array de 10 números aleatorios entre 0 y 9 (inclusive).
     *
     * @return Array de enteros con números aleatorios.
     */
    private int[] generarNumerosAleatorios() {
        Random random = new Random();
        int[] numeros = new int[10];
        for (int i = 0; i < 10; i++) {
            numeros[i] = random.nextInt(10);  // Rango de 0 a 9 inclusive.
        }
        return numeros;
    }

    /**
     * Calcula la suma de un array de enteros.
     *
     * @param numeros Array de enteros.
     * @return Suma de los números en el array.
     */
    private int calcularSuma(int[] numeros) {
        int suma = 0;
        for (int numero : numeros) {
            suma += numero;
        }
        return suma;
    }

    /**
     * Inicia el servidor y espera continuamente conexiones de clientes.
     */
    public void iniciar() {
        while (true) {
            try {
                // Esperar una conexión de cliente.
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente);

                // Procesar la solicitud del cliente.
                ContestarMensaje(cliente);

                // Cerrar la conexión con el cliente.
                cliente.close();
            } catch (IOException e) {
                // Manejar excepciones en caso de error al aceptar una conexión.
                e.printStackTrace();
            }
        }
    }

    /**
     * Método principal que crea una instancia del servidor y lo inicia.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Configuración predeterminada del servidor.
        int PUERTO = 8080;
        String HOST = "localhost";
        
        // Crear e iniciar el servidor.
        Servidor servidor = new Servidor(PUERTO, HOST);
        servidor.iniciar();
    }
}
