package co.edu.uptc.model;

public class Model {
    private String correoElectronico ;
    private String contraseña;
    private String idInterno;
    private String userName;
    private String telefono;
    private String nombre;
    private String apellido;
    public Model() {
    }

    public Model(String correoElectronico, String contraseña, String idInterno, String userName,String telefono , String nombre, String apellido) {
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
        this.idInterno = idInterno;
        this.userName = userName;
        this.telefono=telefono;
        this.nombre=nombre;
        this.apellido=apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
                ", telefono='" + telefono + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}