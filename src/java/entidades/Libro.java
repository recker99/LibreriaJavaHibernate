package entidades;
// Generated 19-11-2024 08:33:23 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Libro generated by hbm2java
 */
public class Libro  implements java.io.Serializable {


     private Long idLibro;
     private String isbn;
     private String titulo;
     private String autor;
     private String editorial;
     private int precio;
     private Date fechaRegistro;

    public Libro() {
    }

    public Libro(String isbn, String titulo, String autor, String editorial, int precio, Date fechaRegistro) {
       this.isbn = isbn;
       this.titulo = titulo;
       this.autor = autor;
       this.editorial = editorial;
       this.precio = precio;
       this.fechaRegistro = fechaRegistro;
    }
   
    public Long getIdLibro() {
        return this.idLibro;
    }
    
    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }
    public String getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return this.autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getEditorial() {
        return this.editorial;
    }
    
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public int getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    

}


