 ;EQUIVALENCIAS USADAS PARA ESTRUCTURAS Y VALORES DE DATOS

 FILCOLJUEGO    EQU 10 ; num de filas/columnas del tablero de juego
 TOTALCELDAS    EQU FILCOLJUEGO*FILCOLJUEGO  ; num total de casillas del tablero de juego

 TIPOSFIC       EQU  7 ; num de tipos de fichas distintas
 FICIGUALES     EQU  5 ; num de fichas iguales seguidas para juntar y que se eliminen durante el juego (>1)
 FICSIGUIENTES  EQU  2 ; num de fichas siguientes 
 FICINICIAL     EQU 20 ; num de fichas iniciales
 PUNTFIN        EQU 10 ; Puntuacion para ganar el juego
 PUNTOSPORACIERTO EQU 2 ; Puntos a aï¿½adir en caso de acertar

 ;EQUIVALENCIAS USADAS PARA REPRESENTAR DATOS

 CARFICHA       EQU 'o' ; Caracter para mostrar en pantalla correspondiente a una ficha
 CARNOFICHA     EQU ' ' ; Caracter para "borrar"/sobreescribir una ficha


 ;EQUIVALENCIAS DE COORDENADAS PARA PINTAR EN PANTALLA (FIL, COL)

 FILMODO        EQU 10
 COLMODO        EQU 44  ; Posicion para mostrar el mensaje de peticion del modo inicial (Debug o no)

 FILFA          EQU  3
 COLC0          EQU 28  ; Posicion de tablero Fila A, Columna 0

 FILMSG         EQU 23
 COLMSG         EQU  8  ; Posicion para mostrar el mensaje "Indica comando >> "

 FILENTRADA     EQU FILMSG
 COLENTRADA     EQU COLMSG+18  ; Posicion de la peticion de cadena al usario

 FILPUNT        EQU  7
 COLPUNT        EQU 61  ; Posicion de la puntuacion

 FILSIGS        EQU 10
 COLSIGS        EQU COLPUNT  ; Posicion de las dos fichas siguientes

 FILMDE         EQU 15
 COLMDE         EQU COLPUNT  ; Posicion del origen de movimiento de una ficha

 FILMA          EQU 16
 COLMA          EQU COLPUNT  ; Posicion del destino de movimiento de una ficha


data segment
	puntos        db 0  ; Almacena la puntuacion durante el juego

	SiguienteFic  db FICSIGUIENTES DUP (?)  ; fichas siguientes, que apareceran durante la partida

	ficEnTablero  db 0  ; num fichas que hay en el tablero durante el juego

	fichasMov     db 4 dup(-1)  ; vector con 4 elementos: Fini,Cini,Ffin,Cfin
								; Fila y columa de inicio y de fin de un movimiento
								; -1 indica que no hay seleccionada posicion de inicio/fin

	;La estructura a usar es una matriz, la de modo debug prerellena es de 10x10
	; los valores del 1 al 5 indican fichas de distinto tipo
	arcadeDebug   db 0, 5, 5, 0, 5, 5, 0, 0, 0, 0 ;  > Fila A
				  db 0, 2, 0, 0, 2, 2, 0, 0, 0, 0 ;  > Fila B
				  db 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 ;  > Fila C
				  db 0, 0, 4, 0, 0, 0, 0, 0, 0, 0 ;  > Fila D
				  db 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 ;  > Fila E
				  db 0, 0, 3, 0, 0, 0, 0, 0, 0, 0 ;  > Fila F
				  db 0, 1, 0, 0, 0, 1, 1, 0, 0, 0 ;  > Fila G
				  db 0, 0, 0, 5, 0, 1, 0, 2, 0, 0 ;  > Fila H
				  db 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 ;  > Fila I
				  db 0, 0, 0, 3, 0, 0, 0, 0, 0, 0 ;  > Fila J

	tableroJuego  db TOTALCELDAS dup(0) ; Almacena el tablero de juego


	;colorFic      db 01h,0Ah,09h,08h,0Dh,0Eh ; TIPOSFIC que puede tomar una ficha en pantalla
    colorFic db 01h,0Ah,09h,08h,0Dh,0Eh,04h,0Fh
	fil           db ?
	col           db ?  ; Usadas en el procedimiento ColocarCursor

	colMatriz     db ?
	filMatriz     db ?
	posMatriz     db ?  ; Usadas en los procedimientos VectorAMatriz y MatrizAVector


  ;**************************CADENAS ********************************

	cadenaE          db 21, ?, 21 dup (?) ; Cadena de entrada con el posible comando

	cadPuntos        db 6 dup (?)         ; Mensaje para mostrar puntuacion

	msgBlancoLargo   db 47 dup (' '),'$'  ; Cadena para borrar la entrada incorrecta de un comando o mensajes de error mostrados
	msgBlancoCorto   db "    $"           ; Cadena para borrar el S/N de debug y los datos de fila y columna de origen y destino de un movimiento

	msgIntOp         db "Indica comando >> $"
	msgInicT         db "<Colocando fichas> $"
	msgPintando      db "Poniendo las fichas en tablero...$"
	msgDebug         db "  Modo debug (con Tablero conocido)? (S/N)$"
	msgGenAleat      db 13, 10, 13, 10, "  Generando tablero aleatorio...espere por favor...$"
	msgGenDebug      db 13, 10, 13, 10, "  Cargando tablero...$"
	msgErrFinal      db "Error:fin con ficha.$"
	msgErrInicio     db "Error:inicio sin ficha.$"
	msgErrRango      db "Error:la direccion saca la ficha del tablero.$"
	msgErrCeldaFicha db "Error:la direccion pasa por una celda con ficha.$"
	msgMNoLlega      db "Error:la direccion no llega al final indicado.$"
	msgErMoverIF     db "Error:no se puede mover sin inicio y fin.$"
	msgAgrEnFila     db "Eliminando fichas en fila...$"
	msgAgrEnCol      db "Eliminando fichas en columna...$"
	msgPartidaGanada      db "...ENHORABUENA! ... Has ganado!  ;-)$"
	msgPartidaPerdida     db "...LO SIENTO!... Has perdido! ;-($"
	msgPartidaFinalizada  db "...Juego finalizado...adios...$"

	pantallaInicio db 10, 13, 10
		   db "    _                     _        _     _                  ",10, 13
		   db "   / \   ___ ___ __ _  __| | ___  | |   (_)_ __   ___  ___  ",10, 13
		   db "  / _ \ | __/ __/ _` |/ _` |/ _ \ | |   | | '_ \ / _ \/ __| ",10, 13
		   db " / ___ \| | |(_  (_| | (_| |  __/ | |___| | | | |  __/\__ \ ",10, 13
		   db "/_/   \_\_| \___\__,_|\__,_|\___| |_____|_|_| |_|\___||___/ ",10, 13, '$'

	pantTablero db "                           ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "                           ³0³1³2³3³4³5³6³7³8³9³   Arcade Lines",10, 13
				db "    Comandos             ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "    --------             ³A³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³",10, 13
				db "I <F,C> -> Ficha inicio  ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "F <F,C> -> Posic.  final ³B³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³",10, 13
				db "M <dir> -> Mover ficha   ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "E       -> Empezar       ³C³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³      Puntos:   / 10"   ,10, 13
				db "S       -> Salir         ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "                         ³D³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³",10, 13
				db "                         ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ  Siguientes:", 10, 13
				db " F,C: Fila,Col (ej. A,2) ³E³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³",10, 13
				db " dir: direccion (ej. NE) ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "                         ³F³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³     Mover Ficha"  ,10, 13
				db "  hasta 18 trayectorias: ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ" ,10, 13
				db "          N:norte        ³G³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³          de ",10, 13
				db "          S:sur          ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ           a  ",10, 13
				db "          E:este         ³H³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³",10, 13
				db "          O:oeste        ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "                         ³I³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³",10, 13
				db "                         ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13
				db "                         ³J³ ³ ³ ³ ³ ³ ³ ³ ³ ³ ³",10, 13
				db "                         ÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅÄÅ",10, 13,'$'

  ;*****************************************************************************
  ;* A partir de este punto puedes declarar variables auxiliares que necesites *
  ;*****************************************************************************

    msgPuntos db 3 dup(0)
    movimientos db 18 dup(0)
	
