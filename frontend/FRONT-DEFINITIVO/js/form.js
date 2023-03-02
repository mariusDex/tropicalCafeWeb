// URL's para poder conectar con el controller mediante AJAX


// var URL = "http://localhost:8080/TropicalCafeJava/Controller";
var URL = "http://192.168.2.78:28554/TropicalCafeJava/Controller";



// recogida de todas las etiquetas que se van a modificar durante las distintas comprobaciones a la hora de introducir datos
var divErrorPasswordShort = document.querySelector("#alertPasswordShort"); 
var divErrorPasswordMatching = document.querySelector("#alertPasswordMatching"); 
var divErrorNicknameShort = document.querySelector('#alertNicknameShort');   
var divErrorNicknameExists  = document.querySelector('#alertNicknameExists');
var divErrorEmail = document.querySelector('#alertEmail');
var divErrorEmailExists =  document.querySelector('#alertEmailExists');
var divErrorAddress = document.querySelector('#alertAddress');
var divErrorPhone = document.querySelector('#alertPhone');
var divErrorName = document.querySelector('#alertNameShort');
var divErrorSurname = document.querySelector('#alertSurnameShort');
var divErrorPrivacy = document.querySelector('#alertPrivacy');
var inputNickname = document.querySelector('#nickname')
var inputPass1 = document.querySelector('#pass1');
var inputPass2 = document.querySelector('#pass2');
var inputName = document.querySelector('#name');
var inputSurname = document.querySelector('#surname');
var inputEmail = document.querySelector('#email');
var inputAddress = document.querySelector('#address');
var inputPhone = document.querySelector('#phone');
var inputPrivacyCheckbox = document.querySelector('#checkboxPrivacy');

// colores establecidos para marcas de error/éxito/null
var green = "1.5px solid rgb(9, 206, 9)";
var red = "1.5px solid red";
var white = "1px white solid";

// booleanso para la validación de los datos introducidos 
var validNickname;
var validPass;
var validPhone;
var validEmail ;
var validAddress; 
var validName ;
var validSurname; 
var validPrivacyAccept;

/**
 * Desc. función que recorre todos los inputos, y comprueba si el nickname/email ya están asignados a un usuario en la base de datos
 * y además comprueba que los datos no son nulos o inválidos o tienen una longitud demasiado corta ... etc..
 * 
 * En caso de que todos lo booleanso de validación sean true, registrará al usuario en la base de datos
 */
