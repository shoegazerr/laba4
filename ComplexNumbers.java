public class ComplexNumbers {
    double mnim; // создали мнимую часть числа
    double real; //создали реальную часть числа

    public ComplexNumbers(){
        this.mnim = 0;
        this.real = 0;
    }
    public double square (){
        return this.real*this.real + this.mnim*this.mnim;
    }

    public void countMandelbrot(double x, double y){
        //формула для построения множества мандельброта
        double newReal = real*real - mnim*mnim + x;
        double newMnim = 2*real*mnim +y;

        this.mnim = newMnim;
        this.real = newReal;
    }

}
