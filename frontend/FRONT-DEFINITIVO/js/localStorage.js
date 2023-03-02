


// Hash map utilizado para almacenar los productos a comprar
var hashMapProductos = new Map();
const inicioJSON = '{'
const finalJSON = '}';

// Funciones que se ejecutan al cargar la página
document.body.addEventListener("reload", comprobarUserSession());
document.body.addEventListener("load", takeHashMap());




/**
 * Desc : función que recoge el hashMap de los produtos cada vez que se refresca la página (se ha creado esta función
 * para evitar problemas de desactualización del item hashMapProductos a la hora de borrar productos del carrito )
 */
function takeHashMap() {
    let cadenaProductosJSON = JSON.parse(localStorage.getItem('hashMapProductos'))

    // recorro el JSON y lo guardo en el hashmap
    for (var clave in cadenaProductosJSON) {
        // Controlando que json realmente tenga esa propiedad
        if (cadenaProductosJSON.hasOwnProperty(clave)) {
            // Mostrando en pantalla la clave junto a su valor
            hashMapProductos.set(parseInt((clave)), parseInt((cadenaProductosJSON[clave])));
        }
    }
}

/**
 * Desc : función que comprueba si estamos logueados. En caso de que no lo estemos, no mostrará ni el icono de carrito ni el 
 * de usuario, y en caso contrario, los mostrará.
 */
function comprobarUserSession() {
    if (localStorage.getItem("userSession") != null) {
        document.querySelector("#signUp").style.display = "none";

        if (document.querySelector('#shoppingCartIcon') != null) {
            document.querySelector('#shoppingCartIcon').style.display = "block";
        }

        document.querySelector("#logIn").style.display = "none";
        if (document.querySelector('#logoCarrito') != null) {
            document.querySelector('#logoCarrito').style.display = "block";
        }
        let userData = localStorage.getItem("userSession");
        let userDataJSON = JSON.parse(userData);
        let cadenaUser;
        cadenaUser = '<i class="fa fa-user" id="logoUser"></i>'

        $("#contenedorBotones").html(cadenaUser);
        document.querySelector("#contenedorBotones").style.display = "block";
    }
}


/**
 * Desc : añade el ID del producto además de la cantidad que seleccionamos en el hashMap de productos el cual se guarda en el
 * local storage como STRING con formato JSON y es recogido desde java como un objeto JSON y recorrido para ser insertado dentro de la base
 * de datos
 * @param {*} productID : ID del producto que queremos añadir dentro del hashMap que posteriormente mandaremos en formato
 * JSON a java para meterlos uno a uno en la base de datos tras la compra
 */
function addToHash(productID) {

    if (localStorage.getItem('userSession') != null) {

        var productStock;
        $.ajax(
            {
                url: URL,
                data: "ACTION=PRODUCT.FIND_ALL&PRODUCT_ID=" + productID,
                type: 'get',
                dataType: 'json',
                async: false,
                success: function (responseText) {
                    let datos = responseText.Products;
                    productStock = datos[0].productStock;

                },
                error: function (xhr, textStatus, errorThrown) { }
            }
        );





        let cantidadProducto;
        let cadena;
        let contador = 0;
        let amountInput = "#inputCantidad" + productID;




        if (document.querySelector('.inputCantidad') != null) {
            cantidadProducto = parseInt(document.querySelector(amountInput).value);
        }

        if (productStock >= cantidadProducto && cantidadProducto > 0 && cantidadProducto < 25) {

            if (hashMapProductos.has(productID)) {
                hashMapProductos.set(productID, (hashMapProductos.get(productID)) + cantidadProducto);
            } else {
                hashMapProductos.set(productID, cantidadProducto);
            }
            console.log(hashMapProductos);


            cadena = inicioJSON;
            for (let [key, value] of hashMapProductos) {
                cadena += '"' + key + '":"' + value + '"';
                contador++;
                if (contador < hashMapProductos.size) {
                    cadena += ',';
                }
            }

            cadena += finalJSON;

            console.log(cadena);
            localStorage.setItem('hashMapProductos', cadena);

            mostrarTickVerde(productID);

        } else {
            mostrarErrorUnits(productID);
        }

    } else {
        document.location = "login.html";
    }

}






