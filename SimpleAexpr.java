// Exercise 1.4

abstract class Aexpr {
    abstract public String toString();
}

class CstI extends Aexpr {
    protected final int i;

    public CstI(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "" + i;
    }
}

class Var extends Aexpr {
    protected final String name;

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
    
    Aexpr e1 = new Add(new CstI(17), new Var("z"));
    Aexpr e2 = new Sub(new Var("v"), new Add(new Var("w"), new Var("z")));
    Aexpr e3 = new Mul(new CstI(2), new Sub(new Var("v"), new Add(new Var("w"), new Var("z"))));
    Aexpr e4 = new Add(new Var("x"), new Add(new Var("y"), new Add(new Var("z"), new Var("v"))));
    
    System.out.println(e1);
    System.out.println(e2);
    System.out.println(e3);
    System.out.println(e4);
  }
}