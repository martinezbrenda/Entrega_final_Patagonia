package codoacodo.vuelosapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

public class Dolar {
    @Getter
    private String casa;
    @Getter
    private String nombre;
    @Getter
    private double compra;
    @Getter
    private double venta;
    private double promedio;
    @Getter
    private LocalDateTime fechaActualizacion;

    public double getPromedio() {
        return (getCompra()+getVenta())/2;
    }

    @Override
    public String toString() {
        return "Dolar{" +
                ", nombre='" + nombre + '\'' +
                ", compra=" + compra +
                ", venta=" + venta +
                ", precio=" + promedio +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }

}
