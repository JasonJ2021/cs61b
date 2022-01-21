public class Planet {
    public double xxPos , yyPos,xxVel,yyVel,mass;
    public String imgFileName;
    private static final double G = 6.67e-11;
    public Planet(double xP , double yP,double xV,
                  double yV , double m , String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m ;
        imgFileName = img;
    }
    public Planet(Planet p ){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass =  p.mass ;
        imgFileName = p.imgFileName;
    }

    /**
     * eturn the distance between two planet,
     * usage samh.calcDistance(rocinante);
     * @param p
     * @return
     */
    public double calcDistance(Planet p ){
        return Math.sqrt((p.xxPos - xxPos)*(p.xxPos - xxPos) + (p.yyPos - yyPos)*(p.yyPos - yyPos));
    }
    public double calcForceExertedBy(Planet p ){
        double r = this.calcDistance(p);
        return G*this.mass*p.mass/ (r*r);
    }
    public double calcForceExertedByX(Planet p ){
        double r = this.calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return this.calcForceExertedBy(p) * dx / r;
    }
    public double calcForceExertedByY(Planet p ){
        double r = this.calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        return this.calcForceExertedBy(p) * dy / r;
    }
    public double calcNetForceExertedByX(Planet[] planets ){
        double total = 0 ;
        for (Planet p : planets){
            if(!this.equals(p)){
                total += this.calcForceExertedByX(p);
            }
        }
        return total;
    }
    public double calcNetForceExertedByY(Planet[] planets ){
        double total = 0 ;
        for (Planet p : planets){
            if(!this.equals(p)){
                total += this.calcForceExertedByY(p);
            }
        }
        return total;
    }
    public void update(double dt , double fX , double fY){
        double ax = fX / this.mass ;
        double ay = fY / this.mass;
        this.xxVel += dt * ax;
        this.yyVel += dt * ay;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }
    public void draw(){
        String path = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, path);
        StdDraw.show();
    }
}
