//ReactDOM.render(<Cp2 hello="Helo world!"/>,document.getElementById("root"));

document.getElementById("menuBtn").addEventListener("click", toggleMenu);



function toggleMenu(){
    var div = document.getElementById("modalMenu");
    div.classList.add("modalOn");
}