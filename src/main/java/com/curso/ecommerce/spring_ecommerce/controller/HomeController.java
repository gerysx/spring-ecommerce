package com.curso.ecommerce.spring_ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.curso.ecommerce.spring_ecommerce.model.DetalleOrden;
import com.curso.ecommerce.spring_ecommerce.model.Orden;
import com.curso.ecommerce.spring_ecommerce.model.Producto;
import com.curso.ecommerce.spring_ecommerce.model.Usuario;
import com.curso.ecommerce.spring_ecommerce.service.IUsuarioService;
import com.curso.ecommerce.spring_ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    //PARA ALMACENAR LOS DETALLES DE LA ORDEN
    List <DetalleOrden> detalles= new ArrayList<DetalleOrden>();

   
    private Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "/usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model){
        log.info("ID producto enviado como parámetro{}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        Double sumaTotal = 0.0;
        Optional<Producto> optionalProducto= productoService.get(id);
        log.info("Producto añadido : {}", optionalProducto.get() );
        log.info("Cantidad: {}",cantidad);
        producto=optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //VALIDAR QUE EL PRODUCTO NO SE AÑADA EN FILAS DISTINTAS DOS VECES
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p->p.getProducto().getId()==idProducto);

        if(!ingresado){
            detalles.add(detalleOrden);
        }

        

        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    //QUITAR UN PRODUCTO DEL CARRITO
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model){

        //LISTA NUEVA DE PRODUCTOS
        List <DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
        
        for(DetalleOrden detalleOrden: detalles){
            if(detalleOrden.getProducto().getId()!=id){
                ordenesNueva.add(detalleOrden);
            }
        }

        //LA NUEVA LISTA CON LOS PRODUCTOS RESTANTES
        detalles =ordenesNueva;

        double sumaTotal=0.0;
        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);


        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart (Model model ){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model){

        Usuario usuario = usuarioService.findById(1).get();
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/resumenorden";
    }
}
