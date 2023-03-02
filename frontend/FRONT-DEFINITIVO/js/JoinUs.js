
// función que se ejecuta al iniciar la página
document.body.addEventListener('load', comprobarSession());


/**
 * Desc : comprobamos cuando cargue la págna si estamos logueados o no, en caso de que no lo estemos, nos mandará
  a la página correspondiente para realizar el  log in
 */
function comprobarSession(){
    if(localStorage.getItem('userSession') == null){
        document.location = "login.html";
    }
}

/**
 * Desc : Envía la información del candidato a la base de datos (Crea un candidato y lo mete junto a la Offer_ID en la tabla N-M)  
 * @param {*} offerNumber : Número de la oferta que carga desde base de datos y es insertado en el onclick(es la offerID)
 * y es lo que guardaremos en la base de datos en la tabla Offer_candidate (N-M)
 */
function addCandidato(offerNumber){

    var userJSON = JSON.parse(localStorage.getItem('userSession'));
    var userID = userJSON[0].userID;
    var userName = userJSON[0].userName;
    var userSurname = userJSON[0].userSurname;
    var userEmail = userJSON[0].userEmail;

    $.ajax(
        {
            url: URL,
            data: "ACTION=CANDIDATE.ADD_CANDIDATE&CANDIDATE_ID=" + userID + "&CANDIDATE_NAME=" + userName + "&CANDIDATE_SURNAME=" + userSurname + "&CANDIDATE_EMAIL=" + userEmail + "&OFFER_ID=" + offerNumber,
            type: 'POST',
            dataType: 'text',
            async :false,
            success: function (responseText) {
                alert('You applied correcty for this job! ')

            },
            error: function (xhr, textStatus, errorThrown) { 
                alert('Something went wrong. Please, wait a minute');
            }
        }
    );
}

/**
 * Desc : función que despliega la información de una job offer tras darle click a "More info"
 * @param {*} numCard : el número de la card cuya información queremos desplegar (se concatena con #infoCard)
 */
function openInfo(numCard){
    let div = document.querySelector('#infoCard'+ numCard);
    div.style.display = "block";
}


/**
 * Desc : función que cierra la información de una job offer tras darle click sobre el icono de la X
 * @param {*} numCard : el número de la card cuya información queremos esconder(se concatena con #infoCard)
 */
function closeInfo(numCard){
    let div = document.querySelector('#infoCard' + numCard);
    div.style.display = "none";
}




