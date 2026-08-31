// File Intro/SimpleExpr.java
// Java representation of expressions as in lecture 1
// sestoft@itu.dk * 2010-08-29

import java.util.Map;
import java.util.HashMap;

abstract class Expr { 
  abstract public int eval(Map<String,Integer> env);
  abstract public String fmt();
  abstract public String fmt2(Map<String,Integer> env);
}

class CstI extends Expr { 
  protected final int i;

  public CstI(int i) { 
    this.i = i; 
  }

  public int eval(Map<String,Integer> env) {
    return i;
  } 

  public String fmt() {
    return ""+i;
  }

  public String fmt2(Map<String,Integer> env) {
    return ""+i;
  }
}

class Var extends Expr { 
  protected final String name;

  public Var(String name) { 
    this.name = name; 
  }

  public int eval(Map<String,Integer> env) {
    return env.get(name);
  } 

  public String fmt() {
    return name;
  } 

  public String fmt2(Map<String,Integer> env) {
    return ""+env.get(name);
  } 

}

class Prim extends Expr { 
  protected final String oper;
  protected final Expr e1, e2;

  public Prim(String oper, Expr e1, Expr e2) { 
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

  public String fmt() {
    return "(" + e1.fmt() + oper + e2.fmt() + ")";
  } 

  public String fmt2(Map<String,Integer> env) {
    return "(" + e1.fmt2(env) + oper + e2.fmt2(env) + ")";
  } 

}

public class SimpleExpr {
  public static void main(String[] args) {
    Expr e1 = new CstI(17);
    Expr e2 = new Prim("+", new CstI(3), new Var("a"));
    Expr e3 = new Prim("+", new Prim("*", new Var("b"), new CstI(9)), 
		            new Var("a"));
    Map<String,Integer> env0 = new HashMap<String,Integer>();
    env0.put("a", 3);
    env0.put("c", 78);
    env0.put("baf", 666);
    env0.put("b", 111);

    System.out.println("Env: " + env0.toString());

    System.out.println(e1.fmt() + " = " + e1.fmt2(env0) + " = " + e1.eval(env0));
    System.out.println(e2.fmt() + " = " + e2.fmt2(env0) + " = " + e2.eval(env0));
    System.out.println(e3.fmt() + " = " + e3.fmt2(env0) + " = " + e3.eval(env0));
  }
}

abstract class Aexpr {
    abstract public String toString();
}

class CstI2 extends Aexpr {
    protected final int i;

    public CstI2(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "" + i;
    }
}

class Var2 extends Aexpr {
    protected final String name;

    public Var2(String name) {
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
