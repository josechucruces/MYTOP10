class Pelicula:
    contador_peliculas = 0  

    def __init__(self, nombre=''):
        Pelicula.contador_peliculas += 1
        self.id_pelicula = Pelicula.contador_peliculas
        self.nombre = nombre

        
    def __str__(self):
        return (f'Pelicula: id_pelicula = {self.id_pelicula}, nombre = {self.nombre}')
    
    def escribir_pelicula(self):
        return f'{self.id_pelicula},{self.nombre}'