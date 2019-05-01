package generation;

public class Road extends Infrastructure {
    private Node start;
    private Node end;
    private double[] matrixRepresentation ;
    private double [] equationCarthesienneReduite;//equation carthesienne du style ax+by+c = 0 => a/c x + b/c y = 0

    public Road (Node start, Node end) {
        this.start = start;
        this.end = end;
        this.matrixRepresentation = new double[]{end.x - start.x, end.y - start.y};
        double a = end.y-start.y;
        double b = start.x - end.x;
        double c = a*start.x + b*start.y;
        this.equationCarthesienneReduite = new double []{a,b,c};
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public double[] getMatrixRepresentation() {
        return matrixRepresentation;
    }

    public double equationCartesienneY (double x){// y = ...
        return (x-this.start.x)*this.matrixRepresentation[1]/this.matrixRepresentation[0]+this.start.y;
    }

    public double equationCartesienneX (double y){// x = ...
        return -((y-this.start.y)*this.matrixRepresentation[0])/this.matrixRepresentation[1]+this.start.x;
    }

    public void printMatrixRepresentation (){
        System.out.println("("+this.matrixRepresentation[0]+","+this.matrixRepresentation[1]+")");
    }

    @Override
    public void print(){
        System.out.println("This road called : "+this.name+" start at "+ this.start.name+" and end at "+ this.end.name);
        System.out.print("\t\t\t");
        this.start.print();
        System.out.print("\t\t\t");
        this.end.print();
        System.out.println("Matrix representation : ("+this.matrixRepresentation[0]+","+this.matrixRepresentation[1]+")");
    }

    public double[] getEquationCarthesienneReduite() {
        return equationCarthesienneReduite;
    }

    public void setEquationCarthesienneReduite(double[] equationCarthesienneReduite) {
        this.equationCarthesienneReduite = equationCarthesienneReduite;
    }
}
