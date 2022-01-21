public class NBody {
    public static double readRadius(String path){
        In in = new In(path);
        int N = in.readInt();
        double Radius = in.readDouble();
        return Radius;
    }
    public static Planet[] readPlanets(String path){
        In in = new In(path);
        int N = in.readInt();
        double Radius = in.readDouble();
        Planet[] planets = new Planet[N];
        for(int i = 0 ; i < N ;i++){
            planets[i] = new Planet(in.readDouble() , in.readDouble() , in.readDouble() , in.readDouble() , in.readDouble() , in.readString());

        }
        return planets;
    }
    private static void showBackground(double Radius){
        String background = "images/starfield.jpg";
        StdDraw.setScale(-Radius, Radius);
        StdDraw.picture(0, 0, background , 2*Radius,2*Radius);
        StdDraw.show();
    }
    public static void main(String[] args) {
        /**Reading some arguments*/
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double Radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        /** Drawing backgrounds*/
        showBackground(Radius);
        int n = planets.length;
        /** Drawing all of the Planets*/
        for(int i = 0 ; i < n ;i++){
            planets[i].draw();
        }

        StdDraw.enableDoubleBuffering();
        double time = 0 ;

        while(time < T){
            double[] xForce = new double[n];
            double[] yForce = new double[n];
            for(int i = 0 ; i < n ; i++) {
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i = 0 ; i< n ; i++){
                planets[i].update(dt,xForce[i],yForce[i]);
            }

            showBackground(Radius);

            for(int i = 0 ; i < n ;i++){
                planets[i].draw();
            }
            StdDraw.pause(10);
            time+=dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", Radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