data ends


code segment
;*************************************************************************************
;************************ procedimientos dados ***************************************
;*************************************************************************************

;F: Calcula un valor aleatorio entre 0 y un valor maximo dado-1
;E: BL valor maximo (1...99)
;S: AH valor aleatorio calculado (0...BL-1)
proc NumAleatorio
	push cx
	push dx

	mov ah, 2Ch ;interrupcion que recupera la hora actual del sistema operativo
	int 21h
	;ch=horas cl=minutos dh=segundos dl=centesimas de segundo, 1/100 secs

	xor ah, ah
	mov al, dl
	div bl

	pop dx
	pop cx
    ret
endp NumAleatorio

 ;F: Imprime una cadena terminada en $ en la posicion donde se encuentre el cursor
 ;E: DX direccion de comienzo de la cadena a imprimir
proc ImprimirCadena
	push ax

	mov ah, 9
	int 21h

	pop ax
   ret
endp ImprimirCadena


 ;F: Lee una cadena por teclado
 ;E: DX contiene la direccion de la cadena donde se almacenarï¿½ la cadena leida
 ;E: la posicion 0 de esa cadena debe contener el numero maximo de caracteres a leer
proc LeerCadena
	push ax

	mov ah, 0ah
	int 21h

	pop ax
   ret
endp LeerCadena


 ;Convierte un numero entero a una cadena de caracteres terminada en $
 ;E: AX contiene el numero a convertir
 ;E: DX contiene la direccion de la cadena donde almacena la cadena resultado
proc NumeroACadena
	push ax
	push bx
	push cx
	push dx
	push di

	mov bx, 10
	mov di, dx

	xor cx, cx

	cmp ax, 0
	jge BNumCad

	mov [di], '-'
	inc di
	neg ax

   BNumCad:        ;Bucle que transforma cada digito a caracter, de menor a mayor peso
	xor dx, dx
	div bx
	add dl, '0'
	push dx
	inc cx
	cmp ax, 0
	jne BNumCad

   BInvertir:      ;Bucle para invertir los restos
	pop [di]
	inc di
	loop BInvertir

	mov [di], '$'

	pop di
	pop dx
	pop cx
	pop bx
	pop ax
   ret
endp NumeroACadena


;F: Coloca el cursor en una determinada fila y columna de pantalla
;E: fil - Fila
;E: col - Columna
proc ColocarCursor
    	push ax
    	push bx
    	push dx

    	mov ah, 2
    	mov dh, fil
    	mov dl, col
    	xor bh, bh
	    int 10h

	    pop dx
	    pop bx
	    pop ax
    	ret
endp ColocarCursor

 ;F: Lee un caracter por teclado con eco por pantalla
 ;S: AL caracter ASCII leido
proc LeerTeclaSinEco
		mov ah,8 ;1 para que sea con eco
		int 21h
		ret
endp LeerTeclaSinEco

;*************************************************************************************
;****************** procedimientos implementados en clase ****************************
;*************************************************************************************

 ;F: transforma fil col de una matriz de FILCOLJUEGO columnas en posicion de vector
 ;E: filMatriz - Fila
 ;E: colMatriz - Columna
 ;S: posMatriz - Posicion en la matriz
 proc MatrizAVector
    push ax
    push bx

    mov al, filMatriz
    mov bl, FILCOLJUEGO
    mul bl
    add al, colMatriz              
    mov posMatriz, al
        
    pop bx
    pop ax
    ret
 endp MatrizAVector

 ;F: transforma una posicion de vector en el equivalente en una matriz de FILCOLJUEGO columnas (fila / columna)
 ;E: posMatriz - Posicion en la matriz
 ;S: filMatriz - Fila
 ;S: colMatriz - Columna
 proc VectorAMatriz
    push ax
    push bx
    
    xor ax, ax
    mov al, posMatriz
    mov bl, FILCOLJUEGO
    div bl                         
    mov filMatriz, al             
    mov colMatriz, ah
    
    pop bx    
    pop ax
    ret
 endp VectorAMatriz


