package organicwaves.truthtables

import android.util.Log

// This class transforms any expression in infix notation to postfix notation

class ChangeNotation {

    private var expression:String = ""

    constructor(expression:String){
        this.expression = expression
    }

    // This defines the hierarchy operator
    private fun getHierarchyOperator(operator:Char):Int {
        when(operator){
            '~' -> return 5
            '⊕' -> return 4
            'X' -> return 4
            '·' -> return 3
            '&' -> return 3
            '+' -> return 2
            'N' -> return 2
        }
        return -1
    }

    // This converts a infix expression into a postfix notation
    fun getPostfixNotation():String{
        var charStack = Stack()
        var postfixExpression:String = ""
        for(i in 0 until expression.length){
            when(expression[i]) {
                '(' -> charStack.push(expression[i])
                ')' -> {
                    while(!charStack.isEmpty() && charStack.top() != '('){
                        postfixExpression += charStack.pop()
                    }
                    if (charStack.top()=='('){
                        charStack.pop()
                    } else {
                        Log.d("OUTPUT CHANGE NOTATION", "Error, los parentesis no estan balanceados")
                        return "error"
                    }
                }
                '⊕','X','·','&','+','N','~' -> {
                    while (!charStack.isEmpty() && getHierarchyOperator(charStack.top()) >= getHierarchyOperator(expression[i])) {
                        postfixExpression += charStack.pop()
                    }
                    charStack.push(expression[i])
                }
                else -> postfixExpression += expression[i]
            }
        }
        while (!charStack.isEmpty()){
            postfixExpression += charStack.pop()
        }
        Log.d("OUTPUT CHANGE NOTATION", "$expression -> $postfixExpression")
        return postfixExpression
    }
}
/*
    INICIO
     Crear pila y la lista de salida, inicialmente vacias.
     MIENTRAS lista de entrada no este vacia y
              no se ha encontrado ningun error HACER
       Extraer el primer termino de la lista (lo llamaremos E)
       SEGUN-SEA E
         CASO E es número :
           Insertar E al final de la lista de salida
         CASO E es la variable x :
           Insertar E al final de la lista de salida
         CASO E es un paréntesis izquierdo :
           Insertar E en la pila
         CASO E es un paréntesis derecho :
           MIENTRAS La pila no este vacía y
                    su cima no sea un paréntesis izquierdo HACER
             Extraer elemento de la pila
             Insertarlo al final de la lista de salida
           FIN-MIENTRAS
           SI Encontramos el parentesis izquierdo ENTONCES
             Extraerlo de la pila y destruirlo
           SINO
             Se ha detectado un ERROR 2
           FIN-SI
           Destruir E
         CASO E es un operador :
           MIENTRAS La pila no este vacía y
                    su cima sea un operador
                    de precedencia mayor o igual que la de E HACER
             Extraer elemento de la pila
             Insertarlo al final de la lista de salida
           FIN-MIENTRAS
           Insertar E en la pila
       FIN-SEGUN-SEA
    FIN-MIENTRAS
    MIENTRAS Pila no esté vacía HACER
      Extraer elemento de la pila
      Insertarlo al final de la lista de salida
    FIN-MIENTRAS
    Destruir pila
  FIN
*/