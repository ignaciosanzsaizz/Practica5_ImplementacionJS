// 1. Apuntamos al puerto correcto donde corre Spring Boot (8080)
const API_BASE = "http://localhost:8080";
const API_CARRITO = `${API_BASE}/api/carrito`;
const API_LINEA = `${API_BASE}/api/linea`;
const API_PUT_CARRITO = (id) => `${API_BASE}/api/carrito/${id}`;

// ==========================================
// UTILIDADES: MEMORIA CACHÉ DEL NAVEGADOR
// ==========================================
function obtenerCarritoCache() {
  const carritoGuardado = localStorage.getItem("carritoLocal");
  return carritoGuardado ? JSON.parse(carritoGuardado) : [];
}

function guardarCarritoCache(carrito) {
  localStorage.setItem("carritoLocal", JSON.stringify(carrito));
}

// ==========================================
// 1. GENERAR ID DE CARRITO ANÓNIMO AL ENTRAR
// ==========================================
async function initCarrito() {
  let id_carrito = localStorage.getItem("id_carrito");
  
  if (!id_carrito) {
    try {
      // Mandamos un POST vacío para que Spring cree un Carrito y nos devuelva su ID
      const res = await fetch(API_CARRITO, { 
          method: "POST", 
          headers: {"Content-Type": "application/json"},
          body: JSON.stringify({}) 
      });
      
      const data = await res.json();
      localStorage.setItem("id_carrito", data.idCarrito);
      console.log("Nuevo ID de carrito creado en el servidor:", data.idCarrito);
    } catch (e) { 
        console.error("Error al crear carrito en servidor. ¿Está el backend encendido y con CORS?", e); 
    }
  }
}

// ==========================================
// 2. CONFIGURAR BOTONES (Página de Productos)
// ==========================================
function configurarBotones() {
  const botonesAñadir = document.querySelectorAll(".add");
  
  botonesAñadir.forEach(boton => {
    boton.onclick = (evento) => { 
      if (boton.disabled) return;

      const contenedor = evento.target.closest(".producto");
      const nombre = contenedor.querySelector("h3").innerText.trim();
      const precioTexto = contenedor.querySelector(".precio").innerText;
      const precio = parseFloat(precioTexto.replace("€", ""));

      let carrito = obtenerCarritoCache();
      let existente = carrito.find(l => l.idArticulo === nombre);

      if (existente) {
        existente.unidades += 1;
        existente.costeLinea = existente.unidades * existente.precioUnitario;
      } else {
        carrito.push({ idArticulo: nombre, precioUnitario: precio, unidades: 1, costeLinea: precio });
      }

      guardarCarritoCache(carrito);
      alert(`${nombre} añadido a tu carrito.`);
    };
  });
}

// ==========================================
// 3. PINTAR LA TABLA (Página de Carrito)
// ==========================================
function pintarTablaCarrito() {
  const tabla = document.querySelector(".carrito");
  if (!tabla) return; // Si no estamos en carrito.html, abortamos función
  
  const carrito = obtenerCarritoCache();
  
  // Limpiar la tabla dejando solo las cabeceras
  tabla.innerHTML = `<tr><th>Producto</th><th>Precio</th><th>Cantidad</th><th>Total</th></tr>`;

  let total = 0;
  carrito.forEach(linea => {
    total += linea.costeLinea;
    const fila = document.createElement("tr");
    fila.innerHTML = `
      <td>${linea.idArticulo}</td>
      <td>${linea.precioUnitario.toFixed(2)}€</td>
      <td>${linea.unidades}</td>
      <td>${linea.costeLinea.toFixed(2)}€</td>
    `;
    tabla.appendChild(fila);
  });

  const elTotal = document.querySelector("h3");
  if (elTotal) elTotal.innerText = `Total: ${total.toFixed(2)} €`;

  // Inyectar el botón de checkout si hay productos
  if (carrito.length > 0 && !document.getElementById("btn-tramitar")) {
      const seccion = document.querySelector("section");
      const btnTramitar = document.createElement("button");
      btnTramitar.id = "btn-tramitar";
      btnTramitar.innerText = "Tramitar Pedido Definitivo";
      btnTramitar.style.marginTop = "20px";
      btnTramitar.onclick = enviarPedidoAlBackend;
      seccion.appendChild(btnTramitar);
  }
}


async function enviarPedidoAlBackend() {
  const carritoLocal = obtenerCarritoCache();
  const idGenerado = localStorage.getItem("id_carrito");
  
  if (carritoLocal.length === 0) return alert("El carrito está vacío");
  if (!idGenerado) return alert("Fallo técnico: No hay un ID de carrito asociado.");

  console.log(localStorage.getItem("id_carrito"));
  console.log(carritoLocal);
  let coste_total = 0;

  
  try {
    // Mandamos las líneas una a una
    for (const linea of carritoLocal) {
      const lineaJSON = {
          carrito: { idCarrito: idGenerado }, // Relación de JPA
          idArticulo: linea.idArticulo,
          precioUnitario: linea.precioUnitario,
          unidades: linea.unidades,
          costeLinea: linea.costeLinea
      };

      coste_total += linea.costeLinea;
 
      const res = await fetch(API_LINEA, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(lineaJSON)
      });

      if (!res.ok) throw new Error("Fallo al guardar línea en BBDD");
    }
    
    const carritoJSON = {
      idCarrito: idGenerado,
      correo: `cliente${idGenerado}@gmail.com`,
      idUsuario: parseInt(idGenerado),
      precioTotal: coste_total
    }

    const res = await fetch(API_PUT_CARRITO(idGenerado), {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(carritoJSON)
    });

    // Si todo ha ido bien, vaciamos la caché y creamos un carrito nuevo para el futuro
    localStorage.removeItem("carritoLocal");
    localStorage.removeItem("id_carrito"); 
    
    alert("¡Pedido enviado correctamente a la base de datos!");
    location.reload(); // Recarga la página para mostrar el carrito vacío

  } catch (e) { 
      console.error(e);
      alert("Hubo un problema de conexión con el servidor."); 
  }
}

document.addEventListener("DOMContentLoaded", () => {
  initCarrito();
  configurarBotones();
  pintarTablaCarrito();
});