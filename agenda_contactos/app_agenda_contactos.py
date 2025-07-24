from servicio_contactos import Servicio_contactos
from contacto import Contacto 


class App_agenda_contactos:
    def __init__(self):
        self.servicio_contactos = Servicio_contactos()

    def iniciar_app(self):
        print('*** Agenda de contactos ***') 
        self.servicio_contactos.mostrar_contactos()
        salir = False
        while not salir:
            try: 
                opcion = self.mostrar_menu()
                salir = self.ejecutar_opcion(opcion)
            except Exception as e:
                print(f"Ocurrió un error: {e}")

    def mostrar_menu(self):
        print('''\nMenu:
        1. Mostrar todos contactos
        2. Agregar contacto
        3. Eliminar todos contactos
        4. Eliminar un contacto
        5. Salir''')
        return int(input('Elige una opción: '))

    def ejecutar_opcion(self, opcion):
        if opcion == 1:
            self.mostrar_contactos()
        elif opcion == 2:
            self.agregar_contacto()
        elif opcion == 3:
            self.eliminar_todos_contactos()
        elif opcion == 4:
            self.mostrar_contactos()
            self.eliminar_un_contacto()
        elif opcion == 5:
            print("¡Regresa pronto!")
            return True
        else:
            print(f'Opción inválida: {opcion}')
        return False  
        
    def mostrar_contactos(self):
        contactos = self.servicio_contactos.get_contactos()
        if not contactos:
            print("No hay contactos en la agenda.")
            return 
        print('\n--- Agenda de contactos ---')
        for contacto in contactos:
            print(f'\t- {contacto.nombre}')

    def agregar_contacto(self):
        nombre = input('Nombre del contacto: ')
        nuevo_contacto = Contacto(nombre)
        self.servicio_contactos.agregar_contacto(nuevo_contacto)  
        print('Contacto agregado correctamente a la agenda.')

    def eliminar_todos_contactos(self):
        confirmacion = input('¿Estás seguro de que quieres eliminar la agenda? (s/n): ')
        if confirmacion.lower() == 's':
            self.servicio_contactos.eliminar_todos_contactos()
        else:
            print('Operación cancelada.')
    def eliminar_un_contacto(self):
        contacto_eliminar = input('De la lista de contactos que acabo de mostrar, ¿cuál quieres eliminar?: ')
        if self.servicio_contactos.eliminar_contacto_por_nombre(contacto_eliminar):
            print(f'El contacto "{contacto_eliminar}" fue eliminado correctamente.')
        else:
            print(f'No se encontró ningún contacto con el nombre "{contacto_eliminar}".')

        
# Programa principal
if __name__ == '__main__':
    app_agenda_contactos = App_agenda_contactos()
    app_agenda_contactos.iniciar_app()
