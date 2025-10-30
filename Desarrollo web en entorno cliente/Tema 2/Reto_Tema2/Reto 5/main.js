    function areaTriangulo() {
    alert((base=document.getElementById("base").value)*(altura=document.getElementById("altura").value)/2);
    }
    function perimetroTrianguloEquilatero() {
        alert((lado1=document.getElementById("lado1").value)+(lado2=document.getElementById("lado2").value)+(lado3=document.getElementById("lado3").value));
    }
     function perimetroTrianguloIsoceles() {
        alert((lado1=document.getElementById("lado1").value)+(lado1=document.getElementById("lado1").value)+(lado2=document.getElementById("lado2").value));
    }
     function perimetroTrianguloEscaleno() {
        alert((lado1=document.getElementById("lado1").value)+(lado1=document.getElementById("lado1").value)+(lado3=document.getElementById("lado3").value));
    }
    function areaCuadrado(){
        alert((lado=document.getElementById("lado2").value)*(lado=document.getElementById("lado2").value));
    }
    function perimetroCuadrado(){
        alert(4*(lado=document.getElementById("lado2").value));
    }
    function areaCirculo(){
        alert(Math.PI*(radio=document.getElementById("radio").value)*(radio=document.getElementById("radio").value));
    }
    function longitudCircunferencia(){
        alert(2*Math.PI*(radio=document.getElementById("radio").value));
    }