;F: Lee una cadena y comprueba y controla que la cadena apuntada por SI tenga uno de los formatos_:
;E: SI - apuntador a la cadena de entrada cadena
;E: fil - Fila en la que iniciar la lectura de entrada
;E: col - Columna en la que iniciar la lectura de entrada
;S: AH - Formato
;S: AL - Operando del formato (F1(I,F), F3(E,S))
;S: DH - Fila del formato 1
;S: DL - Columna del formato 1
proc ValidarEntradaOperacion
        push bx
    	push cx
    	push si
        
    ValidarEntradaVolverAPedir:
        call Mensajes_BlancoLargo
           
        call ColocarCursor
        mov dx, si
        call LeerCadena
        
        cmp [si + 2] , 'I'
        je ValidarEntradaValidarFormato1
        cmp [si + 2] , 'F'
        je ValidarEntradaValidarFormato1
        cmp [si +2] , 'E'
        je ValidarEntradaValidarFormato3
        cmp [si +2] , 'S'
        je ValidarEntradaValidarFormato3
        cmp [si + 2] , 'M'
        je ValidarEntradaValidarFormato2
        jmp ValidarEntradaVolverAPedir
        
        ;1: <op1> <car><,><num> donde
        ;<op1> o 'I' o 'F'. Caracter (2)
        ;<car> 'A'..'J' (4) y <num>  '0'..'9' (6)
    ValidarEntradaValidarFormato1:
        cmp [si+3], ' '
        jne ValidarEntradaVolverAPedir
        cmp [si+5], ','
        jne ValidarEntradaVolverAPedir
        cmp [si+6], '0'
        jl ValidarEntradaVolverAPedir 
        cmp [si+6], '9'
        jg ValidarEntradaVolverAPedir
        cmp [si+4], 'A'
        jl ValidarEntradaVolverAPedir 
        cmp [si+4], 'J'
        jg ValidarEntradaVolverAPedir
        mov ah, 1
        mov al, [si+2] ; Caracter de operacion a devolver
		mov dh, [si + 4] ; Fila a devolver
        sub dh, 41h
		mov dl, [si + 6] ; Columna a devolver
        sub dl, 30h
        jmp ValidarEntradaFinValidarEntradaOperacion
       
 		;2: <op2> <cadena> donde
 		;<op2> Es 'M' y
 		;<cadena> una cadena de 18 caracteres mximo con caracteres 'N','S','E','O'.      
    ValidarEntradaValidarFormato2:
        cmp [si+3], ' '
        jne ValidarEntradaVolverAPedir
        ;TODO: Validar en bucle
        xor cx, cx
        mov cl, [si +1]
        sub cx, 2
        mov bx, 4 ; Empezamos a comparar a partir del espacio en blanco
    ValidarEntradaInicioBucle:
        cmp [si +bx], 'N'
        je ValidarEntradaFinBucle  
        cmp [si +bx], 'S'
        je ValidarEntradaFinBucle
        cmp [si +bx], 'E'
        je ValidarEntradaFinBucle
        cmp [si +bx], 'O'
        je ValidarEntradaFinBucle
        jmp ValidarEntradaVolverAPedir
    ValidarEntradaFinBucle:
        inc bx 
        loop ValidarEntradaInicioBucle
        mov ah, 2
        jmp ValidarEntradaFinValidarEntradaOperacion

		;3: <op3> donde op3 es un caracter 'E' o 'S'        
    ValidarEntradaValidarFormato3:                      
        mov ah, 3 ; Asignamos el codigo de operacion
        mov al, [si+2] ; Caracter de operacion a devolver
              
    ValidarEntradaFinValidarEntradaOperacion:
        pop si
        pop cx    
        pop bx
        ret
endp ValidarEntradaOperacion

;*************************************************************************************
;*************************     procedimientos personales    *******************************
;*************************************************************************************

;------------------------------------------------------------------------
;--------------------------------- INPUT --------------------------------
;------------------------------------------------------------------------

;F: Operaciones a ejecutar por el formato 1
;E: AL - Operando del formato (F1(I,F), F3(E,S))
;E: DH - Fila del formato 1
;E: DL - Columna del formato 1 
proc Input_OperacionesFormato1 
        push ax
        push bx
        push si
        
        ; Buscamos la posicion que vamos a seleccionar
        mov filMatriz, dh
        mov colMatriz, dl
        call MatrizAVector
        lea bx, [tableroJuego]
        add bl, posMatriz
        mov si, bx


        cmp al, 'I'
        je OperacionesFormato1Inicio 
        
        ;En caso de que seleccionemos destino
        cmp [si], 0 ;Comprobamos si existe o no
        jne OperacionesFormato1MostrarErrorFichaFin
        call Pantalla_ActualizarFichaFin
        mov [fichasMov + 2], dh
        mov [fichasMov + 3], dl
        jmp OperacionesFormato1Fin
    OperacionesFormato1MostrarErrorFichaFin:
        call Mensajes_FichaFin
        jmp OperacionesFormato1Fin
        
        ; En caso de que la ficha sea la de inicio
    OperacionesFormato1Inicio:
        cmp [si], 0 ;Comprobamos si existe o no
        je OperacionesFormato1MostrarErrorFichaInicio
        call Pantalla_ActualizarFichaInicio 
        mov [fichasMov], dh
        mov [fichasMov + 1], dl
        jmp OperacionesFormato1Fin
    OperacionesFormato1MostrarErrorFichaInicio:
        call Mensajes_FichaInicio
        jmp OperacionesFormato1Fin
        
    OperacionesFormato1Fin:
        pop si  
        pop bx  
        pop ax
        ret
endp Input_OperacionesFormato1

;F: Operaciones a ejecutar por el formato 2
;E: SI - Cadena leida
;S: AH - 1 si hay error en el formato, 0 si es correcto
;S: AL - 1 si ha terminado partida, 0 si continua
proc Input_OperacionesFormato2
        push bx


        ; Validamos si el camino introducido es correcto
        call Input_ValidarFormato2
        mov bl, ah ;Guardamos temporalmente AH en BL
        mov al, 0
        cmp ah, 1
        je OperacionesFormato2Fin

        ; Mover la ficha de sitio
        call Tablero_MoverFichaOrigenDestino

        ;Comprobamos si hay que borrar la fila
        call Tablero_ComprobarFila
        cmp ah, 1 
        je OperacionesFormato2_NoComprobarColumna
        ;Si no se ha borrado fila, comprobamos la columna
        call Tablero_ComprobarColumna
    OperacionesFormato2_NoComprobarColumna:

        call Tablero_AccionesEnCualquierCaso
        cmp ah, 0 ;Comprobamos si ha habido acierto o fallo al eliminar fichas
        je OperacionesFormato2_NoSeHaBorrado
        call Tablero_AccionesEnCasoDeAcierto
        jmp OperacionesFormato2Fin
    OperacionesFormato2_NoSeHaBorrado:
        call Tablero_AccionesEnCasoDeFallo

    OperacionesFormato2Fin:
        mov ah, bl
        pop bx
        ret
endp Input_OperacionesFormato2

