package it.basiliocarrabbotta.timbra_cartellini;

/**
 * Created by Kalos on 13/08/2017.
 */

class Coordinates {


    private Double lat;
   private Double lng;
   private char lat_type;
   private char lng_type;

    public Coordinates(Double lat, Double lng){

        this.lat=lat;
        this.lng=lng;
        if(lat<0d)
            this.lat_type='O';
        else
            this.lng_type='E';

        if(lng<0d)
            this.lng_type='S';
                else
                    this.lng_type='N';
                    ;


    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public char getLat_type() {
        return lat_type;
    }

    public char getLng_type() {
        return lng_type;
    }

}
