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


function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}