;F: Valida si el formato 2 introducido cumple todas las condiciones apra ser valido
;E: SI - Cadena leida
;S: AH - 0 si es correcto, 1 si hay error
proc Input_ValidarFormato2
        push cx
        push si

        ; Colocamos en fil y col el inicio
        ; Si no hay ficha de inicio, mostrarmos un error
        mov al, [fichasmov]
        cmp al, -1
        je ValidarFormato2ErrorMoverIF
        mov fil, al
        mov al, [fichasmov+1]
        cmp al, -1
        je ValidarFormato2ErrorMoverIF
        mov col, al
        ; Comprobamos que exista la ficha del final
        mov al, [fichasmov+2]
        cmp al, -1
        je ValidarFormato2ErrorMoverIF
        mov al, [fichasmov+3]
        cmp al, -1
        je ValidarFormato2ErrorMoverIF
        
        xor cx, cx
        ; Empezamos la validacion del camino seleccionado
        mov cl, [si + 1] ;Ponemos numero de letras leidas
        sub cx, 2
        add si, 4
    ValidarFormato2InicioLoop:
        ; Modificamos la fila o columna
        cmp [si], 'N'
        je ValidarFormato2DecrementarFila
        cmp [si], 'S'
        je ValidarFormato2IncrementarFila
        cmp [si], 'E'
        je ValidarFormato2IncrementarColumna
        cmp [si], 'O'
        je ValidarFormato2DecrementarColumna

    ValidarFormato2IncrementarFila:
        inc fil
        cmp fil, FILCOLJUEGO
        jg ValidarFormato2ErrorRango
        jmp ValidarFormato2FinContinuar
    ValidarFormato2IncrementarColumna:
        inc col
        cmp col, FILCOLJUEGO
        jg ValidarFormato2ErrorRango
        jmp ValidarFormato2FinContinuar
    ValidarFormato2DecrementarFila:
        dec fil
        cmp fil, 0
        jl ValidarFormato2ErrorRango
        jmp ValidarFormato2FinContinuar
    ValidarFormato2DecrementarColumna:
        dec col
        cmp col, 0
        jl ValidarFormato2ErrorRango
        jmp ValidarFormato2FinContinuar

    ValidarFormato2FinContinuar:

        mov ah, [fil]
        mov [filmatriz], ah
        ; Comprobamos si no es fila de origen
        cmp ah, [fichasmov]
        je ValidarFormato2_EsFilaOrigen
        jmp ValidarFormato2_NoEsFilaOrigen
    ValidarFormato2_EsFilaOrigen:
        mov ah, [col]
        cmp ah, [fichasmov + 1]
        je ValidarFormato2_ContinuarLoop

    ValidarFormato2_NoEsFilaOrigen:
        mov ah, [col]
        mov [colmatriz], ah
        call Tablero_CargarFicha

    ValidarFormato2_ValidarNoPosicionOrigen:
        cmp al, 0
        jne ValidarFormato2ErrorCeldaFicha
    ValidarFormato2_ContinuarLoop:
        inc si
        loop ValidarFormato2InicioLoop ;Comprueba si la ruta esta vacia
        
        ;Comprobamos si fil y col han acabado en la posicion final
        mov al, fil
        mov ah, [fichasmov + 2]
        cmp al, ah
        jne ValidarFormato2ErrorMoverIF
        mov al, col
        mov ah, [fichasmov+3]
        cmp al, ah
        jne ValidarFormato2ErrorMoverIF

        ; Si todo ha ido bien, acabamos poniendo ah a 0 para indicarlo y saltando al final
        mov ah, 0
        jmp ValidarFormato2Fin

    ValidarFormato2ErrorMoverIF:
        call Mensajes_MoverIF
        mov ah, 1
        jmp ValidarFormato2Fin

    ValidarFormato2ErrorRango:
        call Mensajes_Rango
        mov ah, 1
        jmp ValidarFormato2Fin

    ValidarFormato2ErrorCeldaFicha:
        ;DEFENSA: No esta mal si es la posicion origen

        call Mensajes_CeldaFicha
        mov ah, 1
        jmp ValidarFormato2Fin

    ValidarFormato2Fin:
        pop si
        pop cx
        ret
endp Input_ValidarFormato2

;F: Coloca los mensajes, lee y valida la entrada
;S: SI - Inicio de la cadena leida
;S: AH - Formato
;S: AL - Operando del formato (F1(I,F), F3(E,S))
;S: DH - Fila del formato 1
;S: DL - Columna del formato 1
proc Input_PedirEntrada
        
        mov fil, FILMSG
        mov col, COLMSG
        call ColocarCursor
        lea dx, msgIntOp
        call ImprimirCadena
        
        lea si, cadenaE
        mov fil, FILENTRADA
        mov col, COLENTRADA
        
 
        call ValidarEntradaOperacion
        
        ret
endp Input_PedirEntrada

;------------------------------------------------------------------------
;-------------------------------- TABLERO -------------------------------
;------------------------------------------------------------------------

;F: Mueve la ficha en la posicion de origen a la posicion de destino
proc Tablero_MoverFichaOrigenDestino
        push ax
        push bx
        push si

        ; Actualizamos los valores de tableroJuego y repintamos
        xor bx, bx
        ; Cargamos la posicion de origen en filMatriz, colMatriz, fil y col
        mov bl, [fichasmov] 
        mov [filmatriz], bl
        mov [fil], bl
        mov bl, [fichasmov+1]
        mov [colmatriz], bl
        mov [col], bl
        call MATRIZAVECTOR ; Calculamos su posicion en el vector
        mov bl, [posmatriz]
        mov al, [tablerojuego + bx] ; Guardamos el valor de origen
    ; Actualizamos su valor logico
        mov [tablerojuego + bx], 0 
    ; Actualizamos su valor grafico
        call Pantalla_ColocarCursorEnTablero
        mov bl, 0
        call Pantalla_PintarFicha

        ;Cargamos la posicion de destino en filMatriz, colMatriz, fil y col y repintamos
        mov bl, [fichasmov+2] ; Cargamos la posicion destino
        mov [filmatriz], bl
        mov [fil], bl
        mov bl, [fichasmov+3]
        mov [colmatriz], bl
        mov [col], bl
        call MATRIZAVECTOR
        mov bl, [posmatriz]
    ; Actualizamos su valor logico
        mov [tablerojuego + bx], al
    ; Actualizamos su valor grafico
        call Pantalla_ColocarCursorEnTablero
        mov bl, al
        mov bl, [colorfic + bx]
        call Pantalla_PintarFicha

        pop si
        pop bx
        pop ax
        ret
endp Tablero_MoverFichaOrigenDestino

;F: Devuelve los datos de la ficha en la fil y col actual
;E: filMatriz - Fila de la matriz
;E: colMatriz - Columna de la matriz
;S: AL - Valor de la ficha
;S: posMatriz - Posicion dentro del vector
proc Tablero_CargarFicha
        push bx

        call MatrizAVector
        mov bl, posMatriz
        mov al, [tablerojuego + bx]

        pop bx
        ret
endp Tablero_CargarFicha

;F: Acciones comunes al acierto o fallo
proc Tablero_AccionesEnCualquierCaso; Ponemos en blanco la ficha de origen
        mov [fil], FILMDE
        mov [col], COLMDE
        call Mensajes_BlancoCorto
        ; Ponemos en blanco la ficha de destino
        mov [fil], FILMA
        mov [col], COLMA
        call Mensajes_BlancoCorto
        ; Ponemos los valores del vector de fichas seleccionadas a -1
        mov [fichasmov], -1
        mov [fichasmov+1], -1
        mov [fichasmov+2], -1
        mov [fichasmov+3], -1
        ret
endp Tablero_AccionesEnCualquierCaso

;F: Acciones en caso de que se elimine una fila o columna
;S: AL - 1 si el juego ha terminado. 0 si continua
proc Tablero_AccionesEnCasoDeAcierto
        mov al, 0
        add [puntos], PUNTOSPORACIERTO
        call Pantalla_ActualizarPuntuacion

        cmp [puntos], PUNTFIN
        jl Tablero_AccionesEnCasoDeAcierto_Fin
        call Mensajes_PartidaGanada
        mov al, 1

    Tablero_AccionesEnCasoDeAcierto_Fin:
        ret
endp Tablero_AccionesEnCasoDeAcierto