function validar(){

    // listener para parar el envío de datos al servidor mediante el submit. Lo realizaremos 
    // más adelatne mediante AJAX.
    document.querySelector("#registerForm").addEventListener("submit", function(event){
        event.preventDefault()});

        // Variable que será true cuando los datos introducidos estén correctos y se ejecturá el INSERT del usuario en la BBDD
        let validRegister = false;
        
        // Recogida de datos de los INPUTS

        let pass1 = document.querySelector('#pass1').value;
        let pass2 = document.querySelector('#pass2').value; 
        let nickname = document.querySelector('#nickname').value; 
        let phone = document.querySelector('#phone').value; 
        let address = document.querySelector('#address').value; 
        let email= document.querySelector('#email').value; 
        let name = document.querySelector('#name').value;
        let surname = document.querySelector('#surname').value;


    // verificar que no hay ningún nickname no esta en BBDD conectando con java con IF y que no sea demasiado corto
    if(nickname.length < 4 || nickname.includes('ñ','ó','ò','é','ú','ù','è','á','à','í','ì','@','*','/','^',';',':','-','º','ª','>','<',',','.','+','[',']','{','}')){
        divErrorNicknameShort.style.display = "block";
        inputNickname.style.borderBottom = red;
        validNickname = false;
    } else {
        $.ajax(
            {
                url: URL,
                data: "ACTION=USER.FIND_ALL&NICKNAME=" + nickname,
                type: 'GET',
                dataType: 'json',
                async :false,
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
    
    // comprobamos la contraseña   
    if(pass1.length < 8){
        divErrorPasswordShort.style.display = "block";
        inputPass1.style.borderBottom = red;
        inputPass1.value = "";
        inputPass2.style.borderBottom = red;
        inputPass2.value = "";
        validPass = false;
    }else{
        if(pass1 != pass2){
            divErrorPasswordShort.style.display = "none";
            divErrorPasswordMatching.style.display = "block";
            inputPass1.style.borderBottom = white;
            inputPass2.value = "";
            inputPass2.style.borderBottom = red;
            validPass = false;

        }else{
            divErrorPasswordShort.style.display = "none";
            divErrorPasswordMatching.style.display = "none";
            inputPass1.style.borderBottom = green;
            inputPass2.style.borderBottom = green;
            validPass = true;
            
        }

    }
    
    // comprobamos el name y el surname

    if(name.length < 2){
        divErrorName.style.display = "block";
        inputName.value = "";
        inputName.style.borderBottom = red;
        validName = false
    }else{
        divErrorName.style.display = "none";
        inputName.style.borderBottom = green;
        validName = true;
    }

    if(surname.length < 2){
        divErrorSurname.style.display = "block";
        inputSurname.value = "";
        inputSurname.style.borderBottom = red;
        validSurname = false;
    }else{
        divErrorSurname.style.display = "none";
        inputSurname.style.borderBottom = green;
        validSurname = true;
    }

    // comprobamos Email
    if(!email.includes('@') || email.length < 4){
        divErrorEmail.style.display = "block";
        inputEmail.style.borderBottom = red;
        inputEmail.value = "";
        validEmail = false;
    }else{

        $.ajax(
            {
                url: URL,
                data: "ACTION=USER.FIND_ALL&EMAIL=" + email,
                type: 'GET',
                dataType: 'json',
                async :false,
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

    // comprobamos address
    if(address.length < 5){
        divErrorAddress.style.display = "block";
        inputAddress.style.borderBottom  = red;
        inputAddress.value = "";
        validAddress = false;    
    }else{
        divErrorAddress.style.display = "none";
        inputAddress.style.borderBottom = green;
        validAddress = true;
    }

    // comprobamos phone

    if(phone  < 0 || phone > 999999999 || phone.length != 9){
        divErrorPhone.style.display = "block";
        inputPhone.value = "";
        inputPhone.style.borderBottom = red;
        validPhone = false;
    }else{
        divErrorPhone.style.display = "none";
        inputPhone.style.borderBottom = green;
        validPhone = true;
            
    }

    if( $('#checkboxPrivacy').is(':checked') ) {
        validPrivacyAccept = true;
        inputPrivacyCheckbox.style.borderBottom = green;
        divErrorPrivacy.style.display = "none";
    }else{
        inputPrivacyCheckbox.style.borderBottom = red;
        divErrorPrivacy.style.display = "block";
        validPrivacyAccept = false;
    }

    

// comprobaciones de los inputs válidos
   
     if(validNickname == true &&  validPass == true && validPhone == true && validEmail == true && validAddress== true && validName== true && validSurname == true && validPrivacyAccept == true){
        validRegister = true;
     }


     if(validRegister == true){
        let registerChain ="";
        registerChain += "&NICKNAME=" + inputNickname.value;
        registerChain += "&NAME=" + inputName.value; 
        registerChain += "&PASSWORD=" + inputPass1.value;
        registerChain += "&SURNAME=" + inputSurname.value;
        registerChain += "&EMAIL=" + inputEmail.value;
        registerChain += "&ADDRESS=" + inputAddress.value;
        registerChain += "&PHONE=" + inputPhone.value;
        $.ajax(
            {
                url: URL,
                data:"ACTION=USER.REGISTER" + registerChain,
                type: 'POST',
                dataType: 'text',
                success: function (responseText) {
                    
                   
                    alert("Your account has been registered " + inputNickname.value +" !");
                    document.location = "/login.html"
                },
                error: function (xhr, textStatus, errorThrown) { }
            }
        );
     } 
}