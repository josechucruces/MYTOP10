package com.ejemplo.AgendaEstudiantes;


// importamos Arraylist de las librerias de java para utilizarlo

import java.util.ArrayList;
import java.util.Scanner;

// Creamos la clase Estudiante donde estan declarados los atributos que se van a utilizar en caso de que la llamemos

class Estudiante {
	
	// Atributo cadena de texto nombre
	
    private String nombre;
    
    // Atributo numero entero edad 
    
    private int edad;
    
    // Atributoo numero decimal para la nota de un estudiante
    
    private float notaFinal;

    // Este es el constructor de la clase Estudiante que tiene los atributos que le pasaremos 
    
    public Estudiante(String nombre, int edad, float notaFinal) {
        this.nombre = nombre;
        this.edad = edad;
        this.notaFinal = notaFinal;
    }

    // // Método getter para obtener el nombre del estudiante

    public String getNombre() {
    	
        return nombre;
    }
    // // Método que imprime en consola la información del estudiante (nombre, edad y nota final)
    
    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre + ", Edad: " + edad + ", Nota Final: " + notaFinal);
    }
}


// Clase Estudiantes que es la que aloja todo el menu principal y los menus internos con sus diversas funciones , ademas del main que ejecuta 

public class AgendaEstudiantes {
	
	// // Objeto Scanner para leer la entrada del usuario desde la consola 
	
    private static Scanner scanner = new Scanner(System.in);
    
    // declaramos el array Estudiante donde se guardaran los datos del menu Array y que tiene tres espacios fijos para cada estudiante
    
    private static Estudiante[] estudiantesArray = new Estudiante[3];
    
    // declaramos el ArrayList para el menu ArrayList
    
    private static ArrayList<Estudiante> estudiantesArrayList = new ArrayList<>();

    // Método principal que inicia el programa mostrando el menú principal

    public static void main(String[] args) {
    	
        mostrarMenuPrincipal();
    }

    // Aqui describimos el menu principal del programa, que no devuelve ningun valor
    
    private static void mostrarMenuPrincipal() {
    	
    	// Bucle infinito que mantiene el menú en ejecución hasta que el usuario elija salir
    	
        while (true) {
        	
        	// Sacamos en pantalla el texto de bienvenida y las diferentes lineas que muestran las opciones que tenemos
            System.out.println("\n--- Bienvenido a la Agenda de Estudiantes ---");
            System.out.println("1. Usar Array");
            System.out.println("2. Usar ArrayList");
            System.out.println("3. Salir");
            System.out.print("Escoge una opción (1, 2, 3): ");

            // declaramos la variable opcion para que guarde la opcion que acaban de escribir en pantalla y segun la opcion se actue de una manera u otra
            
            int opcion = scanner.nextInt();
            
            scanner.nextLine();

            // Estructura switch que ejecuta la acción según la opción seleccionada por el usuario
 
            switch (opcion) {
            
                case 1:
                	
                	// Método que gestiona el menú para operar con el arreglo fijo de estudiantes
                    menuArray();
                    break;
                    
                case 2:
                	
                	// Arrancamos el metodo menuArrayList()
                    menuArrayList();
                    break;
                    
                case 3:
                	
                	// Mostramos en pantalla que han escogido la opcion salir y salimos
                    System.out.println("Saliendo del programa...");
                    //cerramos scanner para que ya libere memoria
                    scanner.close();
                    return;
                    
                default:
                	
                	// Ponemos esta opcionn como proteccion en caso de que pongan una opcion no valida y lo muestre en pantalla
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
            }
        }
    }

    // Metodo menuArray() 
    
