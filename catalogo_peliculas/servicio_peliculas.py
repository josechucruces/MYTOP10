import os
from pelicula import Pelicula

class Servicio_peliculas:
    NOMBRE_ARCHIVO ='peliculas.txt'
    
    def __init__(self):
        self.peliculas = []
        #revisar si ya esxiste el archivo snacks
        #si ya existe , obetenemos los snacks
        if os.path.isfile(self.NOMBRE_ARCHIVO):
            self.peliculas = self.obtener_peliculas()
        #si no existe cargamos algunos snaks iniciales
        else:
            self.cargar_peliculas_iniciales()
    
    def cargar_peliculas_iniciales(self):
        peliculas_iniciales = [
            Pelicula('Spiderman'),
            Pelicula('Batman'),
            ]    
        self.peliculas.extend(peliculas_iniciales)
        self.guardar_peliculas_archivo(peliculas_iniciales)
        
    def guardar_peliculas_archivo(self, peliculas):
        try:
            with open(self.NOMBRE_ARCHIVO, 'a') as archivo:
                for pelicula in peliculas:
                    archivo.write(f'{pelicula.escribir_pelicula()}\n')
        except Exception as e:
            print(" Error al guardar archivo {e}")
    
    def eliminar_catalogo(self):
        if os.path.exists(self.NOMBRE_ARCHIVO):
            os.remove(self.NOMBRE_ARCHIVO)
            self.peliculas = []  # Limpiamos la lista en memoria también
            print("Catálogo eliminado correctamente.")
        else:
            print("No existe un catálogo para eliminar.")
    
    def obtener_peliculas(self):
        peliculas = []
        try:
            with open(self.NOMBRE_ARCHIVO, 'r') as archivo:
                for linea in archivo:
                    id_pelicula, nombre = linea.strip().split(',')
                    pelicula = Pelicula(nombre)
                    pelicula.id_pelicula = int(id_pelicula)
                    peliculas.append(pelicula)
        except Exception as e:
            print(e)
        return peliculas

    def agregar_pelicula(self, pelicula):
        self.peliculas.append(pelicula)
        self.guardar_peliculas_archivo([pelicula])
    def mostrar_peliculas(self):
        print("--- Peliculas en el inventario ---")
        for pelicula in self.peliculas:
            print(pelicula) 
    def get_peliculas(self):
        return self.peliculas