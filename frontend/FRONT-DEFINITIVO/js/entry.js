// var URL = "http://localhost:8080/TropicalCafeJava/Controller";
var URL = "http://192.168.2.78:28554/TropicalCafeJava/Controller";


var slides = document.querySelectorAll(".slide");
var dots = document.querySelectorAll(".dot");
var index = 0;

/**
 * Desc : Función que busca los productos en la base de datos que san TOP PRODUCTS (es decir, que tengan en
 * el campo de la base de datos de TOP_PRODUCT un "1") y los displayea dentro de los 4 slides existentes para slidear
 * las fotos de los TOP PRODUCTS
 */
function cafesIntroDisplay() {
  
  $.ajax(
        {
            url: URL,
            data:"ACTION=PRODUCT.FIND_ALL&TOP_PRODUCT=1",
            type: 'GET',
            dataType: 'json',
            success: function (responseText) {
                
               
                let html = "";
                arrayProducts = responseText.Products;
               
                for (let contador in arrayProducts) {
                  
                    html = "";
                    html += '<img src="' + arrayProducts[contador].productImage + '" />';

                    $(slides[contador]).html(html);
                  
                 
                   
                }
                    
                

            },
            error: function (xhr, textStatus, errorThrown) { alert('Lo que pasó pasó')}
        }
    );
  
  
  
  
  

}


// TODO LO CORRESPONDIENTE EL MOVIMIENTO DE LA IMÁGENES CON LAS FLECHAS DE LOS SLIDES
function prevSlide(n){
  index+=n;
  console.log("prevSlide is called");
  changeSlide();
}

function nextSlide(n){
  index+=n;
  changeSlide();
}

changeSlide();

function changeSlide(){
    
  if(index>slides.length-1)
    index=0;
  
  if(index<0)
    index=slides.length-1;
  
  
  
    for(let i=0;i<slides.length;i++){
      slides[i].style.display = "none";
      
      dots[i].classList.remove("active");
      
      
    }
    
    slides[index].style.display = "block";
    dots[index].classList.add("active");

  

}