;F: Acciones en caso de que no se elimine una fila o columna
;S: AL - 1 si ha terminado partida, 0 si continua
proc Tablero_AccionesEnCasoDeFallo
        call Tablero_AniadirFichasSiguientes
        mov al, TOTALCELDAS ;Utilizamos AL como auxiliar para comprobar si quedan huecos libres
        sub al, [ficentablero]
        cmp al, 0
        jle Tablero_AccionesEnCasoDeFallo_TerminarPartida
        mov al, 0
        jmp Tablero_AccionesEnCasoDeFallo_Fin

    Tablero_AccionesEnCasoDeFallo_TerminarPartida:
        call Mensajes_PartidaPerdida
        mov al, 1

    Tablero_AccionesEnCasoDeFallo_Fin:
        ret
endp Tablero_AccionesEnCasoDeFallo

;F: Aï¿½ade al tablero el numero de fichas siguientes necesario y las actualiza
proc Tablero_AniadirFichasSiguientes
        push ax
        push bx
        push cx
        push dx

        ;Guardamos en AL el numero de huecos en el tablero
        mov dl, TOTALCELDAS
        sub dl, [ficentablero]
        ;Guardamos en DL el numero de huecos en el tablero

        xor bx, bx ; Contador de posicion
        mov cx, FICSIGUIENTES
    Tablero_AniadirFichasSiguientes_LoopPintarFichas:
        cmp dl, 0 ;Si no queda hueco, no aï¿½adimos
        jle Tablero_AniadirFichasSiguientes_Fin

        call SiguienteFicha_GetSiguiente
        mov dh, al ; Guardamos en DH el color de la ficha

        call Tablero_CargarPosicionAleatoria
        mov bl, al ;Guardamos en BL la posicion del vector
        mov [posmatriz], bl

        ;Colocamos el valor en el vector
        mov [tablerojuego + bx], dh

        ;Pintamos el color en la pantalla
        call VectorAMatriz
        mov bl, [filmatriz]
        mov [fil], bl
        mov bl, [colmatriz]
        mov [col], bl
        call Pantalla_ColocarCursorEnTablero
        mov bl, dh
        mov bl, [colorfic + bx]
        call Pantalla_PintarFicha

        ;Aï¿½adimos ficha al final de siguienteFic
        call SiguienteFicha_AniadirAlFinal

        ;Modificamos contadores de fichas
        dec dl
        inc [ficentablero]

        loop Tablero_AniadirFichasSiguientes_LoopPintarFichas

    Tablero_AniadirFichasSiguientes_Fin:

        pop dx
        pop cx
        pop bx
        pop ax
        ret
endp Tablero_AniadirFichasSiguientes

;F: Devuelve una posicion aleatoria vacia del tablero
;S: AL - Posicion del tablero vacia
proc Tablero_CargarPosicionAleatoria
        push bx

        xor bh, bh
    Tablero_CargarPosicionAleatoria_GenerarAleatorio:
        mov bl, TOTALCELDAS -1
        call NumAleatorio
        mov bl, ah
        cmp [tablerojuego + bx], 0
        jne Tablero_CargarPosicionAleatoria_GenerarAleatorio
        mov al, bl

        pop bx
        ret
endp Tablero_CargarPosicionAleatoria

;F: Comprueba la fila de final de posicion y elimina las fichas si hubiese 5 seguidas
;S: AH - 1 si ha borrado, 0 si no
proc Tablero_ComprobarFila
        push bx
        push cx
        push dx
        mov dh, al
        
        mov al, [fichasmov+ 2]
        mov [filmatriz], al
        mov al, [fichasmov+ 3]
        mov [colmatriz], al
        call Tablero_CargarFicha ; Cargamos en valor de la ficha movida en AL
        
        mov [colmatriz], 0
        call MATRIZAVECTOR
        xor bh, bh
        mov bl, [posmatriz] ; Utilizamos BL con offset inicial de la fila
        xor ah, ah ; Inicializamos AH con valor 0

        ; AH - Ficha original
        ; AL - Contador de ocurrencias seguidas
        ; BX - Offset en el tablero
        ; DL - Valor temporal de la ficha actual a comparar
        mov cx, FILCOLJUEGO
    ComprobarFilaInicioLoop:
        mov dl, [tablerojuego + bx]
        cmp dl, al
        je ComprobarFilaIncrementarContador
        mov ah, 0 ; Si no ha habido coincidencia, reseteamos el contador
        jmp ComprobarFilaContinuarLoop

    ComprobarFilaIncrementarContador:
        inc ah ; Incrementamos el contador de ocurrencias seguidas
        cmp ah, FICIGUALES ; Si ha habido FICIGUALES ocurrencias seguidas, pasamos a eliminar la fila
        je ComprobarFilaEliminarFila

    ComprobarFilaContinuarLoop:
        inc bx ;Incrementamos BX ya que esta en la misma fila
        loop ComprobarFilaInicioLoop
        mov ah, 0
        jmp ComprobarFilaFin
        
    ComprobarFilaEliminarFila:
        call Mensajes_EliminandoFila
        ; Actualizamos el valor logico del tablero
        mov [tablerojuego + bx], 0
        ; Actualizamos el valor grafico del tablero
        mov [posmatriz], bl
        call VECTORAMATRIZ
        mov al, [filmatriz]
        mov [fil], al
        mov al, [colmatriz]
        mov [col], al    
        call Pantalla_ColocarCursorEnTablero
        push bx ;PUSH de bx para poder utilizarlo como valor para pintar ficha
        mov bl, 0
        call Pantalla_PintarFicha
        pop bx ;POP de BX para volver a utilizarlo como indice del vector
        ; Decrementamos la posicion de la columna
        dec bx
        ; Decrementamos el contador de elementos borrados
        dec ah
        cmp ah, 0
        jne ComprobarFilaEliminarFila
        mov ah, 1
        sub [ficentablero], FICIGUALES
        
    ComprobarFilaFin:
        mov al, dh
        pop dx
        pop cx
        pop bx
        ret
endp Tablero_ComprobarFila

