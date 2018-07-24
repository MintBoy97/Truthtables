package organicwaves.truthtables

class changeNotation {

    private var expression:String = ""

    constructor(expression:String){
        this.expression = expression
    }

    private fun getHierarchyOperator(operator:Char):Int{
        /*→↓↔←↑*/
        when(operator){
            '\'' -> return 5
            '∧' -> return 4
            '∨' -> return 3
            '→' -> return 2
            '↔' -> return 1
        }
        return -1
    }

    fun getPostfixNotation():String{
        var charStack = stack()
        var postfixExpression:String = ""
        for(i in 0 until expression.length){
            when(expression[i]) {
                '(' -> charStack.push(expression[i])
                ')' -> {
                    while(charStack.top() != '(' && !charStack.isEmpty()){
                        postfixExpression += charStack.pop()
                    }
                    charStack.pop()
                }
                '\'' -> charStack.push('\'')
                '∧','∨','→','↔' -> {
                    if (charStack.top() != '(' && getHierarchyOperator(expression[i])
                            < getHierarchyOperator(charStack.top())){
                        while (!charStack.isEmpty()){ //si truena es por esta condicion
                            if(charStack.top() == '('){
                                charStack.pop()
                            } else {
                                postfixExpression += charStack.pop()
                            }
                        }
                    } else if (getHierarchyOperator(expression[i])
                            ==  getHierarchyOperator(charStack.top())){
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
        return postfixExpression
    }
}