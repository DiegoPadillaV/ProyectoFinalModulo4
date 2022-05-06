package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Auto;
import modelo.Cliente;
import modelo.Mantencion;

public class Main {

	// Scanner estatico para uso a lo largo del codigo
	static Scanner scanner = new Scanner(System.in);

	// ArrayList de los objetos que utilizaremos
	private static ArrayList<Auto> autos = new ArrayList<>();
	private static ArrayList<Cliente> clientes = new ArrayList<>();
	private static ArrayList<Mantencion> mantenciones = new ArrayList<>();

	// Inicializa constantes de menu
	private final static int OPCION_MENU_AGREGAR_CLIENTE = 1;
	private final static int OPCION_MENU_AGREGAR_MANTENCION = 2;
	private final static int OPCION_MENU_ELIMINAR_CLIENTE = 3;
	private final static int OPCION_MENU_LISTA_MANTENCIONES = 4;
	private final static int OPCION_MENU_RECAUDACIONES = 5;
	private final static int OPCION_MENU_VER_CLIENTES = 6;

	private final static int OPCION_MENU_SALIR = 0;

	public static void main(String[] args) {

		menu();
	}

	private static void menu() {
		// Declara variable opcion para uso y asignacion en menu
		int opcion = 99;

		// Encierra el menu en un ciclo while hasta que el usuario presione SALIR
		do {
			System.out.printf("Bienvenido al sistema%n" + "======================%n" + "1. Agregar cliente/auto%n"
					+ "2. Agregar mantencion de auto%n" + "3. Eliminar cliente%n" + "4. Lista de mantenciones%n"
					+ "5. Recaudaciones del dia%n" + "6. Ver clientes%n" + "0. Salir%n%n" + "Escoja una opcion%n");

			try {

				// Toma un int del usuario, si esta entre las opciones, hara lo que corresponda,
				// sino, volvera a pedir.
				opcion = scanner.nextInt();

				switch (opcion) {
				case OPCION_MENU_AGREGAR_CLIENTE:
					agregarCliente();
					break;
				case OPCION_MENU_ELIMINAR_CLIENTE:
					eliminarCliente();
					break;
				case OPCION_MENU_LISTA_MANTENCIONES:
					listarMantenciones();
					break;
				case OPCION_MENU_RECAUDACIONES:
					verRecaudaciones();
					break;
				case OPCION_MENU_AGREGAR_MANTENCION:
					agregarMantencion();
					break;
				case OPCION_MENU_VER_CLIENTES:
					verClientes();
					break;
				case OPCION_MENU_SALIR:
					System.out.println("Cerrando programa...");
					break;
				default:
					System.out.println("Opción escogida inválida. Intente de nuevo\n\n");
					break;
				}
			} catch (Exception e) {
				System.out.println("Ingreso invalido.\n");
				scanner.nextLine();
			}
		} while (opcion != OPCION_MENU_SALIR);

	}

	private static void verClientes() {
		for (Cliente cliente : clientes) {
			System.out.printf(
					"NOMBRE CLIENTE: %s %s%n" + "RUT CLIENTE: %s%n" + "FONO CLIENTE: %s%n" + "CORREO CLIENTE: %s%n"
							+ "AUTOS REGISTRADOS: %n%n",
					cliente.getNombres(), cliente.getApellidos(), cliente.getRut(), cliente.getFono(),
					cliente.getCorreo());

			System.out.println(cliente.autosCliente());
		}

	}

