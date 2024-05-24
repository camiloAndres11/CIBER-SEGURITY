package co.edu.uptc.model;

public class Model {
    private String correoElectronico ;
    private String contraseña;
    private String idInterno;
    private String userName;
    public Model() {
    }

    public Model(String correoElectronico, String contraseña, String idInterno, String userName) {
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
        this.idInterno = idInterno;
        this.userName = userName;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(String idInterno) {
        this.idInterno = idInterno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Model{" +
                "correoElectronico='" + correoElectronico + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", idInterno='" + idInterno + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
