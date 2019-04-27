package generation;

public abstract class Infrastructure {
    protected double x;
    protected double y;
    protected String name;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print(){
        System.out.println("Position of "+ this.getName() +" = ("+this.x+","+this.y+")");
    }
}
