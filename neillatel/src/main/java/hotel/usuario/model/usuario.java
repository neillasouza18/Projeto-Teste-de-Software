package hotel.usuario.model;
    
public class usuario {

    private String nome, endereco, email, telefone, usuario, senha;
    private boolean adminUsuario;
  

    public usuario(String nome, String endereco, String email, String telefone, String usuario,
    String senha, boolean adminUsuario){
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
        this.adminUsuario = adminUsuario;
    }


    public String getNome(){
        return this.nome;
    }

    
    public String setNome(String nome){
        return this.nome = nome;
    }


    public String getEndereco(){
        return this.endereco;
    }


    public String setEndereco(String endereco){
        return this.endereco = endereco;
    }


    public String getEmail(){
        return this.email;
    }


    public String setEmail(String email){
        return this.email = email;
    }


    public String getTelefone(){
        return this.telefone;
    }


    public String setTelefone(String telefone){
        return this.telefone = telefone;
    }


    public String getSenha(){
        return this.senha;
    }


    public String setSenha(String senha){
        return this.senha = senha;
    }


    public String getUsuario(){
        return this.usuario;
    }


    public String setUsuario(String usuario){
        return this.usuario = usuario;
    }


     public boolean getAdminUsuario(){
         return this.adminUsuario;
     }

    
     public boolean setAdminUsuario(boolean adminUsuario){
         return this.adminUsuario = adminUsuario;
     }
   

@Override
public String toString() {
    return String.format(
    "[Nome: %s, endereço: %s, Email: %s, Telefone: %s, usuário: %s, Senha: %s, admin: %b]",
    this.nome, this.endereco, this.email, this.telefone, this.usuario, this.senha, this.adminUsuario
    );
}
}