;F: Comprueba la columna de final de posicion y elimina las fichas si hubiese 5 seguidas
;S: AH - 1 si ha borrado, 0 si no
proc Tablero_ComprobarColumna
        push bx
        push cx
        push dx
        mov dh, al

        
        mov al, [fichasmov+ 2]
        mov [filmatriz], al
        mov al, [fichasmov+ 3]
        mov [colmatriz], al
        call Tablero_CargarFicha ; Cargamos en valor de la ficha movida en AL
        
        mov [filmatriz], 0
        call MATRIZAVECTOR
        xor bh, bh
        mov bl, [posmatriz] ; Utilizamos BL con offset inicial de la columna
        xor ah, ah ; Inicializamos AH con valor 0

        ; AH - Ficha original
        ; AL - Contador de ocurrencias seguidas
        ; BX - Offset en el tablero
        ; DL - Valor temporal de la ficha actual a comparar
        mov cx, FILCOLJUEGO
    ComprobarColumnaInicioLoop:
        mov dl, [tablerojuego + bx]
        cmp dl, al
        je ComprobarColumnaIncrementarContador
        mov ah, 0 ; Si no ha habido coincidencia, reseteamos el contador
        jmp ComprobarColumnaContinuarLoop

    ComprobarColumnaIncrementarContador:
        inc ah ; Incrementamos el contador de ocurrencias seguidas
        cmp ah, FICIGUALES ; Si ha habido FICIGUALES ocurrencias seguidas, pasamos a eliminar la fila
        je ComprobarColumnaEliminar

    ComprobarColumnaContinuarLoop:
        add bx, FILCOLJUEGO ;Incrementamos BX una fila entera
        loop ComprobarColumnaInicioLoop
        mov ah, 0
        jmp ComprobarColumnaFin
        
    ComprobarColumnaEliminar:
        call Mensajes_EliminandoColumna
        ; Actualizamos el valor logico del tablero
        mov [tablerojuego + bx], 0
        ; Actualizamos el valor grafico del tablero
        mov [posmatriz], bl
        call VECTORAMATRIZ
        mov al, [filmatriz]
        mov [fil], al
        mov al, [colmatriz]
        mov [col], al    
        call Pantalla_ColocarCursorEnTablero
        push bx ;PUSH de bx para poder utilizarlo como valor para pintar ficha
        mov bl, 0
        call Pantalla_PintarFicha
        pop bx ;POP de BX para volver a utilizarlo como indice del vector
        ; Decrementamos la posicion de la columna
        sub bx, FILCOLJUEGO
        ; Decrementamos el contador de elementos borrados
        dec ah
        cmp ah, 0
        jne ComprobarColumnaEliminar
        mov ah, 1
        sub [ficentablero], FICIGUALES
        
    ComprobarColumnaFin:
        mov al, dh
        pop dx
        pop cx
        pop bx
        ret
endp Tablero_ComprobarColumna

;------------------------------------------------------------------------
;-------------------------------- PANTALLA ------------------------------
;------------------------------------------------------------------------

;F: Coloca el cursor en la posicion correspondiente en el tablero de la consola
;E: FIL - Fila en la que colocar el puntero
;E: COL - Columna en la que colocar el puntero
;S: FIL - Posicion correspondiente en la pantalla
;S: COL - Posicion correspondiente en la pantalla
proc Pantalla_ColocarCursorEnTablero
        push ax
        push bx

        mov bl, 2 ; Distancia entre posiciones del tablero

        xor ax, ax
        mov al, fil
        mul bl ; Adaptamos la posicion de origen a la posicion en la pantalla
        mov fil, al
        add fil, FILFA

        xor ax, ax
        mov al, col
        mul bl 
        mov col, al
        add col, COLC0
        call ColocarCursor

        pop bx
        pop ax
        ret
endp Pantalla_ColocarCursorEnTablero

;F: Pinta un caracter de un color especifico en la posicion actual del puntero. Si BL es 0, borra la ficha
;E: BL - Codigo de color
proc Pantalla_PintarFicha
        push ax
        push bx
        push cx

        cmp bl, 0
        je PintarFichaCARNOFICHA
        mov al, CARFICHA
        jmp PINTARFICHAPROCEDER
    
    PintarFichaCARNOFICHA:
        mov al, CARNOFICHA
    
    PintarFichaProceder:
        mov ah, 9
        mov bh, 0
        mov cx, 1
        int 10h
        
        pop cx
        pop bx 
        pop ax
        ret
endp Pantalla_PintarFicha

;F: Borra la pantalla (la deja en negro)
proc Pantalla_Borrar
	    push ax
	    push bx
	    push cx
	    push dx

	    mov ah, 6h
	    xor al, al
	    mov bh, 7
	    xor cx, cx
	    mov dh, 24
	    mov dl, 79
    	int 10h

    	pop dx
    	pop cx
    	pop bx
    	pop ax
        ret
endp Pantalla_Borrar

;F: Actualiza las fichas del tablero actual
proc Pantalla_ActualizarTablero
        push ax
        push cx
        push si
        
        call Mensajes_ColocandoFichas
        
        mov fil, FILFA
        mov col, COLC0
        call ColocarCursor
                             
    ;array a pintar tableroJuego
    ;Celdas totles TOTALCELDAS
    ;Numero filas o columnas FILCOLJUEGO
        lea si, tableroJuego
        mov cx, FILCOLJUEGO
    ActualizarTableroBuclePasarColumna:
        
        push cx
        mov cx, FILCOLJUEGO
    ActualizarTableroPintarFila:
        xor bh, bh
        mov bl, [si] ; Cargamos el color a pintar
        cmp bl, 0 ;Comprobamos si hay que pintarlo
        je ActualizarTableroContinuar
        call ColocarCursor
        mov bl, [colorFic + bx]
        call Pantalla_PintarFicha
    ActualizarTableroContinuar:    
        inc si
        add col, 2
        loop ActualizarTableroPintarFila
        
        add fil, 2
        mov col, COLC0 
        pop cx
        loop ActualizarTableroBuclePasarColumna
        
        pop si
        pop cx
        pop ax              
        ret                     
endp Pantalla_ActualizarTablero

;F: Actualiza la puntuacion actual
proc Pantalla_ActualizarPuntuacion
        push ax
        push dx
        
        mov fil, FILPUNT
        mov col, COLPUNT
        call ColocarCursor
        
        xor ax, ax
        mov al, puntos
        lea dx, msgPuntos
        call NumeroACadena
        call ImprimirCadena
        
        pop dx
        pop ax
        ret
endp Pantalla_ActualizarPuntuacion

;F: Actualiza el valor de la ficha de inicio
proc Pantalla_ActualizarFichaInicio
        push dx
        push si  
        
        mov fil, FILMDE
        mov col, COLMDE
        call ColocarCursor
        lea si, cadenaE
        add si, 4
        mov [si+3], '$'
        mov dx, si
        call ImprimirCadena
        
        pop si
        pop dx
        ret
endp Pantalla_ActualizarFichaInicio

;F: Actualiza el valor de la ficha de fin
proc Pantalla_ActualizarFichaFin
        push dx
        push si  
        
        mov fil, FILMA
        mov col, COLMA
        call ColocarCursor
        lea si, cadenaE
        add si, 4
        mov [si+3], '$'
        mov dx, si
        call ImprimirCadena
        
        pop si
        pop dx
        ret
endp Pantalla_ActualizarFichaFin

;------------------------------------------------------------------------
;---------------------------- SIGUIENTES FICHAS -------------------------
;------------------------------------------------------------------------

;F: Devuelve la ficha en primera posicion del vector y genera una al final moviendo las intermedias
;S: AL - Color de la siguiente ficha
proc SiguienteFicha_GetSiguiente
        push cx
        push si

        lea si, [siguientefic]
        mov cx, FICSIGUIENTES
        call Utilidad_DevolverPrimeroDeVectorYMover

        pop si
        pop cx
        ret
endp SiguienteFicha_GetSiguiente

