
/**
 * Desc : función de JQUERY para el correcto display del nav a la hora de hacerlo responsive
 */

$(document).ready(function() {
    $(".btn").click(function() {
      $(".items").toggleClass("show");
      $("ul li").toggleClass("hide");
    });
  });