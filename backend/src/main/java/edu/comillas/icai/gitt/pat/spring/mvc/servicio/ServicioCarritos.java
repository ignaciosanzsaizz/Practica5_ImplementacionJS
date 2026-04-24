package edu.comillas.icai.gitt.pat.spring.mvc.servicio;

import edu.comillas.icai.gitt.pat.spring.mvc.entity.Carrito;
import edu.comillas.icai.gitt.pat.spring.mvc.entity.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.mvc.repositorio.RepoCarrito;
import edu.comillas.icai.gitt.pat.spring.mvc.repositorio.RepoLineaCarrito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServicioCarritos {

    @Autowired
    RepoCarrito repoCarrito;

    @Autowired
    RepoLineaCarrito repoLineaCarrito;

    //Crea
    @Transactional
    public Carrito crea(Carrito carritoNuevo) {
        if (repoCarrito.findByIdCarrito(carritoNuevo.getIdCarrito()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Carrito duplicado");
        }

        repoCarrito.save(carritoNuevo);
        return carritoNuevo;
    }

    //Crea Linea
    @Transactional
    public LineaCarrito creaLineaCarritos(LineaCarrito lineaCarritoNuevo) {
        // Simplemente la guardamos. Varias líneas pueden tener el mismo carrito.
        return repoLineaCarrito.save(lineaCarritoNuevo);
    }

    //Buscar por ID
    public Carrito buscarId (Long idCarritoNuevo){
        if (repoCarrito.findByIdCarrito(idCarritoNuevo) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no existe");
        }

        return  repoCarrito.findByIdCarrito(idCarritoNuevo);
    }

    //Devolver todos
    public Iterable<Carrito> listar(){
        return repoCarrito.findAll();
    }


    //Actualizar
    public Carrito actualiza (Long idCarrito, Carrito carritoActualizado){
        //Buscar el carrito
        if (repoCarrito.findByIdCarrito(idCarrito) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no existe");
        }
        if (carritoActualizado.getIdCarrito().equals(idCarrito) == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IDs no coinciden");
        }

        repoCarrito.save(carritoActualizado);
        return repoCarrito.findByIdCarrito(idCarrito);
    }

    //Delete
    public void borra( Long idCarrito){
        Carrito carrito = repoCarrito.findByIdCarrito(idCarrito);

        if (carrito == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no existe");
        }
        repoCarrito.delete(carrito);
    }

    //Delete

    public void borraLineaCarrito(Long idLineaCarrito) {
        // Usamos findById() que busca por la Primary Key (@Id) automáticamente
        LineaCarrito linea = repoLineaCarrito.findById(idLineaCarrito).orElse(null);

        if (linea == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Linea de Carrito no existe");
        }

        repoLineaCarrito.delete(linea);
    }
}
