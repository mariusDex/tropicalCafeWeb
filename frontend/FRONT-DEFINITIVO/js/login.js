// var URL = "http://localhost:8080/TropicalCafeJava/Controller";
var URL = "http://192.168.2.78:28554/TropicalCafeJava/Controller";

var divErrorNicknameNoExist = document.querySelector("#alertNicknameInvalid");
var divErrorPasswordMatch = document.querySelector("#alertPasswordIncorrect");
var inputNickname = document.querySelector("#nickname");

// booleanos de validación de los distintos campos rellenado por el usuario
var validNickname;
var validPassword;
var nickname;
var password;

// colores pre establecidos
var green = "1.5px solid rgb(9, 206, 9)";
var red = "1.5px solid red";
var white = "1px white solid";

var datos;


/**
 * Desc: función que comprueba que el username introducido existe en la base de datos y que la contraseña introducida
 * corresponde a ese Nickname, en caso afirmativo diriga al usuario a "catalogue.html"
 */
function validarLogin(){
    
    nickname = document.querySelector("#nickname").value;
    password = document.querySelector("#password").value;

    // Buscamos en la BBDD si existe el nickname, y comprueba la constraseña

    $.ajax(
        {
            url: URL,
            data: "ACTION=USER.FIND_ALL&NICKNAME=" + nickname,
            type: 'GET',
            dataType: 'json',
            async :false,
            success: function (responseText) {
                datos = responseText.Users;
                if (datos[0] != null) {
                    for (let contador in datos) {
                        //validPassword = true;
                        // En caso de que el la BBDD devuelva un user con ese nick
                        if (datos[contador] != null) {
                            divErrorNicknameNoExist.style.display = "none";
                            inputNickname.style.borderBottom = green;
                            validNickname = true;
                            validPassword = true;
                            // en caso de que la contraseña introducida coincida
                            if (datos[contador].userPassword != (password)) {
                                divErrorPasswordMatch.style.display = "block";
                                document.querySelector("#password").style.borderBottom = red;
                                validPassword = false;
                                inputPassword = "";


                                // En caso de que la contra no coincida con el usuario
                            } else {
                                document.querySelector("#password").style.borderBottom = green;
                                divErrorPasswordMatch.style.display = "none";
                                validPassword = true;

                            }

                        }
                    }

                } else { // En caso de que el nick introducido no esté registrado en la base de datos
                    divErrorNicknameNoExist.style.display = "block";
                    document.querySelector("#nickname").style.borderBottom = red;
                    document.querySelector("#password").value = "";
                    document.querySelector("#password").style.borderBottom = white;

                    validNickname = false;
                    validPassword = false;
                }


                // Registramos los datos en las Cookies en caso de que todo sea válido
                // y coincida 

                if(validNickname == true && validPassword == true){
                    let datosNuevos = JSON.stringify(datos);
                    localStorage.setItem("userSession", datosNuevos);
                    // ahora para extraer datos de ser userSession  ( por ejemplo el nickname) : 
                    //        localStorage.getItem("userSession")[0].userNickname
                    document.location  = "catalogue.html";
                }
            },
            error: function (xhr, textStatus, errorThrown) { 
                alert("Ha habido un error en el AJAX ")
            }
        }
    );





}