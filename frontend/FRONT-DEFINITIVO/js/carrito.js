// var URL = "http://localhost:8080/TropicalCafeJava/Controller";
var URL = "http://192.168.2.78:28554/TropicalCafeJava/Controller";

var hashMapCarrito = new Map();
var hashMapPrecios = new Map();
var html = "";
var totalCost = 0.0;
var cadenaProductosJSON;

// Funciones que se ejecutan al iniciar la página
document.body.addEventListener('load', comprobarCarrito());
document.body.addEventListener('load', displayCarrito());

/**
 * Desc : Muestra un div indicando al user que no tiene ningun producto seleccionado en caso de que el hashMapProductos
 * en el local storage sea null o esté vación despues de un clear all
 */
function comprobarCarrito(){
    if(localStorage.getItem('hashMapProductos') == null || localStorage.getItem('hashMapProductos') == '{}'){
        document.querySelector('#divGeneral').style.display = "none";
        document.querySelector('#emptyCart').style.display = "block";
        
    }else{
        
        document.querySelector('#divGeneral').style.display = "block";
    }
}

/**
 * Función que cierra el div del carrito y lleva al usuario directamente al checkout displayeando su correspondiente div
 */

function checkout(){
    document.querySelector('#divGeneral').style.display = "none";
    document.querySelector('#checkout').style.display = "block";
}

/**
 * Desc : añade la compra a la base de datos cogiendo los datos de userSession y hashMapProductos
 */
function addSale(){
    document.querySelector('#divGeneral').style.display = "none";
    document.querySelector('#buyDone').style.display = "block";
    var userAddress;
    var userJSON = JSON.parse(localStorage.getItem('userSession'));
    var allProductos = (localStorage.getItem('hashMapProductos'))


    console.log(allProductos)

    if( $('#checkBoxAddress').is(':checked') ) {
        userAddress = userJSON[0].userAddress;
    }else{
        userAddress = document.querySelector('#shippingAddress').value;
    }
    


    $.ajax(
        {
            url: URL,
            data: "ACTION=SALE.ADD_SALE&INVONCE=" + totalCost.toFixed(2) + "&ADDRESS=" + userAddress + "&CLIENT_ID=" + userJSON[0].userID + "&PRODUCTS=" + allProductos,
            type: 'GET',
            dataType: 'text',
            async :false,
            success: function (responseText) {
                
                document.querySelector('#divGeneral').style.display = "none";
                document.querySelector('#checkout').style.display = "none";   
                document.querySelector('#buyDone').style.display = "block";   
                localStorage.removeItem('hashMapProductos');
                
            },
            error: function (xhr, textStatus, errorThrown) {
                alert("¡ Vaya ! Algo ha fallado. Si el problema no termina, espere un tiempo.")
             }
        }
    );




}


/**
 * Desc : función que se encarga de mostrar todo el carrito en la sección principal del carrito
 */
function displayCarrito(){
    
    let cadenaProductos;
    
    cadenaProductos = localStorage.getItem('hashMapProductos');
    cadenaProductosJSON = JSON.parse(cadenaProductos);
    


    // guardamos desde el JSON todo en el hashMap para que se pueda borrar posteriormente
    
    for (var clave in cadenaProductosJSON){
        // Controlando que json realmente tenga esa propiedad
        if (cadenaProductosJSON.hasOwnProperty(clave)) {
          // Mostrando en pantalla la clave junto a su valor
            hashMapCarrito.set(parseInt(clave) , parseInt(cadenaProductosJSON[clave]) );
        }
    }

    console.log(hashMapCarrito);
    // iteramos hashmap y sacamos la información de cada producto
    

    for(var i in cadenaProductosJSON){
       
    
    
    
    $.ajax(
        {
            url: URL,
            data: "ACTION=PRODUCT.FIND_ALL&PRODUCT_ID=" + i ,
            type: 'GET',
            dataType: 'json',
            async :false,
            success: function (responseText) {
                var datosProducto = responseText.Products;
                
                for(let counter in datosProducto){
                    var totalPriceOfProduct =  parseFloat(datosProducto[counter].productCost)* cadenaProductosJSON[i];
                    
                    html += '<div class="card">'
                    html += '<div style="height:100px">'
                    html += '<img src="'+ datosProducto[counter].productImage + '" style="padding: 0;" />'
                    html += '</div>'

                    html += '<div style="text-align: center;height: 100px;">'
                    html += '<h3>' + datosProducto[counter].productName + '</h3><br>'
                    html += '<h6>'+ datosProducto[counter].productDescription +'</h6>'
                    html += '</div>'

                    html += '<div style="margin-bottom:25px">'
                    html += ' <label style="font-size: 13px;text-align:center;">Units</label>'
                    html += '<h5>' + cadenaProductosJSON[i] + '</h5>'
                    html += ' </div>'

                    html += '<div style="margin-top: 3px;padding:15px;border-top:1.5px solid white">'
                    html += '<h2>$ ' + totalPriceOfProduct.toFixed(2)+'</h2>'
                    html += '</div>'
                    html += '<div>'
                    html += '<input type="button"  onclick="removeFromCart(' + i + ')" value="Remove" class="removeButton" />'
                    html += '</div>'
                    html+= '</div>' 


                    console.log(datosProducto[counter].productCost)
                    totalCost += parseFloat(datosProducto[counter].productCost) * cadenaProductosJSON[i];
                    hashMapPrecios.set(datosProducto[counter].productID, datosProducto[counter].productCost )
                    $('#priceInsert').html( 'Total : $ ' + totalCost.toFixed(2));


                       
                }




                

            },
            error: function (xhr, textStatus, errorThrown) {
                alert("¡ Vaya ! Algo ha fallado. Si el problema no termina, espere un tiempo.")
             }
        }
    );

    


    }
    $('#productos').html(html);
}

/**
 * Desc : elimina un producto del carrito
 * @param {*} product : id del producto que le pasamos a la función para elimianarla del hashMapProdutos y acutalizarlo en el localStorage
 */
function removeFromCart(product){

    
    // Eliminamos le product del carrito general
    
    
    //document.querySelector('#payButton').value = 'Pay and enjoy | Total : ' + (totalCost - (hashMapPrecios.get(product)));
    let comprobarValor = product;
    console.log(comprobarValor)
    console.log("Hashmap antes del delete")
    console.log(hashMapCarrito)

    hashMapCarrito.delete(comprobarValor);
    
    console.log("Hashmap despues del delete")
    console.log(hashMapCarrito)
    
    

    let cadena = inicioJSON;
    let contador = "";
    for (let [key,value] of hashMapCarrito) {
        cadena += '"' + key + '":"' + value + '"';
        contador++;
        if(contador < hashMapCarrito.size){
            cadena += ',';
        }
    } 

    cadena += finalJSON
    localStorage.removeItem('hashMapProductos')
    localStorage.setItem('hashMapProductos', cadena);
    
   document.location = "carrito.html";
 
}

/**
 * Desc : eliminar todo los productos del carrito
 */
function clearAll(){
    localStorage.removeItem('hashMapProductos');
    document.location ="carrito.html";
}












