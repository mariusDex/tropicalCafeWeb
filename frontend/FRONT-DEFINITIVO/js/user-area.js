// var URL = "http://localhost:8080/TropicalCafeJava/Controller";
var URL = "http://192.168.2.78:28554/TropicalCafeJava/Controller";



// Al iniciar la página se cargarán las siguientes funciones : 
document.body.addEventListener('load', mostrarDatos());


/**
 * Desc: rellena el div #infoUser con la información del usuario proveniente de la base de datos
 * return :  void
 */
function mostrarDatos() {

    //sacamos la información alamcenada en el local storage y la guardamos en un objeto de tipo JSON para recoger
    // la información con facilidad
    var userJSON = JSON.parse(localStorage.getItem('userSession'));
    console.log(userJSON);
    console.log(userJSON[0].userID)
    userJSON[0].userAddress

    $.ajax(
        {
            url: URL,
            data: "ACTION=USER.FIND_ALL&USER_ID=" + userJSON[0].userID,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (responseText) {
                var datosUser = responseText.Users
                let html = "";
                for (let counter in datosUser) {


                    html += '<div id="registerForm">'
                    html += '<h2>' + datosUser[0].userNickname + '</h2><br><br>'
                    html += '<h4>Nickname</h4>'
                    html += '<input type="text"  class="inputDatosUser" value="' + datosUser[0].userNickname + '" id="userNickname"/>'
                    html += '<div class="errorAlert" id="alertNicknameShort">'
                    html += '<p>Your nickname is too short (4 characters as minimum)</p>'
                    html += ' </div>'
                    html += '<div class="errorAlert" id="alertNicknameExists">'
                    html += '<p>This nickname is already registered</p>'
                    html += '</div>'
                    html += '<h4>Address</h4>'
                    html += '<input type="text"  class="inputDatosUser" value="' + datosUser[0].userAddress + '" id="userAddress"/>'
                    html += ' <div class="errorAlert" id="alertAddress">'
                    html += ' <p>Invalid address</p>'
                    html += ' </div>'
                    html += ' <h4>E-mail</h4>'
                    html += ' <input type="text"  class="inputDatosUser"  value="' + datosUser[0].userEmail + '"id="userEmail"/>'
                    html += ' <div class="errorAlert" id="alertEmail">'
                    html += ' <p>Invalid e-mail address</p>'
                    html += ' </div>'
                    html += '<div class="errorAlert" id="alertEmailExists">'
                    html += ' <p>This E-mail is already registered</p>'
                    html += ' </div>'
                    html += '<h4>Phone</h4>'
                    html += ' <input  type="number" class="inputDatosUser" max="999999999" value="' + datosUser[0].userPhone + '" id="userPhone"/>'
                    html += ' <div class="errorAlert" id="alertPhone">'
                    html += '<p>Invalid phone number</p>'
                    html += ' </div>'

                    html += '<h3 style="margin-top: 60px;padding-top : 30px;border-top : 3px solid green;color : white">Password change :</h3>'
                    html += '<h4>Actual password</h4>'
                    html += ' <input  type="text" class="inputDatosUser" id="actualPassword" />'
                    html += ' <div class="errorAlert" id="alertNotYourPassword">'
                    html += '<p>This is not your password !</p>'
                    html += ' </div>'

                    html += '<h4>New password</h4>'
                    html += ' <input  type="text" class="inputDatosUser" id="newPassword" />'
                    html += ' <div class="errorAlert" id="alertInvalidPassword">'
                    html += '<p>Invalid password</p>'
                    html += ' </div>'

                    html += ' <div id="edition2">'
                    html += '<a class="btn btn-apply float-sm-right float-xs-left" id="saveChangesButton" onclick="saveChanges()" >Save changes &nbsp;&nbsp;&nbsp;<i class="fas fa-save"></i></a>'
                    html += ' <a class="btn btn-apply float-sm-right float-xs-left" id="logOutButton" onclick="logOut()" ><i class="fas fa-power-off"></i></a>'
                    html += ' <a class="btn btn-apply float-sm-right float-xs-left" id="deleteAccountButton" onclick="deleteAccount()" >Delete account &nbsp;&nbsp;&nbsp;<i class="fas fa-trash"></i></a>'
                    html += '</div>'

                    html += ' </div>'




                }

                $('#infoUser').html(html);

            },
            error: function (xhr, textStatus, errorThrown) {
                alert("¡ Vaya ! Algo ha fallado. Si el problema no termina, espere un tiempo.")
            }
        }
    );
}



