// var URL = "http://localhost:8080/TropicalCafeJava/Controller";
var URL = "http://192.168.2.78:28554/TropicalCafeJava/Controller";

var numeroObjetoGeneral;

// Función que se ejectua al cargar la página
document.body.addEventListener("load", displayInicial(''));


/**
 * Desc : Función que displayea los productos del catálogo según la categoría (puede ser: NULL (buscará todos), SWEET, COFFE o SNACK)
 * @param {*} categoryName : nombre de la categoría que viene insertada desde el onclick de html, y por la cuál filtraremos la búsqueda
 * de los productos desde la base de datos.
 */
function displayInicial(categoryName) {

    /* VERIFICAMOS CUAL SE HA PULSADO PARA CAMBIAR EL BORDER DE LAS OPCIONES DE CATEGORÍA*/
    var category = categoryName;

    // Switch para modificar la estética de las opciones del filtro al darle click
    switch(categoryName){
        case 'coffee':
            document.querySelector('#coffeeOption').style.borderBottom = "4px solid rgb(8, 129, 34)";
            document.querySelector('#allOption').style.borderBottom = "none";
            document.querySelector('#snacksOption').style.borderBottom = "none";
            document.querySelector('#sweetOption').style.borderBottom = "none";
            break;
        case 'sweet':
            document.querySelector('#sweetOption').style.borderBottom = "4px solid rgb(8, 129, 34)";
            document.querySelector('#allOption').style.borderBottom = "none";
            document.querySelector('#snacksOption').style.borderBottom = "none";
            document.querySelector('#coffeeOption').style.borderBottom = "none";
            break;
        case 'snack':
            document.querySelector('#snacksOption').style.borderBottom = "4px solid rgb(8, 129, 34)";
            document.querySelector('#allOption').style.borderBottom = "none";
            document.querySelector('#sweetOption').style.borderBottom = "none";
            document.querySelector('#coffeeOption').style.borderBottom = "none";
            break;
        case '':
            document.querySelector('#allOption').style.borderBottom = "4px solid rgb(8, 129, 34)";
            document.querySelector('#snacksOption').style.borderBottom = "none";
            document.querySelector('#sweetOption').style.borderBottom = "none";
            document.querySelector('#coffeeOption').style.borderBottom = "none";
            category = '';
            break;
        

    }

    $.ajax(
        {
            url: URL,
            data: "ACTION=PRODUCT.FIND_ALL&PRODUCT_CATEGORY=" + category,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (responseText) {

                let datosProducto = responseText.Products;
                let html = "";
                for (let counter in datosProducto) {
                    html += '<div class="card" id="card' + datosProducto[counter].productID + '">';

                    html += '<img src="./img/tickVerde.png" class="buySuccess" id="buySuccess' + datosProducto[counter].productID + '"/>';
                    html += '<div class="containerImagen" id="containerImagen' + datosProducto[counter].productID + '">'
                    html += '<img src="' + datosProducto[counter].productImage + '" alt="" id="foto' + datosProducto[counter].productID + '" style="cursor:pointer" onclick="voltearTarjeta(' + datosProducto[counter].productID + ')">';
                    html += '</div>'
                    html += '<p class="title">' + datosProducto[counter].productName + '</p>';
                    html += '<div id="cantidadProducto">'
                    html += '<label for="cantidad">Amount</label>';
                    html += '<input type="number" name="cantidad" value="1" class="inputCantidad" id="inputCantidad' + datosProducto[counter].productID + '"/>';
                    html += '</div>';
                    html += '<div id="addDiv">';

                    if (datosProducto[counter].productStock != 0) {
                        html += '<a class="button" style="cursor:pointer;"  onclick="addToHash(' + datosProducto[counter].productID + ')" >Add to cart</a>';
                    } else {
                        html += '<a class="button" id="noStock' + datosProducto[counter].productID + '" style="color:red;">OUT OF STOCK</a>';
                    }

                    html += '<p class="price">$' + datosProducto[counter].productCost + '</p>'
                    html += '</div>';
                    html += '<div id="divUnits' + datosProducto[counter].productID +'" style="display:none;width:100%;text-align:center;margin-top:-20px;color:red">No units available</div>'
                    html += '</div>';

                    html += '<div style="display:none;cursor:pointer" class="cardInfo" id="oculto' + datosProducto[counter].productID + '"  onclick="cerrarInfo(' + datosProducto[counter].productID + ')">';
                    html += '<p>' + datosProducto[counter].productDescription + '</p>';
                    html += '</div>';

                }

                $('.cards').html(html);


            },
            error: function (xhr, textStatus, errorThrown) {
                alert("¡ Vaya ! Algo ha fallado. Si el problema no termina, espere un tiempo.")
            }
        }
    );



}

/**
 * Desc : muestra y hace desaparecer el tick verde al añadir exitosamente un porducto al carrito de la compra
 * @param {} numeroObjeto : número de la tarjeta en la cual queremos que se muestre un tick verder al añadir exitosamente un producto al carrito
 */
function mostrarTickVerde(numeroObjeto) {

    numeroObjetoGeneral = numeroObjeto
    document.querySelector('#containerImagen' + numeroObjeto).style.display = "none";
    document.querySelector('#buySuccess' + numeroObjeto).style.display = "block";


    setTimeout(mostrarFoto, 700);

}

/**
 * Desc : muestra un error en caso de que el usuario haya seleccionado una cantidad no permitida de produto a añadir
 * @param {*} numeroObjeto : numero de la tarjeta en la cual queremos displayear el error referente a la unidades seleccionada por el usuario 
 */
function mostrarErrorUnits(numeroObjeto){
    document.querySelector('#divUnits' + numeroObjeto).style.display = "block";
    setTimeout(function () { ocultarErrorUnits( numeroObjeto ); }, 3000);
}


/**
 * Desc : oculta el error mostraado de la función de arriba
 * @param {*} numeroObjeto :  numero de la tarjeta en la cual queremos ocultar el error referente a la unidades seleccionada por el usuario 
 */
function ocultarErrorUnits(numeroObjeto){
    document.querySelector('#divUnits' + numeroObjeto).style.display = "none";
}

/**
 * Desc : muestra la foto de nuevo después del tick verde (ejecutada en la función de mostratTickVerde con un setTimeout)
 */
function mostrarFoto() {

    document.querySelector('#buySuccess' + numeroObjetoGeneral).style.display = "none";
    document.querySelector('#containerImagen' + numeroObjetoGeneral).style.display = "block";

}


/**
 * Desc : "voltea" la card y muestra la descripción del producto correspondiente
 * @param {} numTarjeta : número de la tarjeta en la cual vamos a mostrar la información del producto al dar click en la imagen 
 */
function voltearTarjeta(numTarjeta) {
    let desc = document.querySelector('#oculto' + numTarjeta);
    let card = document.querySelector('#card' + numTarjeta);

    card.style.display = "none";
    desc.style.display = "block";
}


/**
 * Desc :  Oculta la descripción del producto y muestra de nuevo la información de la card 
 * @param {} numTarjeta : número de la tarjeta en la cual  queremos cerrar la información displayeada con la función voltearTarjeta() 
 */
function cerrarInfo(numTarjeta) {
    let desc = document.querySelector('#oculto' + numTarjeta);
    let card = document.querySelector('#card' + numTarjeta);

    card.style.display = "block";
    desc.style.display = "none";
}










    
  

    
    
    



