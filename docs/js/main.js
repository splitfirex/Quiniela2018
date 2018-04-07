//ReactDOM.render(<Cp2 hello="Helo world!"/>,document.getElementById("root"));

document.getElementById("menuBtnOpen").addEventListener("click", openMenu);

//document.getElementById("menuBtnOpen").addEventListener("click", closeMenu);

function openMenu(){
    var div = document.getElementById("slideMenu");
    div.classList.add("menuOn");

    document.getElementById("menuBtnOpen").removeEventListener("click",openMenu);
    document.getElementById("menuBtnOpen").addEventListener("click", closeMenu);
}

function closeMenu(){
    var div = document.getElementById("slideMenu");
    div.classList.remove("menuOn");

    document.getElementById("menuBtnOpen").addEventListener("click",openMenu);
    document.getElementById("menuBtnOpen").removeEventListener("click", closeMenu);
}

function displaySignIn(){
    var div = document.getElementById("container");
    document.getElementById('container').style.display= 'none' ;
    document.getElementById('modal').classList.add("show");
}

var itemClickClass = document.getElementsByClassName("itemClick");

for (var i = 0; i < itemClickClass.length; i++) {
    itemClickClass[i].addEventListener('click', closeMenu, false);
}