;F: Aï¿½ade una ficha aleatoria al final de SiguienteFicha y actualiza su grafica
proc SiguienteFicha_AniadirAlFinal
        push cx
        push dx
        push si

        lea si, [siguientefic]
        add si, FICSIGUIENTES
        sub si, 1
        mov dh, TIPOSFIC
        mov cx, 1
        call Utilidad_RellenarDeAleatorios

        call SiguienteFicha_ActualizarGraficos

        pop si
        pop dx
        pop cx
        ret
endp SiguienteFicha_AniadirAlFinal

;F: Actualiza las fichas siguientes
proc SiguienteFicha_ActualizarGraficos
        push ax
        push bx
        push cx
        push dx
        push si
        
        mov fil, FILSIGS
        mov col, COLSIGS
        call ColocarCursor
        
        lea dx, msgBlancoCorto
        call ImprimirCadena
        call ColocarCursor
        
        xor bh, bh
        lea si, SiguienteFic
        mov cx, FICSIGUIENTES 
    SiguienteFicha_ActualizarGraficos_PintarFicha:
        mov bl, [si]
        mov bl, [colorfic+bx]
        call Pantalla_PintarFicha
        inc si
        add col, 2
        call ColocarCursor
        loop SiguienteFicha_ActualizarGraficos_PintarFicha
        
        pop si
        pop dx
        pop cx
        pop bx
        pop ax
        ret
endp SiguienteFicha_ActualizarGraficos

;F: Inicializa el vector de fichas siguientes y lo pinta en pantalla
proc SiguienteFicha_Inicializar
        push cx
        push dx
        push si

        lea si, SiguienteFic
        mov cx, FICSIGUIENTES 
        mov dh, TIPOSFIC
        call Utilidad_RellenarDeAleatorios
        call SiguienteFicha_ActualizarGraficos
    
        pop si
        pop dx
        pop cx
        ret
endp SiguienteFicha_Inicializar


;------------------------------------------------------------------------
;------------------------------- UTILIDADES -----------------------------
;------------------------------------------------------------------------

;F: Devuelve el primer elemento del vector y mueve el resto hacia delante 1 posicion.
;E: CX - Numero de elementos del vector
;E: SI - Posicion de inicio del vector
;S: AL - Color de la siguiente ficha
proc Utilidad_DevolverPrimeroDeVectorYMover
    push bx
    push dx

    mov al, [si] ; Guardamos la primera posicion del vector a devolver

    xor bx, bx; Contador
    sub cx, 1 ;Decrementamos para no salirnos del vector
    Utilidad_DevolverPrimeroDeVectorYMover_Loop:
        inc bx
        mov dl, [si + bx] ;Cargamos el elemento siguiente
        dec bx
        mov [si + bx], dl ;Guardamos en la posicion actual
        inc bx
    loop Utilidad_DevolverPrimeroDeVectorYMover_Loop
    
    pop dx
    pop bx
    ret
endp Utilidad_DevolverPrimeroDeVectorYMover

;F: Asigna CX valores aleatorios en el rango 0-DH del vector con valores entre 0 y DL 
;E: SI - Direccion del vector
;E: CX - Numero de aleatorios a generar
;E: DH - Rango de aleatorios a introducir
;E: DL - Rango maximo del aleatorio generado 
proc Utilidad_RellenarAleatoriamente
        push ax
        push bx
		
		;dec dl ; Decrementamos DL y sumamos 1 al generado para que el rango sea 1-limite
	RellenarVectorInicio:
	    mov bl, dl
    	call NumAleatorio
    	mov al, ah
		xor ah, ah
		inc ax
		push ax
		
		xor bh, bh
	RellenarVectorGenerarRango:
		mov bl, dh ; Sustituimos el rango maximo de generacion de aleatorio
		call NumAleatorio
		mov bl, ah ; Ya tenemos el numero en BX
		cmp [si + bx], 0 ; Comprobamos si la direccion aleatoria esta vacia
		jne RellenarVectorGenerarRango
		
		pop ax
   		mov [si + bx], al ; Guardamos el numero aleatorio en la posicion aleatoria
    	loop RellenarVectorInicio ; Repetimos cx veces
    	
    	pop bx
    	pop ax
    	ret 
endp Utilidad_RellenarAleatoriamente

;F: Rellena un vector con caracteres aleatorios entre 1-rango 
;E: SI - Direccion del vector
;E: CX - Rango del vector
;E: DH - Rango de aleatorios a introducir 
proc Utilidad_RellenarDeAleatorios
        push bx
        push ax  
        
        xor ah, ah
        dec dh ;Decrementamos por el rango         
	    mov bl, dh
    RellenarVectorDeAleatoriosInicio:
    	call NumAleatorio
    	mov al, ah
    	xor ah, ah   	
    	inc al ;Ajustamos el generado al rango
    	mov [si], al  
        inc si
        loop RellenarVectorDeAleatoriosInicio
        
        pop ax
        pop bx
        ret
endp Utilidad_RellenarDeAleatorios        

;F: Copia un vector a otro
;E: SI - Primera posicion de memoria del origen
;E: DI - Primera posicion de memoria del origen destino
;E: CX - Numero de elementos a copiar
proc Utilidad_CopiarVector
		push ax
		push si
		push di
		
	loopCopiarVector:
		mov ax, [si]
		mov [di], ax
		inc si
		inc di
		loop loopCopiarVector

		pop di
		pop si
		pop ax
		ret
endp Utilidad_CopiarVector

;------------------------------------------------------------------------
;--------------------------- METODOS DE MENSAJES ------------------------
;------------------------------------------------------------------------

;F: Escribe la cadena msgBlancoCorto en fil y col
;E: FIL - Fila en la que empezar a escribir
;C: COL - Columna en la que empezar a escribir
proc Mensajes_BlancoCorto
        push dx
        call ColocarCursor
        lea dx, msgBlancoCorto
        call ImprimirCadena
        pop dx
        ret
endp Mensajes_BlancoCorto

;F: Escribe la cadena msgBlancoLargo en fil y col
;E: FIL - Fila en la que empezar a escribir
;C: COL - Columna en la que empezar a escribir
proc Mensajes_BlancoLargo
        push dx
        call ColocarCursor
        lea dx, msgBlancoLargo
        call ImprimirCadena
        pop dx
        ret
endp Mensajes_BlancoLargo

;F: Muestra un mensaje debajo del tablero
;E: DX - Inicio de cadena
proc Mensajes_Imprimir
        push ax
        mov al, [fil]
        mov ah, [col]

        mov fil, FILENTRADA
        mov col, COLENTRADA
        call ColocarCursor
        call ImprimirCadena

        mov [col], ah
        mov [fil], al
        pop ax
        ret
endp Mensajes_Imprimir

;F: Muestra en posicion el mensaje de colocar fichas
proc Mensajes_ColocandoFichas
        push dx
        lea dx, msgInicT
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_ColocandoFichas

;F: Muestra msgPartidaGanada
proc Mensajes_PartidaGanada
        push dx
        lea dx, msgPartidaGanada
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_PartidaGanada

