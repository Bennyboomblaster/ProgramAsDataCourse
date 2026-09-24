// Exercise 1.4

import java.util.Map;
import java.util.HashMap;

abstract class Aexpr {
    abstract public int eval(Map<String,Integer> env);
    abstract public Aexpr simplify();
    abstract public String toString();
}

class CstI extends Aexpr {
    protected final int i; 
    
    public CstI(int i) {
        this.i = i;
    }
    
    public int eval(Map<String,Integer> env) {
        return i;
      } 

    public Aexpr simplify() {
        return new CstI(i);
    }
    

    @Override
    public String toString() {
        return "" + i;
    }
}

class Var extends Aexpr {
    protected final String name;
    
    public int eval(Map<String,Integer> env) {
        return env.get(name);
      } 
      
    public Aexpr simplify() {
        return new Var(name);
    }

    public Var(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

abstract class Binop extends Aexpr {
    protected final String oper;
    protected final Aexpr e1, e2;

    protected Binop(String oper, Aexpr e1, Aexpr e2) {
        this.oper = oper; this.e1 = e1; this.e2 = e2;
    }
    
    public int eval(Map<String,Integer> env) {
        if (oper.equals("+"))
              return e1.eval(env) + e2.eval(env);
            else if (oper.equals("*"))
              return e1.eval(env) * e2.eval(env);
            else if (oper.equals("-"))
              return e1.eval(env) - e2.eval(env);
            else
              throw new RuntimeException("unknown primitive");
    }

    public Aexpr simplify() {
        
        if(oper.equals("+")) {
            if(e1 instanceof CstI && ((CstI)e1).i == 0)
                return e2;
            else if (e2 instanceof CstI && ((CstI)e2).i == 0)
                return e1;
        }
        else if(oper.equals("*")) {
            if (e1 instanceof CstI && ((CstI)e1).i == 1)
                return e2;
            else if (e2 instanceof CstI && ((CstI)e2).i == 1)
                return e1;
            else if (e1 instanceof CstI && ((CstI)e1).i == 0)
                return new CstI(0);
            else if (e2 instanceof CstI && ((CstI)e2).i == 0)
                return new CstI(0);
        }
        else if(oper.equals("-")) {
            if (e2 instanceof CstI && ((CstI)e2).i == 0)
                return e1;
            else if (e1 instanceof CstI && ((CstI)e1).i == ((CstI)e2).i)
                return new CstI(0);
        }
        else
          throw new RuntimeException("unknown primitive");
          return null;
    }

    @Override
    public String toString(){
        return "(" + e1 + " " +  oper + " " + e2 + ")";
    }
}

class Add extends Binop {
    public Add(Aexpr e1, Aexpr e2) {
        super("+", e1, e2);
    }
}

class Sub extends Binop {
    public Sub(Aexpr e1, Aexpr e2) {
        super("-", e1, e2);
    }
}

class Mul extends Binop {
    public Mul(Aexpr e1, Aexpr e2) {
        super("*", e1, e2);
    }
}

public class SimpleAexpr {
  public static void main(String[] args) {
   
// Exercise 1.4.2
 
    Aexpr e1 = new Add(new CstI(17), new Var("z"));
    Aexpr e2 = new Sub(new Var("v"), new Add(new Var("w"), new Var("z")));
    Aexpr e3 = new Mul(new CstI(2), new Sub(new Var("v"), new Add(new Var("w"), new Var("z"))));
    Aexpr e4 = new Add(new Var("x"), new Add(new Var("y"), new Add(new Var("z"), new Var("v"))));
    Aexpr e5 = new Add(new Var("x"), new CstI(0));
    
    
    System.out.println(e1);
    System.out.println(e2);
    System.out.println(e3);
    System.out.println(e4);
    
    
// Exercise 1.4.3

    Map<String,Integer> env0 = new HashMap<String,Integer>();
        env0.put("z", 3);
        env0.put("v", 78);
        env0.put("w", 666);
        env0.put("x", 111);
        env0.put("y", 222);
    
    System.out.println("Env: " + env0);
    
        System.out.println(e1 + " = " + e1.eval(env0));
        System.out.println(e2 + " = " + e2.eval(env0));
        System.out.println(e3 + " = " + e3.eval(env0));
        System.out.println(e4 + " = " + e4.eval(env0));
    
    
    System.out.println(e5.simplify());
  }
}