	private static void agregarMantencion() {
		
		Validaciones validar = new Validaciones();
		
		try {
			// MUESTRA AUTOS REGISTRADOS Y SUS DATOS

			// String que guardara los datos de autos a mantener
			String stringAuto = "";

			// Si hay autos registrados, toma sus datos y los añade al string declarado. De
			// lo contrario, tira error.
			if (autos.size() > 0) {
				System.out.println("AUTOS REGISTRADOS SIN MANTENCION");

				for (Auto auto : autos) {
					if (auto.isMantenido() == false) {

						stringAuto += "=======================================\n" + "PPU AUTO: " + auto.getPpu() + "\n"
								+ "MARCA AUTO: " + auto.getMarca() + "\n" + "MODELO AUTO: " + auto.getModelo() + "\n"
								+ "FABRICACION AUTO: " + auto.getFecha() + "\n" + "DUEÑO AUTO: "
								+ auto.getPoseedor().getNombres() + " " + auto.getPoseedor().getApellidos() + "\n"
								+ "=======================================\n";
					}
				}
			} else {
				System.out.println("NO HAY AUTOS REGISTRADOS.");
				throw new Exception("No hay autos registrados");

			}
			// Antes de seguir, se chequea si el stringAuto fue modificado. Si esta vacio
			// quiere decir que no hay autos a los que hacer mantencion, cancela todo y
			// vuelve al menu.
			if (stringAuto.equalsIgnoreCase("")) {
				System.out.println("NO HAY AUTOS A LOS QUE HACER MANTENCION");
				throw new Exception("No hay autos que mantener");
			}

			// Muestra en pantalla los autos disponibles para hacer mantencion
			System.out.println(stringAuto);

			// Pide la PPU del auto al que se le hizo mantencion
			String ppuMantencion = validar.getString("Escriba PPU del auto al que se ha hecho mantencion");

			// Declara un Auto como nulo para futura asignacion
			Auto autoMantencion = null;

			// BUSCA EL AUTO SEGUN PPU EN ARRAYLIST autos, CUANDO LO ENCUENTRA, LO ASIGNA A
			// VARIABLE ANTES DECLARADA (autoMantencion).
			for (Auto auto : autos) {
				if (ppuMantencion.equalsIgnoreCase(auto.getPpu())) {
					autoMantencion = auto;
				}
			}

			// Si esta variable esta nula, cancela todo. No se encontró un auto
			if (autoMantencion == null) {
				System.out.println("Auto no encontrado");
				throw new Exception("AUTO NO ENCONTRADO");
			}

			// Pide el resto de datos para la creacion de un objeto Mantencion
			String tipoMantencion = validar.getString("Escriba el tipo de mantencion realizado");

			String observacionesMantencion = validar.getString("Escriba observaciones realizadas");

			int montoMantencion = validar.getInt("Escriba monto cobrado");

			// CREA EL OBJETO MANTENCION Y LO AGREGA A ARRALIST MANTENCIONES
			Mantencion mantencion = new Mantencion(tipoMantencion, observacionesMantencion, montoMantencion,
					autoMantencion, LocalDate.now());
			mantenciones.add(mantencion);
			// Setea el estado de mantenido del auto a true.
			autoMantencion.setMantenido(true);
		} catch (Exception e) {
		}
	}

	private static void verRecaudaciones() {
		// Declara int para recaudaciones y autos mantenidos, aumentaran de valor por
		// cada mantencion exitosa
		int recaudaciones = 0;
		int autosMantenidos = 0;

		for (Mantencion mantencion : mantenciones) {
			recaudaciones += mantencion.getMontoServicio();
			autosMantenidos++;
		}
		// Llama al metodo listarMantenciones para mostrar un listado de los autos a los
		// que se les hizo mantencion
		listarMantenciones();
		System.out.printf("Recaudaciones totales: $%d%n " + "Autos mantenidos: %d %n%n", recaudaciones,
				autosMantenidos);

	}

	private static void listarMantenciones() {
		System.out.println("Listado de mantenciones realizadas: \n===================================================");

		// Por cada mantencion realizada, muestra los datos en la consola
		for (Mantencion mantencion : mantenciones) {
			System.out.println("PPU AUTO MANTENIDO: " + mantencion.getAuto().getPpu());
			System.out.println("FECHA DE MANTENCION: " + mantencion.getFechaMantencion());
			System.out.println("DUEÑO DE AUTO MANTENIDO: " + mantencion.getAuto().getPoseedor().getNombres() + " "
					+ mantencion.getAuto().getPoseedor().getApellidos());
			System.out.println("TIPO MANTENCION: " + mantencion.getMantencionRealizada());
			System.out.println("OBSERVACIONES: " + mantencion.getObservaciones());
			System.out.println("MONTO: " + mantencion.getMontoServicio());
			System.out.println("===================================================\n");
		}
	}

