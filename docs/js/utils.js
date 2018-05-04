function zeroPad(num, places) {
    var zero = places - num.toString().length + 1;
    return Array(+(zero > 0 && zero)).join("0") + num;
}

function getGradient(counter) {

    var initial = parseInt((255-100) / counter);
    var init = counter;

    return {
        next: function () {
            init = init -1;
            return "rgb("+initial*init+","+initial*init+","+initial*init+")";
        }
    }
}

var safeColors = ['00','33','66','99','cc','ff'];

Math.seedrandom('hello.');

var rand = function() {
    return Math.floor(Math.random()*6);
};


var randomColor = function() {
    var r = safeColors[rand()];
    var g = safeColors[rand()];
    var b = safeColors[rand()];
    return "#"+r+g+b;
};


function isScrolledIntoView(el) {
    var rect = el.getBoundingClientRect();
    var elemTop = rect.top;
    var elemBottom = rect.bottom;

    // Only completely visible elements return true:
    var isVisible = (elemTop >= 0) && (elemBottom <= window.innerHeight);
    // Partially visible elements return true:
    //isVisible = elemTop < window.innerHeight && elemBottom >= 0;
    return isVisible;
}