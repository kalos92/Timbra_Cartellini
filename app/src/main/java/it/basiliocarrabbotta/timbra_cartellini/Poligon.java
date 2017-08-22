package it.basiliocarrabbotta.timbra_cartellini;

import java.util.ArrayList;

import static java.lang.Double.max;
import static java.lang.StrictMath.min;

/**
 * Created by Kalos on 13/08/2017.
 */

public class Poligon {

    ArrayList<Coordinates> vertex = new ArrayList<>();

    public Poligon(ArrayList<Coordinates> vertex){

        this.vertex=vertex;
    }



    boolean InsidePolygon(Coordinates p)
    {
        int counter = 0;
        int i;
        double xinters;
        Coordinates p1,p2;

        if(p.getLat_type()!=vertex.get(0).getLat_type() || p.getLng_type()!=vertex.get(0).getLng_type())
            return false;

        p1 = vertex.get(0);
        for (i=1;i<=vertex.size();i++) {
            p2 = vertex.get(i % vertex.size());
            if (p.getLat() > min(p1.getLat(),p2.getLat())) {
                if (p.getLat() <= StrictMath.max(p1.getLat(),p2.getLat())) {
                    if (p.getLng() <= StrictMath.max(p1.getLng(),p2.getLng())) {
                        if (p1.getLat() != p2.getLat()) {
                            xinters = (p.getLat()-p1.getLat())*(p2.getLng()-p1.getLng())/(p2.getLat()-p1.getLat())+p1.getLng();
                            if (p1.getLng() == p2.getLng() || p.getLng() <= xinters)
                                counter++;
                        }
                    }
                }
            }
            p1 = p2;
        }

        if (counter % 2 == 0)
            return false;
        else
            return true;
    }




}