	private static void eliminarCliente() {
		
		Validaciones validar = new Validaciones();
		
		// Busca al cliente segun rut y si lo encuentra, elimina todos los autos
		// asociados y luego al mismo cliente.
		// Pero si uno de los autos asociados ya ha pasado por mantencion, no elimina el
		// auto en cuestion por motivos de registro.
		
		String rut = validar.getString("Escriba el rut del cliente a eliminar: ");

		for (Cliente cli : clientes) {
			if (rut.equalsIgnoreCase(cli.getRut())) {

				for (Auto auto : autos) {
					if (auto.getPoseedor().getRut().equalsIgnoreCase(rut) && auto.isMantenido() == false) {
						System.out.println("Se ha eliminado el auto (PPU): " + auto.getPpu());
						autos.remove(auto);
					}
				}

				System.out.printf("Se ha eliminado el cliente: %s %n%n", cli.getNombres());
				clientes.remove(cli);
				break;
			}
		}
	}

	private static void agregarCliente() {
		
		Validaciones validar = new Validaciones();
		
		try {

			// PIDE DATOS CLIENTE
			
			String rutCliente = validar.getString("Ingresar RUT");

				// Busca al cliente con el rut proporcionado, si este existe en los registros,
				// se salta el resto del metodo y pasa directo a añadir autos.
				for (Cliente cli : clientes) {
					if (cli.getRut().equalsIgnoreCase(rutCliente)) {
						agregarAutos(cli);
						break;
					}
				}

				System.out.println("Cliente no registrado. Ingrese datos del nuevo cliente.");
				
					String nombresCliente = validar.getString("Ingresar nombres del cliente");

					String apellidosCliente = validar.getString("Ingresar apellidos del cliente");

					String correoCliente = validar.getString("Ingresar correo del cliente");

					String fonoCliente = validar.getString("Ingresar telefono del cliente");

					// CREACION CLIENTE CON DATOS PEDIDOS
					Cliente cliente = new Cliente(nombresCliente, apellidosCliente, rutCliente, correoCliente,
							fonoCliente);
					clientes.add(cliente);

					// Con el cliente creado, pasa a agregar autos para el cliente. Envia el cliente
					// recien creado como parametro
					agregarAutos(cliente);
		} catch (Exception e) {
			System.out.println("Parametros invalidos. Intente de nuevo");
		}
	}

	public static void agregarAutos(Cliente cliente) {
		
		// DATOS AUTO
		// Se repite las veces que sea necesaria, segun la cantidad de autos del cliente
		boolean agregarAutos = true;
		
		Validaciones validar = new Validaciones();
		
		try {
			do {
				
				String ppuAuto = validar.getString("Ingresar PPU del auto").toUpperCase();

				String marcaAuto = validar.getString("Ingrese marca del auto");

				String modeloAuto = validar.getString("Ingresar modelo del auto");

				int yearAuto = validar.getInt("Ingrese año de fabricacion del auto");
				
				if (yearAuto < 1960 || String.valueOf(yearAuto).length() > 4) {
					throw new Exception("AÑO INVALIDO(Debe ser mayor al año 1960 y tener solo 4 digitos). INTENTE DE NUEVO");
				}

				// Crea el objeto Auto con los datos proporcionados.
				Auto auto = new Auto(ppuAuto, marcaAuto, modeloAuto, yearAuto, cliente, false);
				autos.add(auto); // AÑADE AL ARRAY PARA USO FUTURO
				cliente.addAuto(auto); // AÑADE EL AUTO REGISTRADO AL CLIENTE

				String decision = validar.getString("¿Desea seguir añadiendo autos? (si/no)");

				if (decision.equalsIgnoreCase("NO")) {
					agregarAutos = false;
					break;
				}
			} while (agregarAutos);
		} catch (Exception e) {
			System.out.println("Parametros invalidos. Intente de nuevo");
		}
	}
}
