import os
from snack import Snack

class ServicioSnacks:
    NOMBRE_ARCHIVO ='snacks.txt'
    
    def __init__(self):
        self.snacks = []
        #revisar si ya esxiste el archivo snacks
        #si ya existe , obetenemos los snacks
        if os.path.isfile(self.NOMBRE_ARCHIVO):
            self.snacks = self.obtener_snacks()
        #si no existe cargamos algunos snaks iniciales
        else:
            self.cargar_snacks_iniciales()
    
    def cargar_snacks_iniciales(self):
        snacks_iniciales = [
            Snack('Papas', 70),
            Snack('Refresco', 50),
            Snack('Sandwich', 120)
            ]    
        self.snacks.extend(snacks_iniciales)
        self.guardar_snacks_archivo(snacks_iniciales)
        
    def guardar_snacks_archivo(self, snacks):
        try:
            with open(self.NOMBRE_ARCHIVO, 'a') as archivo:
                for snack in snacks:
                    archivo.write(f'{snack.escribir_snack()}\n')
        except Exception as e:
            print(" Error al guardar archivo {e}")
    
    def obtener_snacks(self):
        snacks = []
        try:
            with open(self.NOMBRE_ARCHIVO, 'r') as archivo:
                for linea in archivo:
                    id_snack, nombre, precio = linea.strip().split(',')
                    snack = Snack(nombre, float(precio))
                    snack.id_snack = int(id_snack)
                    snacks.append(snack)
        except Exception as e:
            print(e)
        return snacks


    def agregar_snack(self):
        self.snacks.append(snack)
        self.guardar_snacks_archivo([snack])
    def mostrar_snacks(self):
        print("--- Snacks en el inventario ---")
        for snack in self.snacks:
            print(snack) 
    def get_snacks(self):
        return self.snacks