;F: Muestra msgPartidaGanada
proc Mensajes_PartidaPerdida
        push dx
        lea dx, msgPartidaPerdida
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_PartidaPerdida

;F: Muestra msgPartidaFinalizada
proc Mensajes_PartidaFinalizada
        push dx
        lea dx, msgPartidaFinalizada
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_PartidaFinalizada

;F: Muestra en pantalla que ha habido un error al seleccionar la ficha de fin
proc Mensajes_FichaFin
        push dx
        lea dx, msgErrFinal
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_FichaFin

;F: Muestra en pantalla que ha habido un error al seleccionar la ficha de inicio
proc Mensajes_FichaInicio
        push dx
        lea dx, msgErrInicio
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_FichaInicio

;F: Muestra en pantalla que el camino elegido se sale del tablero
proc Mensajes_Rango
        push dx
        lea dx, msgErrRango
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_Rango

;F: Muestra en pantalla que no se ha seleccionado origen y destino
proc Mensajes_MoverIF
        push dx
        lea dx, msgErMoverIF
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_Rango

;F: Muestra en pantalla que el camino pasa por una ficha ocupada
proc Mensajes_CeldaFicha
        push dx
        lea dx, msgErrCeldaFicha
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_CeldaFicha

;F: Muestra en pantalla el mensaje de que se esta eliminando una fila
proc Mensajes_EliminandoFila
        push dx
        lea dx, msgAgrEnFila
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_EliminandoFila

;F: Muestra en pantalla el mensaje de que se esta eliminando una fila
proc Mensajes_EliminandoColumna
        push dx
        lea dx, msgAgrEnCol
        call Mensajes_Imprimir
        pop dx
        ret
endp Mensajes_EliminandoColumna

;------------------------------------------------------------------------
;--------------------------- LOGICA PRINCIPAL ---------------------------
;------------------------------------------------------------------------

;F: Logica de inicio del juego. Independiente a la de inicio del programa para ser reseteado con facilidad
proc Juego
        push ax
        push bx
        push cx
        push dx
        push di
        push si

        ;Pintamos la pantalla de inicio
		call Juego_Inicio

		;Comprobamos si debug o normal
		cmp al, 'S'
		je mainIniciarDebug
		
		;Colocamos el cursor para mostrar mensaje de cambio de pantalla
		mov fil, FILMODO
		mov col, 0
		call ColocarCursor
	
		;Cargamos valores iniciales aleatorios
		lea dx, msgGenAleat
		call ImprimirCadena         
		
		lea si, tablerojuego
		mov cx, FICINICIAL
		mov dh, TOTALCELDAS - 1
		mov dl, TIPOSFIC
		call Utilidad_RellenarAleatoriamente
        mov [ficentablero], FICINICIAL
		jmp mainEjecutarPantallaJuego

    mainIniciarDebug:
		lea dx, msgGenDebug
		call ImprimirCadena
        ;Cargamos valores de debug
		lea si, arcadeDebug
		lea di, tablerojuego
		mov cx, TOTALCELDAS
		call Utilidad_CopiarVector 
        mov [ficentablero], FICINICIAL

	mainEjecutarPantallaJuego:
		call Juego_Tablero
        pop si
        pop di
        pop dx
        pop cx
        pop bx
        pop ax
        ret
endp Juego

;F: Pinta la pantalla de inicio y comprueba el modo de inicio
;S: AL - Modo de inicio del juego (S=Debug, N=Aleatorio)
proc Juego_Inicio
        push dx
    
    
		mov fil, 0
		mov col, 0
		call ColocarCursor
		lea dx, pantallaInicio
		call ImprimirCadena ;Mostramos el titulo
		
		mov fil, FILMODO
		mov col, 0
		call ColocarCursor
		lea dx, msgDebug
		call ImprimirCadena ;Mostramos  el mensaje de debug

	pantallaInicioPedirDebug:
	    mov fil, FILMODO
		mov col, COLMODO
		call ColocarCursor
		lea dx, cadenaE
		call LeerTeclaSinEco
		
		cmp al, 'S'
		je pantallaInicioFin
		cmp al, 'N'
		je pantallaInicioFin
		jmp pantallaInicioPedirDebug
	pantallaInicioFin:
        
        pop dx
		ret
endp Juego_Inicio

;F: Pinta la pantalla de juego principal y rellena con los valores iniciales
proc Juego_Tablero
        push ax
        push cx
        push dx
        push si
        
        call Pantalla_Borrar 
    
        mov fil, 0
        mov col, 0
        call ColocarCursor
        lea dx, pantTablero
        call ImprimirCadena
        
        call Pantalla_ActualizarTablero
        call Pantalla_ActualizarPuntuacion
        
        ; Creamos las fichas aleatorias iniciales
        call SiguienteFicha_Inicializar
        
        ;Aqui empezaremos la interaccion con el usuario
    EjecutarPantallaJuegoPedirEntrada:
        call Input_PedirEntrada
        cmp ah, 1
        je EjecutarPantallaJuegoFormato1
        cmp ah, 2
        je EjecutarPantallaJuegoFormato2
        
        ; Ejecutamos acciones del formato 3
        cmp al, 'S'
        je EjecutarPantallaJuegoSalir
        call Juego_ResetearDatos
        call Juego
        jmp EjecutarPantallaJuegoFin
    EjecutarPantallaJuegoFormato1:
        call Input_OperacionesFormato1
        jmp EjecutarPantallaJuegoPedirEntrada
    EjecutarPantallaJuegoFormato2:
        call Input_OperacionesFormato2
        cmp al, 1 ;En caso de que se haya terminado la partida, salimos
        je EjecutarPantallaJuegoFin
        jmp EjecutarPantallaJuegoPedirEntrada
        
    EjecutarPantallaJuegoSalir:
        call Mensajes_PartidaFinalizada

    EjecutarPantallaJuegoFin:
        pop si
        pop dx
        pop cx
        pop ax
        ret	
endp Juego_Tablero

;F: Resetea el estado del juego para empezar una nueva partida
proc Juego_ResetearDatos
        ; Limpiamos los valores por defecto
        mov [puntos], 0
        mov [siguientefic], -1
        mov [siguientefic+1], -1
        mov [siguientefic+2], -1
        mov [siguientefic+3], -1
        mov [ficentablero], 0
        mov cx, TOTALCELDAS
        mov bx, 0
        ; Limpiamos el tablero
    ResetearDatos_TableroJuegoLoop:
        mov [tablerojuego + bx], 0
        inc bx
        loop ResetearDatos_TableroJuegoLoop
        ;Limpiamos la pantalla
        call Pantalla_Borrar

endp Juego_ResetearDatos

;------------------------------------------------------------------------
;--------------------------------- MAIN ---------------------------------
;------------------------------------------------------------------------

start:
		mov ax, data
		mov ds, ax
    
		call Juego
           
		mov ah, 4ch
		int 21h
		ends
end start