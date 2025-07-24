class Contacto:
    contador_contactos = 0  

    def __init__(self, nombre=''):
        Contacto.contador_contactos += 1
        self.id_contacto = Contacto.contador_contactos
        self.nombre = nombre

    def __str__(self):
        return f'Contacto [ID: {self.id_contacto}, Nombre: {self.nombre}]'
    
    def escribir_contacto(self):
        return f'{self.id_contacto},{self.nombre}'