/**
 * Desc : rellena el div #divHistory con el historial de compra del usuario logueado (consulta personalizada)
 */
function loadHistory() {
    document.querySelector('#noPurchase').style.display = "none";
    var userJSON = JSON.parse(localStorage.getItem('userSession'));

    $.ajax(
        {
            url: URL,
            data: "ACTION=USER.PURCHASE_HISTORY&USER_ID=" + userJSON[0].userID,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (responseText) {
                var datosCompra = responseText.PurchaseHistory;
                let html = "";

                if (datosCompra[0] != null) {



                    html += '<div id="titulosTh">'
                    html += '<div>IMG</div>'
                    html += '<div>Date</div>'
                    html += '<div>Nº Sale</div>'
                    html += '<div>Product</div>'
                    html += '<div>Unit/s</div>'
                    html += '<div style="color: rgb(8, 129, 34);font-size:17px";align-items:center>Total</div>'
                    html += '</div>'


                    for (let counter in datosCompra) {
                        if (counter > 0) {
                            if (datosCompra[counter - 1].onlineSaleID != datosCompra[counter].onlineSaleID) {
                                html += '<div class="divFecha"><h4>' + datosCompra[counter].onlineSaleDate + ' | Nº Purchase ' + datosCompra[counter].onlineSaleID + '</h4></div>'
                            }
                        } else {
                            html += '<div class="divFecha"><h4>' + datosCompra[counter].onlineSaleDate + ' | Nº Purchase ' + datosCompra[counter].onlineSaleID + '</h4></div>'
                        }

                        html += '<div id="containerCompras">'
                        html += '<div><img src="' + datosCompra[counter].productImage + '"/></div>'
                        html += '<div><p>' + datosCompra[counter].onlineSaleDate + '</p></div>'
                        html += '<div><p>' + datosCompra[counter].onlineSaleID + '</p></div>'
                        html += '<div><p>' + datosCompra[counter].productName + '</p></div>'
                        html += '<div><p>x' + datosCompra[counter].amount + '</p></div>'
                        html += '<div><h3>$ ' + (datosCompra[counter].productCost * datosCompra[counter].amount) + '</p></div>'
                        html += '</div>'
                    }

                    $('#divHistory').html(html);







                } else {
                    
                    document.querySelector('#divHistory').style.display = "none";
                    document.querySelector('#noPurchase').style.display = "block";
                }
            },
            error: function (xhr, textStatus, errorThrown) {
                alert("¡ Vaya ! Algo ha fallado. Si el problema no termina, espere un tiempo.")
            }
        }
    );

}

/**
 * Desc : borra el item 'userSession' del local storage ( desloguea al usuario)
 */
function logOut() {
    localStorage.removeItem("userSession");
    localStorage.removeItem('hashMapProductos');
    document.location = "entry.html";
}


/**
 * Desc : Ejecuta el borrado de la cuenta de un usuario
 */
function deleteAccount() {
    var userJSON = JSON.parse(localStorage.getItem('userSession'));
    var userID = userJSON[0].userID;

    $.ajax(
        {
            url: URL,
            data: "ACTION=USER.DELETE_USER&USER_ID=" + userID,
            type: 'POST',
            dataType: 'text',
            async: false,
            success: function (responseText) {
                localStorage.removeItem("userSession");
                localStorage.removeItem('hashMapProductos');
                document.location = "entry.html";

            },
            error: function (xhr, textStatus, errorThrown) {
                alert("Ha habido un error en el AJAX ")
            }
        }
    );
}


