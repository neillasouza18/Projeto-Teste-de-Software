package hotel.reserva.model;

import java.util.Date;

public class Reserva{
    private int id;
    private Date dataInicio, dataTermino, dataCheckin, dataCheckout;
    private boolean validaReserva;

    //Construtor
    public Reserva( int id, Date dataInicio, Date dataTermino, Date dataCheckin, Date dataCheckout,
            boolean validaReserva) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.dataCheckin = dataCheckin;
        this.dataCheckout = dataCheckout;
        this.validaReserva = validaReserva;
    }

   

    //Gets e Sets
 

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Date getDataInicio() {
        return dataInicio;
    }


    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }


    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }


    public Date getDataCheckin() {
        return dataCheckin;
    }


    public void setDataCheckin(Date dataCheckin) {
        this.dataCheckin = dataCheckin;
    }


    public Date getDataCheckout() {
        return dataCheckout;
    }
    

    public void setDataCheckout(Date dataCheckout) {
        this.dataCheckout = dataCheckout;
    }


    public boolean getValidaReserva() {
        return validaReserva;
    }


    public void setValidaReserva(boolean validaReserva) {
        this.validaReserva = validaReserva;
    }

    //ToString
    @Override
    public String toString() {
        return "[id=" + this.id +  
               ", dataInicio=" + this.dataInicio + 
               ", dataTermino=" + this.dataTermino + 
               ", dataCheckin=" + this.dataCheckin + 
               ", dataCheckout=" + this.dataCheckout + 
               ", validaReserva=" + this.validaReserva + "]";
    }
    
    
}