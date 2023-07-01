# TODO
- Añadir contador de fichas en tablero funcional

# mODIFICACION

Modificar el funcionamiento del juego para que:

1.- durante un movimiento de ficha (comando M), se pueda pasar por la casilla de inicio (introducida previamente con el comando I), situación que con el enunciado original no está permitido.

Por ejemplo: "M NSE" en el juego original sería un movimiento inválido incluso aunque las casillas del Norte y Este de la posición de inicio del movimiento estuvieran vacías. Con la modificación, debe considerarse un movimiento válido.
Se puede probar en el modo debug con los siguiente comandos:

I B,1

F B,0

M SNO  (este último comando es el que en la versión original del juego no era considerado un movimiento válido y ahora si).

2.- Modificar el EQU correspondiente para que el juego pase a funcionar con 7 tipos de fichas diferentes, no 5 como hasta ahora.
Para probarlo basta comprobar el modo aleatorio y ver que se generan nuevos tipos de fichas tanto en el tablero como en siguientes.
Como hace falta asociar a las nuevas fichas dos nuevos colores, os damos la redefinición de:
    colorFic      db 01h,0Ah,09h,08h,0Dh,0Eh,04h,0Fh



# Cosas a mejorar
- Reducir metodos MostrarMensajeX

En esta práctica se realizarán todo lo relativo a la ejecución de los comandos:

Comando I:  Comprobación de las coordenadas de inicio (existencia de la ficha en esa posición y escritura en fichasMov, sino mensaje de error correspondiente); si es correcta, mostrar información en la pantalla de juego. Nota: se permite volver a pedir la ficha de inicio, aun habiéndola pedido antes.

Comando F: Comprobación de las coordenadas de fin (no existencia de ficha en esa posición y gestión en fichasMov, sino mensaje de error correspondiente); si es correcta, mostrar información en la pantalla de juego. Nota: se permite volver a pedir el final, aun habiéndolo pedido antes.

Comando M: Comprobación de fichasMov para asegurarse que se han usado los comandos I y F antes del comando M. Comprobación de la trayectoria <inicio,final> (comprobar que la trayectoria no hace que la ficha se salga del tablero en ningún momento, que no pasa por casillas con ficha -incluida la inicial- y que llega a la casilla final que se había especificado con el comando F previamente). Si la trayectoria es incorrecta, sacar los mensajes pertinentes.

Si la trayectoria es correcta (no es necesario seguir el orden de puntos indicados):

    1.- Realización del movimiento (visualmente, borrar la ficha de inicio y mostrar la ficha en la casilla final) y en tableroJuego reflejar también el cambio de posición de las fichas. Nota: este apartado debería aprovechar procedimientos de la sesión 9: pintar fichas de colores (que como comentábamos en esa sesión se puede generalizar para borrar una ficha simplemente escribiendo como carácter CARNOFICHA en lugar de CARFICHA).

    2.- Comprobación en la fila y columna de líneas con FICIGUALES fichas del mismo tipo (el orden de comprobación será: 1ro por fila (si se encuentra, no es necesario buscar en la columna) de izquierda a derecha; 2do por columna, de arriba a abajo. Si hay más de FICIGUALES fichas seguidas del mismo tipo solo se eliminan las FICIGUALES primeras.

            2.1.- Si las hay, borrarlas visualmente del tablero y actualizar tableroJuego y puntos. Existe una cadena que indica al usuario que el programa está trabajando. Nota: son llamadas a procedimientos ya creados.

            2.2.- En caso contrario, actualizar ficEnTablero y controlar que no se haya alcanzado o superado TOTALCELDAS (o tengamos huecos sufientes para poder seguir moviendo la siguiente vez). En ese caso se producirá el fin del juego. Si no se ha alcanzado ese límite, colocar las 2 fichas siguientes en posiciones aleatorias vacías de tableroJuego y pintarlas en pantalla, también generar otras 2, almacenarlas y visualizarlas en la zona de fichas siguientes. Nota: la mayor parte de este apartado deberían ser aprovechable con código de la sesión 9: generación de fichas siguientes aleatorias, pintar fichas de colores y rellenar posiciones aleatorias del vector (que hacíamos con valores aleatorios y ahora son fijos).

     3.- Reiniciar fichasMov y borrar de pantalla los valores antiguos.

Comando E: Reinicio del juego. Nota: cuidado con reiniciar todas la variables necesarias (puntos, fichasMov, tableroJuego...).

Comando S: Salir del juego (vuelta al sistema operativo).