/**
 * Desc : Valida los datos introducidos en los inputs de la parte de #infoUser y en caso de que estén 
 *        disponibles y correcto ---> Update del usuario en la base de datos
 */
function saveChanges() {
    document.querySelector("#registerForm").addEventListener("submit", function (event) {
        event.preventDefault()
    });
    
    // variables para guardar las diferentes etiquetas que queremos utilizar
    var userJSON = JSON.parse(localStorage.getItem('userSession'));
    var divErrorNicknameShort = document.querySelector('#alertNicknameShort');

    var divErrorActualPassword = document.querySelector('#alertNotYourPassword');
    var divErrorNewPassword = document.querySelector('#alertInvalidPassword');

    var inputActualPassword = document.querySelector('#actualPassword');
    var inputNewPassword = document.querySelector('#newPassword');

    var divErrorNicknameExists = document.querySelector('#alertNicknameExists');
    var divErrorEmail = document.querySelector('#alertEmail');
    var divErrorEmailExists = document.querySelector('#alertEmailExists');
    var inputNickname = document.querySelector('#userNickname')
    var inputEmail = document.querySelector('#userEmail');
    var divErrorPhone = document.querySelector('#alertPhone');
    var inputPhone = document.querySelector('#userPhone');
    var inputAddress = document.querySelector('#userAddress');
    var divErrorAddress = document.querySelector('#alertAddress');
    
    // booleanos para la validación de los inputs
    var validNickname;
    var validEmail;
    var validPhone;
    var validAddress;
    var validActualPassword;
    var validNewPassword;

    var nickname = document.querySelector('#userNickname').value;
    var email = document.querySelector('#userEmail').value;
    var address = document.querySelector('#userAddress').value;
    var phone = document.querySelector('#userPhone').value;
    var actualPassword = document.querySelector('#actualPassword').value;
    var newPassword = document.querySelector('#newPassword').value;

    // comprobación del nickname a introducir
    if (nickname.length < 4) {
        divErrorNicknameShort.style.display = "block";
        inputNickname.style.borderBottom = red;
        validNickname = false;
    } else {

        if (userJSON[0].userNickname == nickname) {
            divErrorNicknameExists.style.display = "none";
            divErrorNicknameShort.style.display = "none";
            validNickname = true;
            inputNickname.style.borderBottom = green;
        } else {
            $.ajax(
                {
                    url: URL,
                    data: "ACTION=USER.FIND_ALL&NICKNAME=" + nickname,
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    success: function (responseText) {

                        let datos = responseText.Users;
                        if (datos[0] != null) {
                            divErrorNicknameExists.style.display = "block";
                            inputNickname.style.borderBottom = red;
                            validNickname = false;

                        } else {
                            divErrorNicknameExists.style.display = "none";
                            divErrorNicknameShort.style.display = "none";
                            validNickname = true;
                            inputNickname.style.borderBottom = green;
                        }
                    },
                    error: function (xhr, textStatus, errorThrown) { }
                }
            );
        }
    }


    // comprobamos contraseña actual
    if(actualPassword != userJSON[0].userPassword){
        divErrorActualPassword.style.display = "block";
        inputActualPassword.style.borderBottom = red;
        validActualPassword = false;
        inputActualPassword.value = "";
    }else{
        divErrorActualPassword.style.display = "none";
        inputActualPassword.style.borderBottom = green;
        validActualPassword = true;
    }

    // comprobamos contraseña nueva

    if(newPassword.length < 8){
        divErrorNewPassword.style.display = "block";
        inputNewPassword.style.borderBottom = red;
        inputNewPassword.value = "";
        validNewPassword = false;
    }else{
        divErrorNewPassword.style.display = "none";
        inputNewPassword.style.borderBottom = green;
        validNewPassword = true;
    }

    // comprobación del address

    if (address.length < 5) {
        divErrorAddress.style.display = "block";
        inputAddress.style.borderBottom = red;
        inputAddress.value = "";
        validAddress = false;
    } else {
        divErrorAddress.style.display = "none";
        inputAddress.style.borderBottom = green;
        validAddress = true;
    }

    // comprobamos email


    if (!email.includes('@') || email.length < 4) {
        divErrorEmail.style.display = "block";
        inputEmail.style.borderBottom = red;
        inputEmail.value = "";
        validEmail = false;
    } else {
        if (userJSON[0].userEmail == email) {
            divErrorEmail.style.display = "none";
            divErrorEmailExists.style.display = "none";
            inputEmail.style.borderBottom = green;
            validEmail = true;
        } else {
            $.ajax(
                {
                    url: URL,
                    data: "ACTION=USER.FIND_ALL&EMAIL=" + email,
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    success: function (responseText) {

                        let datos = responseText.Users;
                        if (datos[0] != null) {
                            divErrorEmail.style.display = "none";
                            divErrorEmailExists.style.display = "block";
                            inputEmail.style.borderBottom = red;
                            inputEmail.value = "";
                            validEmail = false;

                        } else {
                            divErrorEmail.style.display = "none";
                            divErrorEmailExists.style.display = "none";
                            inputEmail.style.borderBottom = green;
                            validEmail = true;
                        }
                    },
                    error: function (xhr, textStatus, errorThrown) { }
                }
            );
        }
    }

    // comprobamos telefono 

    if (phone < 0 || phone > 999999999 || phone.length != 9) {
        divErrorPhone.style.display = "block";
        inputPhone.value = "";
        inputPhone.style.borderBottom = red;
        validPhone = false;
    } else {
        divErrorPhone.style.display = "none";
        inputPhone.style.borderBottom = green;
        validPhone = true;

    }



     
    if (validPhone == true && validNickname == true && validAddress == true && validEmail == true && validNewPassword == true && validActualPassword == true) {
        var string = '&PHONE=' + phone + '&NICKNAME=' + nickname + '&ADDRESS=' + address + '&EMAIL=' + email + '&USER_ID=' + userJSON[0].userID + '&PASSWORD=' + newPassword;


        $.ajax(
            {
                url: URL,
                data: "ACTION=USER.UPDATE" + string,
                type: 'POST',
                dataType: 'json',
                async: false,
                success: function (responseText) {


                },
                error: function (xhr, textStatus, errorThrown) { }
            }
        );


        $.ajax(
            {
                url: URL,
                data: "ACTION=USER.FIND_ALL&USER_ID=" + userJSON[0].userID,
                type: 'get',
                dataType: 'json',
                async: false,
                success: function (responseText) {
                    let datos = responseText.Users;

                    localStorage.setItem('userSession', JSON.stringify(datos))

                },
                error: function (xhr, textStatus, errorThrown) { }
            }
        );




        document.location = "user-area.html";



    }
}

/**
 * Desc : oculta el div del historial de compra del usuario y muestra la información del perfil
 */
function goToProfile() {
    document.querySelector('#profileOption').style.borderBottom = "4px solid rgb(8, 129, 34)";
    document.querySelector('#historyOption').style.borderBottom = "none";

    document.querySelector('#divHistory').style.display = "none";
    document.querySelector('#infoUser').style.display = "block";
}


/**
 * Desc : oculta la información del usuario y muestra la información del historial de compra
 */
function goToPurchaseHistory() {



    document.querySelector('#profileOption').style.borderBottom = "none";
    document.querySelector('#historyOption').style.borderBottom = "4px solid rgb(8, 129, 34)";


    document.querySelector('#divHistory').style.display = "block";
    document.querySelector('#infoUser').style.display = "none";


    loadHistory();
}
