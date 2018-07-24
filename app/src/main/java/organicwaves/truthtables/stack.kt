package organicwaves.truthtables

class stack {

    private var stk = mutableListOf<kotlin.Char>()
    private var top:Int = -1

    fun isEmpty():Boolean{
        return top == -1
    }

    fun top():Char{
        if (!isEmpty()){
            return stk.get(top)
        }
        return '!'
    }

    fun push(item:Char){
        top++
        stk.add(top,item)
    }

    fun pop():Char{
        var temporalItem = stk.get(top)
        top--
        return temporalItem
    }

    fun clear(){
        while(!isEmpty()){
            pop()
        }
    }

    fun print(){
        for (n in 0..top){
            System.out.println(stk.get(n))
        }
    }
}