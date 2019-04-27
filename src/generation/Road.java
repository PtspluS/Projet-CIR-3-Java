package generation;

public class Road extends Infrastructure {
    private Node start;
    private Node end;
    private double[] matrixRepresentation ;

    public Road (Node start, Node end){
        this.start = start;
        this.end = end;
        this.matrixRepresentation = new double[]{end.x - start.x, end.y - start.y};
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
        System.out.println("This road start at "+ this.start.name+" and end at "+ this.end.name);
        System.out.print("\t\t\t");
        this.start.print();
        System.out.print("\t\t\t");
        this.end.print();
    }
}
