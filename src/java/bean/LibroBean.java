/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entidades.Libro;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author ivanb
 */
@ManagedBean(name = "libBean")
@ViewScoped
public class LibroBean {
    
    private Long idLibro;
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private int precio;
    private Date fechaRegistro;
    
    private String busquedaTermino; // Nuevo atributo para la búsqueda
    private List<Libro> librosFiltrados; // Lista de libros filtrados por la búsqueda
    
    private boolean modoRegistro = true; // Nuevo atributo para controlar el modo del formulario
    private boolean modoEdicion = false;   
    private boolean modoNuevo = true;

    /**
     * Creates a new instance of LibroBean
     */
    public LibroBean() {
    }
    
     // Método que se ejecuta al iniciar el bean  
    @PostConstruct  
    public void init() {  
        cargarLibros(); // Cargar los libros al inicio  
    }  

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
     // Getters y Setters del nuevo atributo
    public boolean isModoRegistro() {
        return modoRegistro;
    }

    public void setModoRegistro(boolean modoRegistro) {
        this.modoRegistro = modoRegistro;
    }
    
      public boolean isModoEdicion() {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }

    public void iniciarModoEdicion() {
        this.modoEdicion = true;
    }

    public void cancelarModoEdicion() {
        this.modoEdicion = false;
        limpiarFormulario();
    }
    
    public String getBusquedaTermino() {
        return busquedaTermino;
    }

    public void setBusquedaTermino(String busquedaTermino) {
        this.busquedaTermino = busquedaTermino;
    }

    public List<Libro> getLibrosFiltrados() {
        return librosFiltrados;
    }

    public void setLibrosFiltrados(List<Libro> librosFiltrados) {
        this.librosFiltrados = librosFiltrados;
    }
    
    public boolean isModoNuevo() {
    return modoNuevo;
    }

    public void setModoNuevo(boolean modoNuevo) {
        this.modoNuevo = modoNuevo;
    }
    
        public void cargarLibros() {  
         SessionFactory sf = HibernateUtil.getSessionFactory();  
         Session sesion = sf.openSession();  
         librosFiltrados = sesion.createQuery("FROM Libro").list(); // Cargar todos los libros  
         sesion.close();  
     }  

    public List<Libro> getLibros(){
        //para poder consultar los datos de una BD y metodo
        SessionFactory sf = HibernateUtil.getSessionFactory();
        //importar SessionFactory y HibernateUtil
        Session sesion = sf.openSession(); // abrir una sesion
        List<Libro> lista = sesion.createQuery("from Libro").list(); //obtenemos la lista de productos
        //List<libro> lista2 = sesion.createCriteria(Libro.class).list(); //otras forma
        return lista;
    }
    
    public String guardar(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();
        Transaction tx = sesion.beginTransaction(); //importar transaction
        Libro l = new Libro(isbn, titulo, autor, editorial, precio, fechaRegistro);
        sesion.saveOrUpdate(l);
        tx.commit(); //se hace efectiva la transaccion
        return "index";
    }
    
        public String eliminar() {  
        SessionFactory sf = HibernateUtil.getSessionFactory();  
        Session sesion = sf.openSession();  
        Transaction tx = sesion.beginTransaction();  

        // Busca el libro por ID  
        Object libroAEliminar = sesion.get(Libro.class, idLibro);  
        if (libroAEliminar != null) {  
            sesion.delete(libroAEliminar);  
        }  
    
            tx.commit();  
            sesion.close();  
            limpiarFormulario(); // Limpia el formulario después de eliminar  
            return "index"; // O la página que desees redirigir  
        }
    
    public void cargarParaEdicion(Libro libroSeleccionado) {  
        this.idLibro = libroSeleccionado.getIdLibro();  
        this.isbn = libroSeleccionado.getIsbn();  
        this.titulo = libroSeleccionado.getTitulo();  
        this.autor = libroSeleccionado.getAutor();  
        this.editorial = libroSeleccionado.getEditorial();  
        this.precio = libroSeleccionado.getPrecio();  
        this.fechaRegistro = libroSeleccionado.getFechaRegistro();  
        this.modoEdicion = true; // Cambia a modo edición  
    }  

    public void limpiarFormulario() {
        this.idLibro = null;
        this.isbn = "";
        this.titulo = "";
        this.autor = "";
        this.editorial = "";
        this.precio = 0;
        this.fechaRegistro = null;
        this.modoRegistro = true; // Cambia a modo registro
    }

        public void buscar() {  
        // Realiza la búsqueda según el término ingresado  
        SessionFactory sf = HibernateUtil.getSessionFactory();  
        Session sesion = sf.openSession();  
        String hql = "FROM Libro l WHERE l.isbn LIKE :busquedaTermino OR l.titulo LIKE :busquedaTermino OR l.autor LIKE :busquedaTermino";  
        librosFiltrados = sesion.createQuery(hql)  
                                .setParameter("busquedaTermino", "%" + busquedaTermino + "%")  
                                .list();  
        sesion.close();  

        // No limpiar los campos aquí  
    }
    
       public void seleccionarLibro(Libro libro) {  
            this.idLibro = libro.getIdLibro();  
            this.isbn = libro.getIsbn();  
            this.titulo = libro.getTitulo();  
            this.autor = libro.getAutor();  
            this.editorial = libro.getEditorial();  
            this.precio = libro.getPrecio();  
            this.fechaRegistro = libro.getFechaRegistro();  

            // Actualiza el formulario de manera parcial  
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formulario"); // Verifica que este ID sea correcto  
        }

       public String modificar() {  
        SessionFactory sf = HibernateUtil.getSessionFactory();  
        Session sesion = sf.openSession();  
        Transaction tx = sesion.beginTransaction();  

        // Busca el libro por ID y realiza el casting  
        Libro libro = (Libro) sesion.get(Libro.class, idLibro);  
        if (libro != null) {  
            // Actualiza los campos del libro  
            libro.setIsbn(isbn);  
            libro.setTitulo(titulo);  
            libro.setAutor(autor);  
            libro.setEditorial(editorial);  
            libro.setPrecio(precio);  
            libro.setFechaRegistro(fechaRegistro);  

            sesion.update(libro); // Actualiza el libro en la base de datos  
        }  

        tx.commit();  
        sesion.close();  
        limpiarFormulario(); // Limpia el formulario después de modificar  
        cargarLibros(); // Recarga la lista de libros  
        return "index"; // O la página que desees redirigir  
    }
}