    private static void menuArray() {
    	
        // Declaramos la variable index , la inicializamos que utilizaremos para indexar en el array
    	
    	int index = 0;
    	
    	// Utilzamos while para que haga esto minetras sea true, sino pare
    	
        while (true) {
        	
        	// mostramos los menus de las opciones en pantalla
        	
            System.out.println("\n--- Menú Array ---");
            System.out.println("1. Agregar un estudiante al Array");
            System.out.println("2. Ver estudiantes en el Array");
            System.out.println("3. Volver al menú principal");
            System.out.print("Escoge una opción (1, 2, 3): ");
            
            // Declaramos la variable opcion donde se guarda la opcion de un numero entero que acaban de escribir en pantalla el usuario
            
            int opcion = scanner.nextInt();
            
            scanner.nextLine();

            // Segun la opcion que escoja el usuario le mostramos unas cosas u otras 
            
            switch (opcion) {
            
                case 1:
                	// En la primera opcion lo que hacemos es si array no esta lleno segun su tamaño que use el metodo agregarEstudiante para añadir un estudiante mas a la lista
                	
                    if (index < estudiantesArray.length) {
                    	
                    	// Aqui lo agrega el estudiante
                    	
                        estudiantesArray[index] = agregarEstudiante();
                        
                        //Aqui suma al tamaño del index como que hay uno mas
                        
                        index++;
                        
                        // Y nos muestra que el estudiante se agrego correctamente al array
                        
                        System.out.println("¡Estudiante agregado correctamente!");
                        
                    // Aqui utilizamos el else para en caso de que el array este ya lleno por que su tamaño sea superior al permitido ya te diga que no se puede añadir mas
                  
                    } else {
                   
                    	// En este caso te muestra en pantalla que esta lleno el array y que no se puede incluir
                       
                    	System.out.println("El array está lleno.");
                    }
                  
                    // Cuando ya acaba de hacer todo lo que tenia que hacer sale de la opcion
                  
                    break;
                case 2:
                	
                	// Muestra en pantalla el listao de estudiantes que tiene el array
                  
                	System.out.println("Estudiantes en el Array:");
                  
                	// Recorre el arreglo y muestra la información de cada estudiante si no es null

                    for (Estudiante estudiante : estudiantesArray) {
                    	
                    	// Lo muestra hasta que esncuentra ya un null
                      
                    	if (estudiante != null) {
                        
                    		// mostramos cada estudiante , la info que hay en el array 
                        
                    		estudiante.mostrarInfo();
                        }
                    }
                    break;
                    
                case 3:
                	
                	// opcion salir si escriben 3
                	
                    return;
                    
                default:
                	
                	// ponemos como opcion default que muestre que la opcion es invalida
                	
                    System.out.println("Opción inválida.");
            }
        }
    }

    // creamos el menu que si escogen el menuArrayList aqui describimos lo que va a hacer
    
