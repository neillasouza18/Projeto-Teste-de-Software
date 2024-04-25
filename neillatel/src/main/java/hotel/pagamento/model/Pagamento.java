package hotel.pagamento.model;
import java.util.Date;

public class Pagamento {

    private int id;
    private Date Data;
    private float valor;
    private String metodoPagamento;

    public Pagamento(int id, Date data, float valor, String metodoPagamento) {
        this.id = id;
        this.Data = data;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
    }

   
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Date getData() {
        return Data;
    }


    public void setData(Date data) {
        this.Data = data;
    }


    public float getValor() {
        return valor;
    }


    public void setValor(float valor) {
        this.valor = valor;
    }


    public String getMetodoPagamento() {
        return metodoPagamento;
    }


    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }


    @Override
    public String toString() {
        return " [id=" + id + ", Data=" + Data + ", valor=" + valor + ", metodoPagamento=" + metodoPagamento
                + "]";
    }

}