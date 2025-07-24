import os
from contacto import Contacto

class Servicio_contactos:
    NOMBRE_ARCHIVO = 'agenda_contactos.txt'
    
    def __init__(self):
        self.contactos = []
        if os.path.isfile(self.NOMBRE_ARCHIVO):
            self.contactos = self.obtener_contactos()
        else:
            self.cargar_contactos_iniciales()
    
    def cargar_contactos_iniciales(self):
        contactos_iniciales = [
            Contacto('Manuel'),
            Contacto('Pedro'),
        ]    
        self.contactos.extend(contactos_iniciales)
        self.guardar_contactos_archivo(contactos_iniciales)
        
    def guardar_contactos_archivo(self, contactos):
        try:
            with open(self.NOMBRE_ARCHIVO, 'a') as archivo:
                for contacto in contactos:
                    archivo.write(f'{contacto.escribir_contacto()}\n')
        except Exception as e:
            print(f"Error al guardar archivo: {e}")
    
    def eliminar_todos_contactos(self):
        if os.path.exists(self.NOMBRE_ARCHIVO):
            os.remove(self.NOMBRE_ARCHIVO)
            self.contactos = []
            print("Agenda eliminada.")
        else:
            print("No existe una agenda para eliminar.")
    def eliminar_contacto_por_nombre(self, nombre):
        contacto_encontrado = None
        for contacto in self.contactos:
            if contacto.nombre.lower() == nombre.lower():
                contacto_encontrado = contacto
                break

        if contacto_encontrado:
            self.contactos.remove(contacto_encontrado)
            try:
                with open(self.NOMBRE_ARCHIVO, 'w') as archivo:
                    for c in self.contactos:
                        archivo.write(f'{c.escribir_contacto()}\n')
            except Exception as e:
                print(f'Error al actualizar la agenda: {e}')
            return True
        return False

    
    def obtener_contactos(self):
        contactos = []
        try:
            with open(self.NOMBRE_ARCHIVO, 'r') as archivo:
                for linea in archivo:
                    id_contacto, nombre = linea.strip().split(',')
                    contacto = Contacto(nombre)
                    contacto.id_contacto = int(id_contacto)
                    contactos.append(contacto)
        except Exception as e:
            print(e)
        return contactos

    def agregar_contacto(self, contacto):
        self.contactos.append(contacto)
        self.guardar_contactos_archivo([contacto])

    def mostrar_contactos(self):
        print("--- Contactos en la agenda ---")
        for contacto in self.contactos:
            print(contacto)

    def get_contactos(self):
        return self.contactos