    private static void menuArrayList() {
    	
        // mientras true seguira en el
    	
    	while (true) {
    		
    		// mostramos en pantalla el menu y las opciones a escoger 
            
    		System.out.println("\n--- Menú ArrayList ---");
            System.out.println("1. Agregar un estudiante al ArrayList");
            System.out.println("2. Ver estudiantes en el ArrayList");
            System.out.println("3. Eliminar un estudiante del ArrayList");
            System.out.println("4. Buscar un estudiante por nombre");
            System.out.println("5. Volver al menú principal");
            System.out.print("Escoge una opción (1, 2, 3, 4 o 5): ");

            // declaramos la variable opcion que es donde se recoge la opcion escogida por el usuario como un numero entero
          
            int opcion = scanner.nextInt();
            
            scanner.nextLine();

            // ponemos un switch para con la varaible opcion hara una cosa u otra, cada opcion esta descrita en cada case
          
            switch (opcion) {
              
            	case 1:
                
            		// Aqui llamamos al metodo agragarEstudiante y con el que coja los valores que se añaden al arraylist
                    estudiantesArrayList.add(agregarEstudiante());
                    //Mmostramos en pantalla que hemos agregado al ArrayList el estudiante
                    System.out.println("¡Estudiante agregado correctamente!");
                    break;
              
            	case 2:
                
            		
                    System.out.println("Estudiantes en el ArrayList:");
                    // Recorre y muestra la información de todos los estudiantes en el ArrayList
                    for (Estudiante estudiante : estudiantesArrayList) {
                        estudiante.mostrarInfo();
                    }
                    break;
                    
            	case 3:
            	    // Aqui mostramos en pantalla que nos diga el Estudiante que quiere eliminar 
            	    
            	    System.out.println("Ingrese el nombre del estudiante a eliminar:");
            	    
            	    // Recogemos el valor del nombre del estudiante 
            	    
            	    String nombreEliminar = scanner.nextLine();
            	    
            	    // declaramos la variable booleana llamada eliminado en false para que luego si encuentra el nombre que buscamos en el if diga que lo hemos encontrado y borrado y si no lo encuentra que diga que no lo encontro 
            	    
            	    boolean eliminado = false;
            	    
            	    // Esto es para buscar el nombre recorriendo el ArrayList y si lo encuentra lo borra
            	    
            	    for (int i = 0; i < estudiantesArrayList.size(); i++) {
            	        if (estudiantesArrayList.get(i).getNombre().equalsIgnoreCase(nombreEliminar)) {
            	        	// Eliminamos el estudiante en la posición i
            	            estudiantesArrayList.remove(i); 
            	            eliminado = true;
            	            // Salimos del bucle tras eliminar
            	            break; 
            	        }
            	    }
            	  
            	    if (eliminado) {
            	    
            	        // Aqui mostramos este texto en pantalla que se ha borrado el estudiante si el valor booleano de eliminado es true
            	 
            	        System.out.println("Estudiante eliminado correctamente.");

            	    } else {

            	        // Si no encuentra el Estudiante le sacamos esto por pantalla
            	   
            	        System.out.println("Estudiante no encontrado.");
            	    }

            	    break;

            
                case 4:
              
                	// Mostramos por pantalla que nos indique el estudiante que quiere buscar
                
                	System.out.println("Ingrese el nombre del estudiante a buscar:");
                    
                	// Leemos el nombre que nos da el usuario
                    
                	String nombreBuscar = scanner.nextLine();
                    
                	// deaclaramos la variable encontrado como false para despues buscar el nombre en el ArrayList y si lo encuentra que pase a ser True
                    
                	boolean encontrado = false;
                    
                	// REcorremos el ArrayList
                    
                	for (Estudiante estudiante : estudiantesArrayList) {
                    
                		// Comparamos los nombres con el que ha dado el usuario y si encuentra uno igual usamos mostrarinfo para enseñarlo el Estudiante y sus datos
                        
                		if (estudiante.getNombre().equalsIgnoreCase(nombreBuscar)) {
                        
                			// AQui el mostrar info
                            
                			estudiante.mostrarInfo();
                          
                			// Entonces como si lo hemos encontrao le decimos a la variable encontrado que es true
                         
                			encontrado = true;
                       
                			break;
                        }
                    }
                 
                	// Si no lo hemos encontrado por lo que es false lo que hacemos es mostrar en pantalla que no se encuentra el estudiante
                 
                	if (!encontrado) {
                		
                        System.out.println("Estudiante no encontrado.");
                	}
                	
                    break;
                    
                case 5:
                	
                    return;
                    
                default:
                	
                	// Esto es lo que mostramos en pantalla si el usuario no pone la opcion bien, escribe cualquier cosa que no sea esos numeros
                  
                	System.out.println("Opción inválida. Escribe solo 1, 2, 3, 4 o 5.");
            }
        }
    }

    // Método que pide al usuario los datos de un estudiante y devuelve un objeto Estudiante

    private static Estudiante agregarEstudiante() {
    	
        // Mostramos en pantalla nombre para que nos indique que nombre buscar o añadir o la funcion que queramos
    
    	System.out.print("Nombre: ");
    	
    	// Recogemos lo que el usuario escribe
        
    	String nombre = scanner.nextLine();
     
    	// Mostramos en pantalla edad : para que nos indique la edad que quiere buscar o añadir 
     
    	System.out.print("Edad: ");
     
    	//Recogemos la edad en la varaible edad
      
    	int edad = scanner.nextInt();
     
    	// Mostramos en pantalla nota final ... para que nos indique que nota tiene y que tiene que ser con coma para que no tenga error
    
    	System.out.print("Nota final (ejemplo 7,3 escribir con comas): ");
     
        //REcogemos el valor de la nota
    
    	float nota = scanner.nextFloat();
     
    	scanner.nextLine(); 
    
    	// Devolvemos los valores de nombre , edad y nota para que se puedan usar llamando a este metodo
     
    	return new Estudiante(nombre, edad, nota);
    }
}
