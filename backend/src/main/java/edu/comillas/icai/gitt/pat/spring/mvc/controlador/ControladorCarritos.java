package edu.comillas.icai.gitt.pat.spring.mvc.controlador;


import edu.comillas.icai.gitt.pat.spring.mvc.entity.Carrito;
import edu.comillas.icai.gitt.pat.spring.mvc.entity.LineaCarrito;
import edu.comillas.icai.gitt.pat.spring.mvc.servicio.ServicioCarritos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControladorCarritos {


    @Autowired
    ServicioCarritos servicioCarritos;

    // CREATE carrito
    @PostMapping("/carrito")
    @ResponseStatus(HttpStatus.CREATED)
    public Carrito crea(@RequestBody Carrito carritoNuevo) {
        return servicioCarritos.crea(carritoNuevo);
    }

    // CREATE linea carrito
    @PostMapping("/linea")
    @ResponseStatus(HttpStatus.CREATED)
    public LineaCarrito creaLineaCarritos(@RequestBody LineaCarrito lineaCarritoNuevo) {
        return servicioCarritos.creaLineaCarritos(lineaCarritoNuevo);
    }

    // READ (por id)
    @GetMapping("/carrito/{idCarrito}")
    public Carrito buscar(@PathVariable Long idCarrito) {
        return servicioCarritos.buscarId(idCarrito);
    }

    // READ (todos)
    @GetMapping("/carrito")
    public Iterable<Carrito> listar() {
        return servicioCarritos.listar();
    }

    // UPDATE
    @PutMapping("/carrito/{idCarrito}")
    public Carrito actualiza(@PathVariable Long idCarrito,
                             @RequestBody Carrito carritoActualizado) {
        return servicioCarritos.actualiza(idCarrito, carritoActualizado);
    }

    // DELETE
    @DeleteMapping("/carrito/{idCarrito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borra(@PathVariable Long idCarrito) {
        servicioCarritos.borra(idCarrito);
    }

    // DELETE linea Carrito
    @DeleteMapping("/linea/{idLineaCarrito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borraLineaCarrito(@PathVariable Long idLineaCarrito) {
        servicioCarritos.borra(idLineaCarrito);
    }
}
