package hotel.quarto.model;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Quarto {
    private int numero;
    private String comodidades;
    private int capacidade;
    private float tarifa;
    private Date data;
    private boolean disponibilidade;


    public Quarto(int numero, String comodidades, int capacidade, float tarifa, boolean disponibilidade, Date data) {
        this.numero = numero;
        this.comodidades = comodidades;
        this.capacidade = capacidade;
        this.tarifa = tarifa;
        this.disponibilidade = disponibilidade;
        this.data = data;
    }


    public int getNumero() {
        return numero;
    }


    public void setNumero(int numero) {
        this.numero = numero;
    }


    public String getComodidades() {
        return comodidades;
    }


    public void setComodidades(String comodidades) {
        this.comodidades = comodidades;
    }


    public int getCapacidade() {
        return capacidade;
    }


    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }


    public float getTarifa() {
        return tarifa;
    }


    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }


    public Date getData() {
        return data;
    }


    public void setVerificaData(Date dataNova) {
        data = dataNova;
    }


    public boolean getDisponibilidade(){
        return this.disponibilidade;
    }


    public void setDisponibilidade(boolean disponibilidade){
        this.disponibilidade = disponibilidade;
    }

  
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return String.format(
            "[Número: %d, Comodidades: %s, Capacidade: %d, Tarifa: %.2f, Disponibilidade: %s, Data: %s]",
            numero, comodidades, capacidade, tarifa, disponibilidade, data != null ? sdf.format(data) : "n/a"
        );
    }
}
