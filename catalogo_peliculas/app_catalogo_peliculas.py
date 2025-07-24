from servicio_peliculas import Servicio_peliculas 
from pelicula import Pelicula  


class App_catalogo_peliculas:
    def __init__(self):
        self.servicio_peliculas = Servicio_peliculas()
        self.productos = []

    def app_catalogo_peliculas(self):
        print('*** Catalogo peliculas ***') 
        self.servicio_peliculas.mostrar_peliculas()
        salir = False
        while not salir:
            try: 
                opcion = self.mostrar_menu()
                salir = self.ejecutar_opcion(opcion)
            except Exception as e:
                print(f"Ocurrió un error: {e}")

    def mostrar_menu(self):
        print('''\nMenu:
        1. Mostrar pelicula
        2. Agregar pelicula
        3. Eliminar catalogo
        4. Salir''')
        return int(input('Elige una opción: '))

    def ejecutar_opcion(self, opcion):
        if opcion == 1:
            self.mostrar_pelicula()
        elif opcion == 2:
            self.agregar_pelicula()
        elif opcion == 3:
            self.eliminar_catalogo()
        elif opcion == 4:
            print("¡Regresa pronto!")
            return True
        else:
            print(f'Opción inválida: {opcion}')
        return False  
        
    def mostrar_pelicula(self):
        peliculas = self.servicio_peliculas.get_peliculas()
        if not peliculas:
            print("No hay películas en el catálogo.")
            return 
        print('\n--- Catálogo de películas ---')
        for pelicula in peliculas:
            print(f'\t- {pelicula.nombre}')


    def agregar_pelicula(self):
        nombre = input('Nombre de la película: ')
        nuevo_pelicula = Pelicula(nombre)
        self.servicio_peliculas.agregar_pelicula(nuevo_pelicula)  # <-- se pasa la película
        print('Película agregada correctamente al catálogo.')

        
    def eliminar_catalogo(self):
        confirmacion = input('¿Estás seguro de que quieres eliminar el catálogo? (s/n): ')
        if confirmacion.lower() == 's':
            self.servicio_peliculas.eliminar_catalogo()
        else:
            print('Operación cancelada.')

# Programa principal
if __name__ == '__main__':
    app_catalogo_peliculas = App_catalogo_peliculas()
    app_catalogo_peliculas.app_catalogo_peliculas()
