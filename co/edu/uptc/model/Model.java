package co.edu.uptc.model;

public class Model {
    private String correoElectronico ;
    private String contraseña;
    private String idInterno;
    private String userName;
    public Model() {
    }

    public Model(String correoElectronico, String contraseña) {
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
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

    @Override
    public String toString() {
        return "Model{" +
                "correoElectronico='" + correoElectronico + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
